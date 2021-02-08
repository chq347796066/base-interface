package com.spring.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.account.dao.IAccountChangeRecordDao;
import com.spring.account.dao.ISubAccountDao;
import com.spring.account.service.IPrePayService;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.account.AccountChangeRecordEntity;
import com.spring.base.entity.account.SubAccountEntity;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.entity.pay.BdHousePrepayInfoDto;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.pay.payonekeypay.BillOffsetVo;
import com.spring.base.vo.pay.payonekeypay.PayOneVo;
import com.spring.base.vo.pay.payonekeypay.PrePayVo;
import com.spring.common.constants.MessageCode;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.response.R;
import com.spring.common.util.SnowflakeIdWorker;
import com.spring.common.util.arithmetic.BigDecimalUtil;
import com.spring.common.util.excel.ExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PrePayServiceImpl extends BaseServiceImpl<SubAccountEntity, Long> implements IPrePayService {

    @Autowired
    private IAccountChangeRecordDao accountChangeRecordDao;

    @Autowired
    private ISubAccountDao subAccountDao;

    @Autowired
    private BaseInfoFeignClient baseInfoFeignClient;

    @Autowired
    private ExcelExportService excelExportService;

    @Override
    public BaseDao getBaseMapper() {
        return subAccountDao;
    }


    @Override
    public void createPrePay(PayOneVo vo) throws Exception {

        Date date = vo.getDate();
        String transId = vo.getTransId();
        List<PrePayVo> prePayVos = vo.getPrePayVos();
        SubAccountEntity subAccountEntity = new SubAccountEntity();

        QueryWrapper<SubAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SubAccountEntity::getHid,vo.getHid());
        SubAccountEntity subAccountEntity1 = subAccountDao.selectOne(queryWrapper);
        //账户号
        Long subId = SnowflakeIdWorker.generateId();

        if(subAccountEntity1==null){
            subAccountEntity.setId(subId);
            subAccountEntity.setHid(vo.getHid());
            subAccountEntity.setPname(vo.getOwnerName());
            subAccountEntity.setPid(vo.getOwnerNo());
            subAccountEntity.setSubAccountNo(subId+"");
            subAccountEntity.setSubAccountType("02");
            subAccountEntity.setSubAccountName("综合预缴");
            subAccountEntity.setSubAccountStatus("01");
            subAccountEntity.setInterestFlag("0");
            subAccountEntity.setPreAmount("0");
            subAccountEntity.setOpenBranch(vo.getPlotId());
            subAccountEntity.setOpenOrg(vo.getCompanyId());
            subAccountEntity.setCustName(vo.getOwnerName());

        }else {
            subAccountEntity.setSubAccountNo(subAccountEntity1.getSubAccountNo());
            subAccountEntity.setPreAmount(subAccountEntity1.getCurrentBalance());
            subAccountEntity.setModifyDate(date);

        }
        subAccountEntity.setCommunityName(vo.getPlotName());
        subAccountEntity.setBuildId(vo.getBuildId());
        subAccountEntity.setBuildName(vo.getBuildName());
        subAccountEntity.setCellId(vo.getCellId());
        subAccountEntity.setCellName(vo.getCellName());
        subAccountEntity.setHouseCode(vo.getHouseCode());
        subAccountEntity.setCustNo(vo.getHid());
        String currentAmount = "";
        if(subAccountEntity1!=null){
             currentAmount = subAccountEntity1.getCurrentBalance();
        }

        List<AccountChangeRecordEntity> accountChangeRecordEntities = new ArrayList<>();

        for (PrePayVo prePayVo : prePayVos){
            AccountChangeRecordEntity accountChangeRecordEntity = new AccountChangeRecordEntity();
            Long id = SnowflakeIdWorker.generateId();
            accountChangeRecordEntity.setId(id);
            accountChangeRecordEntity.setTransId(transId);
            accountChangeRecordEntity.setCustNo(vo.getOwnerNo());
            accountChangeRecordEntity.setChangeTime(date);
            accountChangeRecordEntity.setChangeAccountNo(subAccountEntity.getSubAccountNo());
            accountChangeRecordEntity.setCustNo(vo.getHid());
            accountChangeRecordEntity.setProcessStatus("01");
            accountChangeRecordEntity.setChangeReason("01");
            accountChangeRecordEntity.setChangeType("01");
            accountChangeRecordEntity.setPreAmount(currentAmount);
            //金额累加
            currentAmount = BigDecimalUtil.add(currentAmount,prePayVo.getUnpaidAmount(),2);
            accountChangeRecordEntity.setChangeBalance(currentAmount);
            accountChangeRecordEntity.setChangeAmount(prePayVo.getUnpaidAmount());
            accountChangeRecordEntity.setSubAccountType("02");
            accountChangeRecordEntity.setSubAccountName("综合预缴");
            accountChangeRecordEntity.setOrgNo(vo.getCompanyId());
            accountChangeRecordEntity.setHid(vo.getHid());
            accountChangeRecordEntities.add(accountChangeRecordEntity);
        }
        subAccountEntity.setCurrentBalance(currentAmount);
        if(subAccountEntity1==null){
            subAccountDao.insert(subAccountEntity);
        }else {
            QueryWrapper<SubAccountEntity> entityQueryWrapper = new QueryWrapper<>();
            entityQueryWrapper.lambda().eq(SubAccountEntity :: getHid,vo.getHid());
            subAccountDao.update(subAccountEntity,entityQueryWrapper);
        }

        accountChangeRecordDao.addList(accountChangeRecordEntities);


    }

    @Override
    public void importPrePay(List<BdHousePrepayInfoDto> bdHousePrepayInfoDtos) throws Exception {

        List<SubAccountEntity> existsSubAccounts = new ArrayList<>();
        List<SubAccountEntity> notExistsSubAccounts = new ArrayList<>();
        List<AccountChangeRecordEntity> accountChangeRecordEntities = new ArrayList<>();

        for (BdHousePrepayInfoDto bdHousePrepayInfoDto : bdHousePrepayInfoDtos) {
            Date date = bdHousePrepayInfoDto.getDate();
            String transId = bdHousePrepayInfoDto.getTransId();
            QueryWrapper<SubAccountEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SubAccountEntity::getHid, bdHousePrepayInfoDto.getHid());
            SubAccountEntity subAccountEntity1 = subAccountDao.selectOne(queryWrapper);
            //账户号
            Long subId = SnowflakeIdWorker.generateId();
            SubAccountEntity subAccountEntity = new SubAccountEntity();
            if (subAccountEntity1 == null) {
                subAccountEntity.setId(subId);
                subAccountEntity.setHid(bdHousePrepayInfoDto.getHid());
                subAccountEntity.setPname(bdHousePrepayInfoDto.getOwnerName());
                subAccountEntity.setPid(bdHousePrepayInfoDto.getOwnerNo());
                subAccountEntity.setSubAccountNo(subId + "");
                subAccountEntity.setSubAccountType("02");
                subAccountEntity.setSubAccountName("综合预缴");
                subAccountEntity.setSubAccountStatus("01");
                subAccountEntity.setInterestFlag("0");
                subAccountEntity.setPreAmount("0");
                subAccountEntity.setOpenBranch(bdHousePrepayInfoDto.getCid());
                subAccountEntity.setOpenOrg(bdHousePrepayInfoDto.getCompanyId());
                subAccountEntity.setCommunityName(bdHousePrepayInfoDto.getCommunityName());
                subAccountEntity.setCustName(bdHousePrepayInfoDto.getOwnerName());

            } else {
                subAccountEntity.setSubAccountNo(subAccountEntity1.getSubAccountNo());
                subAccountEntity.setPreAmount(subAccountEntity1.getCurrentBalance());
                subAccountEntity.setModifyDate(date);

            }
            subAccountEntity.setBuildId(bdHousePrepayInfoDto.getBuildId());
            subAccountEntity.setBuildName(bdHousePrepayInfoDto.getBuildName());
            subAccountEntity.setCellId(bdHousePrepayInfoDto.getCellId());
            subAccountEntity.setCellName(bdHousePrepayInfoDto.getCellName());
            subAccountEntity.setHouseCode(bdHousePrepayInfoDto.getHouseCode());
            subAccountEntity.setCustNo(bdHousePrepayInfoDto.getHid());
            String currentAmount = "";
            if (subAccountEntity1 != null) {
                currentAmount = subAccountEntity1.getCurrentBalance();
            }


            AccountChangeRecordEntity accountChangeRecordEntity = new AccountChangeRecordEntity();
            Long id = SnowflakeIdWorker.generateId();
            accountChangeRecordEntity.setId(id);
            accountChangeRecordEntity.setTransId(transId);
            accountChangeRecordEntity.setCustNo(bdHousePrepayInfoDto.getOwnerNo());
            accountChangeRecordEntity.setChangeTime(date);
            accountChangeRecordEntity.setChangeAccountNo(subAccountEntity.getSubAccountNo());
            accountChangeRecordEntity.setCustNo(bdHousePrepayInfoDto.getHid());
            accountChangeRecordEntity.setProcessStatus("01");
            accountChangeRecordEntity.setChangeReason("01");
            accountChangeRecordEntity.setChangeType("01");
            accountChangeRecordEntity.setPreAmount(currentAmount);
            //金额累加
            currentAmount = BigDecimalUtil.add(currentAmount, bdHousePrepayInfoDto.getUnpaidAmount(), 2);
            accountChangeRecordEntity.setChangeBalance(currentAmount);
            accountChangeRecordEntity.setChangeAmount(bdHousePrepayInfoDto.getUnpaidAmount());
            accountChangeRecordEntity.setSubAccountType("02");
            accountChangeRecordEntity.setSubAccountName("综合预缴");
            accountChangeRecordEntity.setOrgNo(bdHousePrepayInfoDto.getCompanyId());
            accountChangeRecordEntity.setHid(bdHousePrepayInfoDto.getHid());

            subAccountEntity.setCurrentBalance(currentAmount);



            if (subAccountEntity1 == null) {
                notExistsSubAccounts.add(subAccountEntity);
            } else {
                existsSubAccounts.add(subAccountEntity);
            }
            accountChangeRecordEntities.add(accountChangeRecordEntity);

        }
        if(CollectionUtils.isNotEmpty(notExistsSubAccounts)){
            subAccountDao.addList(notExistsSubAccounts);
        }
        if(CollectionUtils.isNotEmpty(existsSubAccounts)){
            subAccountDao.updateBatch(existsSubAccounts);
        }

        accountChangeRecordDao.addList(accountChangeRecordEntities);
    }

    @Override
    public List<SubAccountEntity> queryPrePayList(BillOffsetVo billOffsetVo) throws Exception {
        SubAccountEntity subAccountEntity = new SubAccountEntity();
        BeanUtils.copyProperties(billOffsetVo,subAccountEntity);
        return subAccountDao.queryList(subAccountEntity);
    }

    @Override
    public List<LinkedHashMap<String, String>> queryExportList(Map<String, Object> params) throws Exception {
        return baseInfoFeignClient.queryExportList(params);
    }

    @Override
    public ApiResponseResult getPrepayList(RequestPageVO<SubAccountEntity> requestPageVO) throws Exception {
        PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
        List<SubAccountEntity> list = subAccountDao.queryList(requestPageVO.getEntity());
        PageInfo<SubAccountEntity> pageInfo = new PageInfo<SubAccountEntity>(list);
        return createSuccessResult(pageInfo);
    }

    @Override
    public ApiResponseResult getPrepayList(SubAccountEntity requestPageVO) throws Exception {
        List<SubAccountEntity> list = subAccountDao.queryList(requestPageVO);
        return createSuccessResult(list);
    }

    @Override
    public ApiResponseResult exportPrepayExcelAsync(SubAccountEntity vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        excelExportService.exportPrepay(vo);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("异步导出excel中,请耐心等待！");
        return result;
    }

    /**
     * @description:异步导出预收Excel
     * @return:
     * @author: 赵进华
     * @time: 2020/12/9 10:49
     */
    @Override
    public List<SubAccountEntity> exportPrepayExcel(SubAccountEntity vo) throws Exception {
        List<SubAccountEntity> list = subAccountDao.queryList(vo);
        return list;
    }


}
