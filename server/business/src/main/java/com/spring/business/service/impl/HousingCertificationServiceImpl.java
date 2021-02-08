package com.spring.business.service.impl;

import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.vo.baseinfo.housingcertification.*;
import com.spring.business.dao.IHousingCertificationDao;
import com.spring.business.service.IHousingCertificationService;
import com.spring.common.constants.CertificationHouseConstants;
import com.spring.common.constants.MessageCode;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
//import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: HousingCertificationServiceImpl
 * @Description: 美泰房屋认证ServiceImpl
 * @author: shangshanshan
 * @date: 2021/1/4 16:22
 * @version: v1.0
 */
@Slf4j
@Service
public class HousingCertificationServiceImpl implements IHousingCertificationService {

    private static final Logger logger = LoggerFactory.getLogger(HousingCertificationServiceImpl.class);

    @Autowired
    private IHousingCertificationDao iHousingCertificationDao;
    @Autowired
    private BaseInfoFeignClient baseInfoFeignClient;

    @Override
    public ApiResponseResult queryHousingList(String userId) {
        //查询已认证成功列表
        List<UserHouseListVo> list = iHousingCertificationDao.queryHousingList(userId);
        return CollectionUtils.isNotEmpty(list) ? new ApiResponseResult(MessageCode.SUCCESS_TEXT,list,MessageCode.SUCCESS) :  new ApiResponseResult(MessageCode.SUCCESS,MessageCode.SUCCESS_TEXT);
    }

    @Override
    public ApiResponseResult getBindingStatus(String userId, String communityId) {
        MyHouseEntity param = new MyHouseEntity();
        param.setUserId(userId);
        param.setCommunityId(communityId);

        //查询已认证成功的房屋地址列表
        List<String> list = iHousingCertificationDao.getBindingList(param);
        boolean status = CollectionUtils.isNotEmpty(list);

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("status",status);
        result.put("houseAddress",list);

        return new ApiResponseResult(MessageCode.SUCCESS_TEXT,result,MessageCode.SUCCESS);
    }

    @Override
    public ApiResponseResult updateCertificationStatus(CertificationStatusUpdateVo vo) {
        ApiResponseResult result;
        try {
            //修改认证状态
            iHousingCertificationDao.updateCertificationStatus(vo);
            result = new ApiResponseResult(MessageCode.SUCCESS,MessageCode.SUCCESS_TEXT);
        } catch (Exception e) {
            log.info("修改房屋认证状态失败,数据id:{}",vo.getId());
            e.printStackTrace();
            result = new ApiResponseResult(MessageCode.FAIL,MessageCode.FAIL_TEXT);
        }
        return result;
    }

    @Override
    public ApiResponseResult getCertificationDetail(String id) {
        //查询房屋认证详情
        HouseCertificationDetailVo detailVo = iHousingCertificationDao.getCertificationDetail(id);
        return new ApiResponseResult(MessageCode.SUCCESS_TEXT,detailVo,MessageCode.SUCCESS);
    }

    @Override
    public ApiResponseResult addHouseCertification(CertificationHouseAddVo vo) {

        //定义返回结果
        ApiResponseResult result;
        //查询房屋是否已存在业主
        int count = iHousingCertificationDao.selectHouseOwnerCount(vo.getHouseId());
        //判断是否是业主认证，若是业主认证需要校验该房屋是否已存在业主
        if (StringUtils.equals(vo.getIdentityType(), CertificationHouseConstants.OWNER)) {
            if (count > CertificationHouseConstants.CONSTANT_ZERO) {
                return new ApiResponseResult(MessageCode.FAIL,"当前房屋已认证过业主,不支持一房多个业主");
            }
        } else {
            //家属或租客认证时校验该房屋是否已存在业主,若不存在则不能申请
            if (count == CertificationHouseConstants.CONSTANT_ZERO) {
                return new ApiResponseResult(MessageCode.FAIL,"当前房屋业主信息为空,不支持亲属租客认证");
            }
        }
        //添加房屋认证信息
        vo.setId(UUIDFactory.createId());
        vo.setCertificationWay(StringUtils.equals(vo.getIdentityType(), CertificationHouseConstants.OWNER) ? CertificationHouseConstants.OWNER_CERTIFICATION : CertificationHouseConstants.PROPERTY_CERTIFICATION);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        vo.setProcessCode("FB"+formatter.format(LocalDate.now()));
        String processName =vo.getUserName()+vo.getBuildName()+vo.getCellName()+vo.getHouseCode()+"认证";
        vo.setProcessName(processName);

        try {
            if ("1".equals(vo.getCertificateType())) {
                vo.setIdCard(vo.getCertificateNo());

//                //新增或修改房产和客户的绑定关系
//                baseInfoFeignClient.addCustomerHouseInfo(vo);

            }
            //新增房屋认证
            iHousingCertificationDao.addHouseCertification(vo);


            result = new ApiResponseResult(MessageCode.SUCCESS,MessageCode.SUCCESS_TEXT);
        } catch (Exception e) {
            log.info("新增房屋认证失败方法名:{}", Thread.currentThread().getStackTrace()[1].getMethodName());
            e.printStackTrace();
            result = new ApiResponseResult(MessageCode.FAIL,MessageCode.FAIL_TEXT);
        }
        return result;
    }

