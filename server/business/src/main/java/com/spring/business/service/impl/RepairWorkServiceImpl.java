package com.spring.business.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.RepairEntity;
import com.spring.base.entity.buiness.RepairImageEntity;
import com.spring.base.entity.buiness.RepairProcessEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.business.dao.IRepairDao;
import com.spring.business.dao.IRepairImageDao;
import com.spring.business.dao.IRepairProcessDao;
import com.spring.business.service.IRepairWorkService;
import com.spring.business.vo.ConfirmPayVo;
import com.spring.business.vo.RepairFinishVo;
import com.spring.business.vo.RepairTransferVo;
import com.spring.common.constants.MessageCode;
import com.spring.common.constants.RepairConstants;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 维修接口实现类
 */

@Slf4j
@Service("rRepairService")
public class RepairWorkServiceImpl extends BaseServiceImpl<RepairEntity, Long> implements IRepairWorkService {

    @Autowired
    private IRepairDao rRepairDao;

    @Autowired
    private IRepairProcessDao iRepairProcessDao;

    @Autowired
    private IRepairImageDao repairImageDao;

    @Override
    public BaseDao getBaseMapper() {
        return rRepairDao;
    }

    /**
     * 维修确认接单
     *
     * @param
     * @return
     * @throws Exception
     * @author 作者：ZhaoJinHua
     * @version 创建时间：2021-01-05 14:57:47
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ApiResponseResult acceptRepair(Long repairId) throws Exception {
        ApiResponseResult response = new ApiResponseResult();
        int noUpdate = 0;
        int insertNo = 0;
        //修改报修单状态为确认接单
        RepairEntity entity = rRepairDao.selectById(repairId);
        if (entity != null) {
            entity.setRepairStatus(RepairConstants.WORK);
            noUpdate = rRepairDao.updateById(entity);

            //新增维修接单报修流程
            RepairProcessEntity process = new RepairProcessEntity();
            //组装数据
            assemblyData(process);
            process.setRepairId(repairId);
            process.setProcessType(RepairConstants.PROCESS_ACCEPT);
            insertNo = iRepairProcessDao.insert(process);
        }
        if (noUpdate > 0 && insertNo > 0) {
            response.setCode(MessageCode.SUCCESS);
            response.setMsg("成功");
        } else {
            response.setCode(MessageCode.FAIL);
            response.setMsg("确认接单失败！");
        }
        return response;
    }

    /**
     * @description:转单
     * @return:
     * @author: 赵进华
     * @time: 2021/1/6 16:31
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ApiResponseResult repairTransfer(RepairTransferVo vo) throws Exception {
        ApiResponseResult response = new ApiResponseResult();
        int noUpdate = 0;
        int insertNo = 0;
        RepairEntity entity = rRepairDao.selectById(vo.getRepairId());
        if (entity != null) {
            if (entity.getRepairStatus().equals(RepairConstants.TURN_SINGLE)){
                return createFailResult("不可重复转单！");
            }
            //更改维修单状态为派工
            entity.setHandleUser(vo.getUserName());
            entity.setRepairStatus(RepairConstants.TURN_SINGLE);
            noUpdate = rRepairDao.updateById(entity);

            //新增转单流程
            RepairProcessEntity process = new RepairProcessEntity();
            //组装数据
            setData(vo,process);
            process.setProcessType(RepairConstants.PROCESS_CHANGE);
            insertNo = iRepairProcessDao.insert(process);
        }
        if (noUpdate > 0 && insertNo > 0) {
            response.setCode(MessageCode.SUCCESS);
            response.setMsg("成功");
        } else {
            response.setCode(MessageCode.FAIL);
            response.setMsg("转单失败！");
        }
        return response;
    }

    /**
     * @description:派单
     * @return:
     * @author: 赵进华
     * @time: 2021/1/6 16:30
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ApiResponseResult repairAllocation(RepairTransferVo vo) {
        ApiResponseResult response = new ApiResponseResult();
        int noUpdate = 0;
        int insertNo = 0;
        RepairEntity entity = rRepairDao.selectById(vo.getRepairId());
        if (entity != null) {
            if (entity.getRepairStatus().equals(RepairConstants.SEND)){
                return createFailResult("工单已被派单,不可重复派单！");
            }
            //更改维修单状态为派工
            entity.setHandleUser(vo.getUserName());
            entity.setRepairStatus(RepairConstants.SEND);
            noUpdate = rRepairDao.updateById(entity);

            //新增派工流程
            RepairProcessEntity process = new RepairProcessEntity();
            //组装数据
            setData(vo,process);
            process.setProcessType(RepairConstants.PROCESS_WORK);
            insertNo = iRepairProcessDao.insert(process);
        }
        if (noUpdate > 0 && insertNo > 0) {
            response.setCode(MessageCode.SUCCESS);
            response.setMsg("成功");
        } else {
            response.setCode(MessageCode.FAIL);
            response.setMsg("派单失败！");
        }
        return response;
    }

    /**
     * @description:维修完成
     * @return:
     * @author: 赵进华
     * @time: 2021/1/6 17:08
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ApiResponseResult repairFinish(RepairFinishVo vo) throws Exception {
        ApiResponseResult response = new ApiResponseResult();
        int noUpdate = 0;
        int insertNo = 0;
        RepairEntity entity = rRepairDao.selectById(vo.getRepairId());
        if (entity != null) {
            //更改维修单状态为维修完成
            entity.setRepairResult(vo.getRepairResult());
            if (vo.getCost().compareTo(BigDecimal.ZERO) == 0) {
                //7已完成
                entity.setRepairStatus(RepairConstants.COMPLETE);
                entity.setFinishDate(new Date());

                //新增维修完成流程
                RepairProcessEntity process = new RepairProcessEntity();
                //组装数据
                assemblyData(process);
                process.setRepairId(vo.getRepairId());
                process.setRemark(vo.getRepairResult());
                process.setProcessType(RepairConstants.PROCESS_FINISH);
                insertNo = iRepairProcessDao.insert(process);
            } else {
                //6待支付
                entity.setRepairStatus(RepairConstants.STAY_PAY);
                entity.setCost(vo.getCost());

                //新增维待支付流程
                RepairProcessEntity process = new RepairProcessEntity();
                //组装数据
                assemblyData(process);
                process.setRemark(vo.getRepairResult());
                process.setRepairId(vo.getRepairId());
                process.setProcessType(RepairConstants.PROCESS_PAY);
                insertNo = iRepairProcessDao.insert(process);
            }
            noUpdate = rRepairDao.updateById(entity);

            //图片列表
            List<RepairImageEntity> list=new ArrayList<>();
            if(vo.getPicUrlList()!=null && vo.getPicUrlList().size()>0) {
                vo.getPicUrlList().forEach(item->{
                    RepairImageEntity repairImageEntity = new RepairImageEntity();
                    //组装数据
                    setPicData(entity,repairImageEntity);
                    repairImageEntity.setDocType(RepairConstants.WORK_PIC);
                    repairImageEntity.setPicPath(item);
                    list.add(repairImageEntity);
                });
                //保存图片列表
                repairImageDao.addList(list);
            }
        }
        if (noUpdate > 0 && insertNo > 0) {
            response.setCode(MessageCode.SUCCESS);
            response.setMsg("成功");
        } else {
            response.setCode(MessageCode.FAIL);
            response.setMsg("维修完成失败！");
        }
        return response;
    }

    /**
     * @description:确认支付
     * @return:
     * @author: 赵进华
     * @time: 2021/1/6 19:18
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ApiResponseResult confirmPay(ConfirmPayVo vo) throws Exception {
        ApiResponseResult response = new ApiResponseResult();
        int noUpdate = 0;
        int insertNo = 0;
        RepairEntity entity = rRepairDao.selectById(vo.getRepairId());
        if (entity != null) {
            //更改维修单状态为已完成
            entity.setRepairStatus(RepairConstants.COMPLETE);
            entity.setFinishDate(new Date());
            noUpdate = rRepairDao.updateById(entity);

            //新增维修完成流程
            RepairProcessEntity process = new RepairProcessEntity();
            //组装数据
            assemblyData(process);
            process.setRepairId(vo.getRepairId());
            process.setRemark(vo.getRemark());
            process.setProcessType(RepairConstants.PROCESS_FINISH);
            insertNo = iRepairProcessDao.insert(process);
            noUpdate = rRepairDao.updateById(entity);

            //图片列表
            List<RepairImageEntity> list=new ArrayList<>();
            if(vo.getPicUrlList()!=null && vo.getPicUrlList().size()>0) {
                vo.getPicUrlList().forEach(item->{
                    RepairImageEntity repairImageEntity = new RepairImageEntity();
                    //组装数据
                    setPicData(entity,repairImageEntity);
                    repairImageEntity.setDocType(RepairConstants.PAY_PIC);
                    repairImageEntity.setPicPath(item);
                    list.add(repairImageEntity);
                });
                //保存图片列表
                repairImageDao.addList(list);
            }
        }
        if (noUpdate > 0 && insertNo > 0) {
            response.setCode(MessageCode.SUCCESS);
            response.setMsg("成功");
        } else {
            response.setCode(MessageCode.FAIL);
            response.setMsg("确认支付失败！");
        }
        return response;
    }

    /**
     * 组装数据(接单 维修完成)
     * @Author 熊锋
     * @param
     * @Description //TODO
     * @Date 2021/1/15 18:54
     * @return
     */
    private void assemblyData(RepairProcessEntity process){
        //数据拷贝
        process.setId(SnowflakeIdWorker.generateId());
        process.setHandleDate(new Date());
        process.setHandleJobId(RequestUtils.getJobId());
        process.setHandlePhone(RequestUtils.getUserCode());
        process.setHandleJobName(RequestUtils.getJobName());
        process.setHandleUser(RequestUtils.getUserName());
        process.setCompanyId(RequestUtils.getCompanyId());
        process.setCommunityId(RequestUtils.getCommunityId());
        process.setTenantId(RequestUtils.getTenantId());

    }

    /**
     * 组装数据(派单,转单)
     * @Author 熊锋
     * @param
     * @Description //TODO
     * @Date 2021/1/15 18:54
     * @return
     */
    private void setData(RepairTransferVo vo,RepairProcessEntity process){
        //数据拷贝
        BeanUtils.copyProperties(vo,process);
        assemblyData(process);

    }

    /**
     * 组装数据(派单,转单)
     * @Author 熊锋
     * @param
     * @Description //TODO
     * @Date 2021/1/15 18:54
     * @return
     */
    private void setPicData(RepairEntity entity,RepairImageEntity repairImageEntity){
        //图片数据
        repairImageEntity.setRepairId(Long.valueOf(entity.getId()));
        repairImageEntity.setId(SnowflakeIdWorker.generateId());
        repairImageEntity.setTenantId(RequestUtils.getTenantId());

    }
}
