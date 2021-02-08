package com.spring.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.base.entity.pay.*;
import com.spring.base.vo.pay.revoke.RevokeInfoAddVo;
import com.spring.base.vo.pay.revoke.RevokeInfoUpdateVo;
import com.spring.base.vo.pay.revoke.RevokeVo;
import com.spring.common.exception.ServiceException;
import com.spring.common.request.RequestUtils;
import com.spring.common.util.arithmetic.BigDecimalUtil;
import com.spring.common.util.date.DateUtils;
import com.spring.common.util.timeconversion.TimeUtil;
import com.spring.pay.dao.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.pay.service.IRevokeInfoService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-05-20 10:37:32
 * @Desc类说明: 撤销记录业务接口实现类
 */

 @Slf4j
@Service("revokeInfoService")
public class RevokeInfoServiceImpl extends BaseServiceImpl<RevokeInfoEntity, String> implements IRevokeInfoService {
	
	@Autowired
	private IRevokeInfoDao revokeInfoDao;
	private static final String STYLE = "yyyyMMddHHmmss";
    @Autowired
    private ITransJournalsDao iTransJournalsDao;
    @Autowired
	private ITranspayJournalsDao iTranspayJournalsDao;
    @Autowired
	private IBusinessJournalsDao iBusinessJournalsDao;
    @Autowired
	private IBillDetailsDao iBillDetailsDao;
    @Autowired
	private IPayBillsDao iPayBillsDao;
    @Autowired
	private IBusinessExtendDao iBusinessExtendDao;

	@Override
	public BaseDao getBaseMapper() {
		return revokeInfoDao;
	}
	
