package com.spring.pay.service.impl;

import com.spring.base.entity.pay.*;
import com.spring.common.constants.Constants;
import com.spring.common.request.RequestUtils;
import com.spring.common.util.SnowflakeIdWorker;
import com.spring.pay.dao.IBusinessExtendDao;
import com.spring.pay.dao.IBusinessJournalsDao;
import com.spring.pay.dao.ITransJournalsDao;
import com.spring.pay.dao.ITranspayJournalsDao;
import com.spring.pay.feign.PayAccountFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ImportPrepay {

    @Autowired
    private PayAccountFeignClient payAccountFeignClient;
    @Autowired
    private IBusinessJournalsDao businessJournalsDao;
    @Autowired
    private IBusinessExtendDao businessExtendDao;
    @Autowired
    private ITransJournalsDao transJournalsDao;
    @Autowired
    private ITranspayJournalsDao transpayJournalsDao;

     public void addList(List<BdHousePrepayInfoDto> list){
         //开始时间
       Date date = new Date();


       //缴费记录表
       List<TransJournalsEntity> transJournalsEntities = new ArrayList<>();
       //支付记录表
       List<TranspayJournalsEntity> transpayJournalsEntities = new ArrayList<>();
       //交易流水记录
       List<BusinessJournalsEntity> businessJournalsEntities = new ArrayList<>();
       //业务流水扩展表
       List<BusinessExtendEntity> businessExtendEntities = new ArrayList<>();



       for(BdHousePrepayInfoDto bdHousePrepayInfoDto : list){
           String transId = "TRADE"+ SnowflakeIdWorker.generateId();
           bdHousePrepayInfoDto.setTransId(transId);
           bdHousePrepayInfoDto.setDate(date);

           //交易流水记录
           BusinessJournalsEntity businessJournals = new BusinessJournalsEntity();
           String businessJournalsId = SnowflakeIdWorker.generateId()+"";
           businessJournals.setId(businessJournalsId);
           businessJournals.setTransId(transId);
           String businessAmount = bdHousePrepayInfoDto.getUnpaidAmount();
           businessJournals.setBusinessAmount(businessAmount);
           businessJournals.setBusinessType("综合预缴");
           businessJournals.setHid(bdHousePrepayInfoDto.getHid());
           businessJournals.setUpdateTime(date);
           businessJournals.setRoomNo(bdHousePrepayInfoDto.getHid());
           businessJournals.setStatus(Constants.Status.ENABLE);
           businessJournals.setTenantId(RequestUtils.getTenantId());
           businessJournals.setCreateUser(RequestUtils.getUserId());
           businessJournals.setCreateDate(date);
           businessJournals.setObservation(0);
           businessJournals.setPname(bdHousePrepayInfoDto.getOwnerName());
           businessJournals.setPid(bdHousePrepayInfoDto.getOwnerNo());
           businessJournals.setPaymentAmount(businessAmount);

           businessJournalsEntities.add(businessJournals);

           //业务流水扩展表
           BusinessExtendEntity businessExtend = new BusinessExtendEntity();
           String businessExtendId =  SnowflakeIdWorker.generateId()+"";
           businessExtend.setId(businessExtendId);
           businessExtend.setBusinessId(businessJournalsId);
           businessExtend.setTransId(transId);
           businessExtend.setChargeWay("");
           businessExtend.setArea("");
           businessExtend.setHouseType("");
           businessExtend.setHouseStatus("");
           businessExtend.setUpdateTime(date);
           businessExtend.setStatus(Constants.Status.ENABLE);
           businessExtend.setTenantId(RequestUtils.getTenantId());
           businessExtend.setCreateUser(RequestUtils.getUserId());
           businessExtend.setCreateDate(date);
           businessExtend.setObservation(0);

           businessExtendEntities.add(businessExtend);



           String transJournalsId = SnowflakeIdWorker.generateId()+"";
           String transpayJournalsId = SnowflakeIdWorker.generateId()+"";
           String actualAmount = bdHousePrepayInfoDto.getUnpaidAmount();
           //缴费记录表
           TransJournalsEntity transJournals = new TransJournalsEntity();
           transJournals.setId(transJournalsId);
           transJournals.setTransId(transId);
           transJournals.setCid(bdHousePrepayInfoDto.getCid());
           transJournals.setCname(bdHousePrepayInfoDto.getCommunityName());
           transJournals.setCompanyId(bdHousePrepayInfoDto.getCompanyId());
           transJournals.setCompanyName(bdHousePrepayInfoDto.getCompanyName());
           transJournals.setTerminIdentify("管理平台");
           SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
           Date transDate = null;
           try {
               transDate = formatter.parse(bdHousePrepayInfoDto.getUpdateTime());
           } catch (ParseException e) {
               e.printStackTrace();
           }
           transJournals.setTransTime(transDate);
           transJournals.setTransStatus("03");
           transJournals.setUpdateTime(date);
           transJournals.setPhone(bdHousePrepayInfoDto.getPhone());
           transJournals.setChargeMark("系统导入");
           transJournals.setStatus(Constants.Status.ENABLE);
           transJournals.setTransAmount(actualAmount);
           transJournals.setTenantId(RequestUtils.getTenantId());
           transJournals.setCreateUser(RequestUtils.getUserId());
           transJournals.setCreateDate(date);
           transJournals.setObservation(0);
           transJournals.setOperatorName("system");
           transJournals.setOperatorNo("system");
           transJournals.setHouseCode(bdHousePrepayInfoDto.getHid());

           transJournalsEntities.add(transJournals);

           //支付记录表
           TranspayJournalsEntity	transpayJournals = new TranspayJournalsEntity();
           transpayJournals.setId(transpayJournalsId);
           transpayJournals.setTransId(transId);
           transpayJournals.setPaytype("现金");
           transpayJournals.setRecordCompanyId(bdHousePrepayInfoDto.getCompanyId());
           transpayJournals.setRecordCompanyName(bdHousePrepayInfoDto.getCompanyName());
           transpayJournals.setUpdateTime(date);
           transpayJournals.setStatus(Constants.Status.ENABLE);
           transpayJournals.setTenantId(RequestUtils.getTenantId());
           transpayJournals.setCreateUser(RequestUtils.getUserId());
           transpayJournals.setCreateDate(date);
           transpayJournals.setObservation(0);

           transpayJournalsEntities.add(transpayJournals);

       }
         payAccountFeignClient.importPrePay(list);
         //存入数据库
         try {
             businessJournalsDao.addList(businessJournalsEntities);
             businessExtendDao.addList(businessExtendEntities);
             transJournalsDao.addList(transJournalsEntities);
             transpayJournalsDao.addList(transpayJournalsEntities);
         } catch (Exception e) {
             e.printStackTrace();
         }



     }

}