    @Override
    public ApiResponseResult updateHouseCertification(CertificationHouseAddVo vo) {

        //定义返回结果
        ApiResponseResult result;
        //查询房屋是否已存在业主
        int count = iHousingCertificationDao.selectHouseOwnerCount(vo.getHouseId());
        //判断是否是业主认证，若是业主认证需要校验该房屋是否已存在业主
        if (StringUtils.equals(vo.getIdentityType(), CertificationHouseConstants.OWNER)) {
            if (count > CertificationHouseConstants.CONSTANT_ZERO) {
                return new ApiResponseResult(MessageCode.FAIL,"当前房屋已认证过业主,不支持一房多个业主");
            }
        } else {
            //家属或租客认证时校验该房屋是否已存在业主,若不存在则不能申请
            if (count == CertificationHouseConstants.CONSTANT_ZERO) {
                return new ApiResponseResult(MessageCode.FAIL,"当前房屋业主信息为空,不支持亲属租客认证");
            }
        }


        try {
            if ("1".equals(vo.getCertificateType())) {
                vo.setIdCard(vo.getCertificateNo());

//                //新增或修改房产和客户的绑定关系
//                baseInfoFeignClient.addCustomerHouseInfo(vo);
            }

            //修改房屋认证
            iHousingCertificationDao.updateHouseCertification(vo);
            result = new ApiResponseResult(MessageCode.SUCCESS,MessageCode.SUCCESS_TEXT);
        } catch (Exception e) {
            log.info("修改房屋认证失败方法名:{}", Thread.currentThread().getStackTrace()[1].getMethodName());
            e.printStackTrace();
            result = new ApiResponseResult(MessageCode.FAIL,MessageCode.FAIL_TEXT);
        }
        return result;
    }

    @Override
    public ApiResponseResult getCertifications(AuditHouseVo auditHouseVo) {
        List<MyHouseEntity> myHouseEntities = iHousingCertificationDao.queryList(auditHouseVo);
        return new ApiResponseResult(MessageCode.SUCCESS_TEXT,myHouseEntities,MessageCode.SUCCESS);
    }

    @Override
    public ApiResponseResult certificationDetail(String id) {
        MyHouseEntity myHouseEntity = iHousingCertificationDao.queryObject(id);
        return new ApiResponseResult(MessageCode.SUCCESS_TEXT,myHouseEntity,MessageCode.SUCCESS);
    }

    @Override
    public ApiResponseResult housingAudit(CertificationVo vo) {
        //定义返回结果
        ApiResponseResult result;
        MyHouseEntity myHouseEntity = new MyHouseEntity();
        BeanUtils.copyProperties(vo,myHouseEntity);
        try {
            iHousingCertificationDao.update(myHouseEntity);
            result = new ApiResponseResult(MessageCode.SUCCESS,MessageCode.SUCCESS_TEXT);
        } catch (Exception e) {
            log.info("房屋审核失败方法名:{}", Thread.currentThread().getStackTrace()[1].getMethodName());
            e.printStackTrace();
            result = new ApiResponseResult(MessageCode.FAIL,MessageCode.FAIL_TEXT);
        }
        return result;
    }

    @Override
    public ApiResponseResult getOwnerTenantsRelatives(String houseId) {
        List<MyHouseEntity> ownerTenantsRelatives = iHousingCertificationDao.getOwnerTenantsRelatives(houseId);
        return new ApiResponseResult(MessageCode.SUCCESS_TEXT,ownerTenantsRelatives,MessageCode.SUCCESS);
    }