	/**
	 * 新增撤销记录
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-05-20 10:37:32
	 */
	@Override
	public ApiResponseResult addRevokeInfo(RevokeInfoAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		RevokeInfoEntity entity = new RevokeInfoEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setStatus(1);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = revokeInfoDao.insert(entity);
		if (no > 0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	/**
	 * 更新撤销记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-05-20 10:37:32
	 */
	@Override
	public ApiResponseResult updateRevokeInfo(RevokeInfoUpdateVo vo) throws Exception {
		RevokeInfoEntity entity = new RevokeInfoEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = revokeInfoDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	 @Override
	 @Transactional(isolation= Isolation.DEFAULT, propagation= Propagation.REQUIRED,rollbackFor= java.lang.Exception.class)
	 public ApiResponseResult payRevoke(RevokeVo revokeVo) throws Exception {
		 ApiResponseResult result = new ApiResponseResult();
		 String transId = revokeVo.getTransId();
		 String undoReason = revokeVo.getUndoReason();
		 String operatorNo = revokeVo.getOperatorNo();
		 String operatorName = revokeVo.getOperatorName();
		 Date date = new Date();
         //正流水
		 QueryWrapper<RevokeInfoEntity> queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().eq(RevokeInfoEntity :: getTransId,transId);
		 RevokeInfoEntity positiveEntity = revokeInfoDao.selectOne(queryWrapper);
		 //反流水
		 QueryWrapper<RevokeInfoEntity> queryRevokeWrapper = new QueryWrapper<>();
		 queryRevokeWrapper.lambda().eq(RevokeInfoEntity :: getRevokeTransId,transId);
		 RevokeInfoEntity negativeEntity = revokeInfoDao.selectOne(queryRevokeWrapper);
		 TransJournalsEntity selectRevokeByTransId = null;
		 if(positiveEntity !=null){
			 if(StringUtils.isNotEmpty(positiveEntity.getRevokeTransId())){
				 QueryWrapper<TransJournalsEntity> queryTransJournalsWrapper = new QueryWrapper<>();
				 queryTransJournalsWrapper.lambda().eq(TransJournalsEntity :: getTransId,positiveEntity.getRevokeTransId());
				  selectRevokeByTransId = iTransJournalsDao.selectOne(queryTransJournalsWrapper);
				 if(selectRevokeByTransId!=null){
					 throw new ServiceException("您已经撤销了,不能再撤了");
				 }
			 }
		 }
		 if(negativeEntity !=null){
			 if(StringUtils.isNotEmpty(negativeEntity.getRevokeTransId())){
				 QueryWrapper<TransJournalsEntity> queryTransJournalsWrapper = new QueryWrapper<>();
				 queryTransJournalsWrapper.lambda().eq(TransJournalsEntity :: getTransId,negativeEntity.getRevokeTransId());
				  selectRevokeByTransId = iTransJournalsDao.selectOne(queryTransJournalsWrapper);
				 if(selectRevokeByTransId!=null){
					 throw new ServiceException("您已经撤销了,不能再撤了");
				 }
			 }
		 }
		 QueryWrapper<TransJournalsEntity> transJournalsEntityQueryWrapper = new QueryWrapper<>();
		 transJournalsEntityQueryWrapper.lambda().eq(TransJournalsEntity :: getTransId,transId);
		 selectRevokeByTransId = iTransJournalsDao.selectOne(transJournalsEntityQueryWrapper);
         // 查询支付方式
		 QueryWrapper<TranspayJournalsEntity> queryTranspayWrapper = new QueryWrapper<>();
		 queryTranspayWrapper.lambda().eq(TranspayJournalsEntity :: getTransId,transId);
		 TranspayJournalsEntity selectTranspayByTransId = iTranspayJournalsDao.selectOne(queryTranspayWrapper);
		 String payType = selectTranspayByTransId.getPaytype();
		 String str="03";
		 if (!str.equals(selectRevokeByTransId.getTransStatus())) {
			 throw new ServiceException("交易状态错误");
		 }
		 //插入撤销记录
		 RevokeInfoEntity revokeInfoEntity = new RevokeInfoEntity();
		 String revokeInfoId =  UUIDFactory.createId();
		 revokeInfoEntity.setId(revokeInfoId);
		 revokeInfoEntity.setTransId(transId);
		 revokeInfoEntity.setUndoReason(undoReason);
		 revokeInfoEntity.setUpdateTime(DateUtils.getCurrentTime());
		 revokeInfoEntity.setOperatorNo(operatorNo);
		 revokeInfoEntity.setOperatorName(operatorName);
		 revokeInfoEntity.setPlotId(selectRevokeByTransId.getCid());
		 revokeInfoDao.insert(revokeInfoEntity);
         // 查询该笔流水关联的与子账单相关的业务流水信息是否正常(子账单是否被删除)
		 QueryWrapper<BusinessJournalsEntity> entityQueryWrapper = new QueryWrapper<>();
		 entityQueryWrapper.lambda().eq(BusinessJournalsEntity :: getTransId,transId);
		 List<BusinessJournalsEntity> businessJournalsEntityList = iBusinessJournalsDao.selectList(entityQueryWrapper);
		 for (BusinessJournalsEntity businessJournalsEntity : businessJournalsEntityList) {
			 String billDetailNo = businessJournalsEntity.getBillDetialNo();
			 if(StringUtils.isNotEmpty(billDetailNo)){
				 QueryWrapper<BillDetailsEntity> billDetailsEntityQueryWrapper = new QueryWrapper<>();
				 billDetailsEntityQueryWrapper.lambda().eq(BillDetailsEntity :: getDetailNo,billDetailNo);
				 BillDetailsEntity bsBillDetails = iBillDetailsDao.selectOne(billDetailsEntityQueryWrapper);
				 if(null == bsBillDetails){
					 throw new ServiceException("子账单不存在");
				 }
			 }

		 }
		 //线下缴费撤销
		//增加反向流水
		String transAmount = selectRevokeByTransId.getTransAmount();
	    String amount = "";
	    if(StringUtils.isNotEmpty(transAmount)){
		    amount = BigDecimalUtil.sub("0",transAmount,2);
	    }
		 String thirdPaymentServiceCharge = selectRevokeByTransId.getThirdPaymentServiceCharge();
		 if(StringUtils.isNotEmpty(thirdPaymentServiceCharge)){
			 thirdPaymentServiceCharge = BigDecimalUtil.sub("0",thirdPaymentServiceCharge,2);
		 }
		 String revokeTransId = getNextId("TRADE");
		 String currentDate = TimeUtil.getCurrentDate(STYLE);
		 String transJournalsId = UUIDFactory.createId();
		 String transpayJournalsId = UUIDFactory.createId();


		 //缴费记录表
		 TransJournalsEntity transJournals = new TransJournalsEntity();
		 BeanUtils.copyProperties(selectRevokeByTransId,transJournals);
		 transJournals.setId(transJournalsId);
		 transJournals.setTransId(revokeTransId);
		 transJournals.setTransStatus("03");
		 transJournals.setTransAmount(amount);
		 transJournals.setOperatorNo(operatorNo);
		 transJournals.setOperatorName(operatorName);
		 transJournals.setTransTime(date);
		 transJournals.setTenantId(RequestUtils.getTenantId());
		 transJournals.setCreateUser(RequestUtils.getUserId());
		 transJournals.setCreateDate(new Date());
		 transJournals.setObservation(0);
		 iTransJournalsDao.insert(transJournals);
		 //撤销支付记录流水
		 QueryWrapper<TranspayJournalsEntity> transpayJournalsEntityQueryWrapper = new QueryWrapper<>();
		 transpayJournalsEntityQueryWrapper.lambda().eq(TranspayJournalsEntity :: getTransId,transId);
		 TranspayJournalsEntity transpayJournalsEntity = iTranspayJournalsDao.selectOne(transpayJournalsEntityQueryWrapper);
		 //支付记录表
		 TranspayJournalsEntity	transpayJournals = new TranspayJournalsEntity();
		 BeanUtils.copyProperties(transpayJournalsEntity,transpayJournals);
		 transpayJournals.setId(transpayJournalsId);
		 transpayJournals.setTransId(revokeTransId);
		 transpayJournals.setTenantId(RequestUtils.getTenantId());
		 transpayJournals.setCreateUser(RequestUtils.getUserId());
		 transpayJournals.setCreateDate(date);
		 transpayJournals.setObservation(0);
		 iTranspayJournalsDao.insert(transpayJournals);
		 //撤销记录表
		 QueryWrapper<RevokeInfoEntity> queryRevoke =new QueryWrapper<>();
		 queryRevoke.lambda().eq(RevokeInfoEntity::getTransId,transId);
		 RevokeInfoEntity revokeInfoEntity1 = new RevokeInfoEntity();
		 revokeInfoEntity1.setRevokeTransId(revokeTransId);
		 revokeInfoDao.update(revokeInfoEntity1,queryRevoke);
		 for (BusinessJournalsEntity businessJournalsEntity : businessJournalsEntityList) {
			 String businessJournalsId = UUIDFactory.createId();
			 String businessAmount = businessJournalsEntity.getBusinessAmount();
			 String businessId2 = businessJournalsEntity.getId();
			 String lateFee = businessJournalsEntity.getLateFee();
			 String busamount = "";
			 if(StringUtils.isNotEmpty(businessAmount)){
				 busamount = BigDecimalUtil.sub("0",businessAmount,2);
			 }
			 if(StringUtils.isNotEmpty(lateFee)){
				 lateFee = BigDecimalUtil.sub("0",lateFee,2);
			 }
			 //交易流水记录
			 BusinessJournalsEntity businessJournals = new BusinessJournalsEntity();
			 BeanUtils.copyProperties(businessJournalsEntity,businessJournals);
			 businessJournals.setId(businessJournalsId);
			 businessJournals.setTransId(revokeTransId);
			 businessJournals.setBusinessType("E081");
			 businessJournals.setBusinessAmount(busamount);
			 businessJournals.setLateFee(lateFee);
			 iBusinessJournalsDao.insert(businessJournals);
			 //撤销业务扩展记录流水
			 QueryWrapper<BusinessExtendEntity> businessExtendEntityQueryWrapper = new QueryWrapper<>();
			 businessExtendEntityQueryWrapper.lambda().eq(BusinessExtendEntity :: getBusinessId,businessId2);
			 BusinessExtendEntity businessExtendEntity = iBusinessExtendDao.selectOne(businessExtendEntityQueryWrapper);
			 if(businessExtendEntity!=null){
				 //业务流水扩展表
				 BusinessExtendEntity businessExtend = new BusinessExtendEntity();
				 BeanUtils.copyProperties(businessExtendEntity,businessExtend);
				 String businessExtendId =  UUIDFactory.createId();
				 businessExtend.setId(businessExtendId);
				 businessExtend.setTransId(revokeTransId);
				 businessExtend.setBusinessId(businessJournalsId);
				 iBusinessExtendDao.insert(businessExtend);
			 }
		 }
		//根据流水编号信息去扣除相应的子账单和主账单的已收金额(过滤非周期性收费)
		List<BusinessJournalsEntity> businessJournalsEntities = businessJournalsEntityList.stream().filter(k->StringUtils.isNotEmpty(k.getBillDetialNo())).collect(Collectors.toList());
	    for (BusinessJournalsEntity businessJournalsEntity : businessJournalsEntities) {
			// 主账单的编号，可能查询出来的对应多个主账单
			String billNo = businessJournalsEntity.getBillNo();
			String billDetailNo = businessJournalsEntity.getBillDetialNo();
			String businessAmount = businessJournalsEntity.getBusinessAmount();

			QueryWrapper<BillDetailsEntity> detailsEntityQueryWrapper = new QueryWrapper<>();
			detailsEntityQueryWrapper.lambda().eq(BillDetailsEntity :: getDetailNo,billDetailNo);
			// 查询子账单信息
			BillDetailsEntity billDetails = iBillDetailsDao.selectOne(detailsEntityQueryWrapper);

			//子账单已收的金额
			String detailRecivedAmount = billDetails.getReceivedAmount();
			String newRecivedAmount = BigDecimalUtil.sub(detailRecivedAmount,businessAmount,2);
			log.debug("更新子账单信息" + billDetailNo + "旧:" + detailRecivedAmount + "新:" + newRecivedAmount);
			BillDetailsEntity billDetailsEntity = new BillDetailsEntity();
			billDetailsEntity.setReceivedAmount(newRecivedAmount);
			billDetailsEntity.setUpdateTime(date);
			int detailStatus = BigDecimalUtil.compareToOther("0",newRecivedAmount);
			if(detailStatus == 0){
				billDetailsEntity.setChargeStatus("01");
			}else if(detailStatus == -1){
				billDetailsEntity.setChargeStatus("04");
			}else if(detailStatus == 1){
				throw new ServiceException("账单异常");
			}
			iBillDetailsDao.update(billDetailsEntity,detailsEntityQueryWrapper);
			QueryWrapper<PayBillsEntity> payBillsEntityQueryWrapper = new QueryWrapper<>();
			payBillsEntityQueryWrapper.lambda().eq(PayBillsEntity :: getBillNo,billNo);
			PayBillsEntity payBills = iPayBillsDao.selectOne(payBillsEntityQueryWrapper);
			String receivedAmount = payBills.getReceivedAmount();
			String newReceivedAmount = BigDecimalUtil.sub(receivedAmount,businessAmount,2);
			PayBillsEntity payBillsEntity = new PayBillsEntity();
			payBillsEntity.setReceivedAmount(newReceivedAmount);
			payBillsEntity.setUpdateTime(date);
			int billStatus = BigDecimalUtil.compareToOther("0",newReceivedAmount);
			if(billStatus == 0){
				payBillsEntity.setBillStatus("01");
			}else if(billStatus == -1){
				payBillsEntity.setBillStatus("03");
			}else if(billStatus == 1){
				throw new ServiceException("账单异常");
			}

			iPayBillsDao.update(payBillsEntity,payBillsEntityQueryWrapper);

	    }

		 return createSuccessResult(null);
	 }

	private void revokeTransJournals()  {

	}
	public String getNextId(String preSuffix){
		try {
			return iTransJournalsDao.getNextId(preSuffix);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}


}