    @Override
    //@GlobalTransactional(timeoutMills = 600000,rollbackFor = Exception.class)
    public ApiResponseResult addHouseUser(CertificationHouseAddVo vo) {
        MyHouseEntity myHouseEntity = new MyHouseEntity();
        BeanUtils.copyProperties(vo,myHouseEntity);
        Date date = new Date();
        myHouseEntity.setApplyDate(date);
        myHouseEntity.setAuditUserId(myHouseEntity.getUserId());
        myHouseEntity.setAuditUserName(myHouseEntity.getUserName());
        myHouseEntity.setAuditDate(date);
        myHouseEntity.setCertificationWay(1);
        myHouseEntity.setAuthType(1);
        if ("1".equals(myHouseEntity.getCertificateType())) {
            myHouseEntity.setIdCard(myHouseEntity.getCertificateNo());
        }
        iHousingCertificationDao.add(myHouseEntity);
        baseInfoFeignClient.addHouseUser(myHouseEntity);
        return new ApiResponseResult(MessageCode.SUCCESS_TEXT,null,MessageCode.SUCCESS);
    }

    @Override
    public ApiResponseResult deleteHouseUser(HouseDeleteParamVo houseDeleteParamVo) {

        iHousingCertificationDao.updateHouserUserStatus(houseDeleteParamVo.getId());
        //baseInfoFeignClient.deleteHouseUser(houseDeleteParamVo);
        return new ApiResponseResult(MessageCode.SUCCESS_TEXT,null,MessageCode.SUCCESS);
    }

    @Override
    public ApiResponseResult addHouseUserInfo(HouseEntity houseEntity) {

        logger.error("新增我的房屋:" + houseEntity.getUserName());
        logger.error("新增我的房屋:" + houseEntity.getHouseCode());
        MyHouseEntity myHouseEntity = new MyHouseEntity();
        Date date = new Date();
        myHouseEntity.setId(UUIDFactory.createId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        myHouseEntity.setProcessCode("FB"+formatter.format(LocalDate.now()));
        myHouseEntity.setUserId(houseEntity.getUserId());
        myHouseEntity.setUserName(houseEntity.getOwnerName());
        myHouseEntity.setCompanyId(houseEntity.getCompanyId());
        myHouseEntity.setCompanyName(houseEntity.getCompanyName());
        myHouseEntity.setCommunityId(houseEntity.getCommunityId());
        myHouseEntity.setCommunityName(houseEntity.getCommunityName());
        myHouseEntity.setCommunityAddress(houseEntity.getCommunityAddress());
        myHouseEntity.setCommunityAddressDetails(houseEntity.getCommunityAddressDetails());
        myHouseEntity.setBuildId(houseEntity.getBuildId());
        myHouseEntity.setBuildName(houseEntity.getBuildName());
        myHouseEntity.setCellId(houseEntity.getCellId());
        myHouseEntity.setCellName(houseEntity.getCellName());
        myHouseEntity.setHouseId(houseEntity.getId());
        myHouseEntity.setHouseNo(houseEntity.getHouseNo());
        myHouseEntity.setHouseCode(houseEntity.getHouseCode());
        myHouseEntity.setOwnerName(houseEntity.getOwnerName());
        myHouseEntity.setOwnerPhone(houseEntity.getOwnerMobile());
        myHouseEntity.setTenantId(houseEntity.getTenantId());
        myHouseEntity.setAuditStatus(1);
        myHouseEntity.setAuditUserId(RequestUtils.getUserId());
        myHouseEntity.setAuditUserName(RequestUtils.getUserName());

        String processName =myHouseEntity.getUserName()+myHouseEntity.getBuildName()+myHouseEntity.getCellName()+myHouseEntity.getHouseCode()+"认证";
        myHouseEntity.setProcessName(processName);
        myHouseEntity.setCreateDate(date);
        myHouseEntity.setApplyDate(date);
        myHouseEntity.setAuditDate(date);
        myHouseEntity.setIdentityType(1);
        myHouseEntity.setCertificationWay(2);
        myHouseEntity.setAuthType(2);
        myHouseEntity.setMobile(houseEntity.getOwnerMobile());
        myHouseEntity.setDelFlag(0);
        myHouseEntity.setSex(houseEntity.getSex());
        myHouseEntity.setCertificateType(houseEntity.getCertificateType());
        myHouseEntity.setCertificateNo(houseEntity.getCertificateNo());
        iHousingCertificationDao.add(myHouseEntity);
        return null;
    }


}
