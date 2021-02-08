package com.spring.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.account.SubAccountEntity;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.entity.pay.*;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.pay.billdetail.BillDetailParamVo;
import com.spring.base.vo.pay.businessjournals.BusinessJournalsAddVo;
import com.spring.base.vo.pay.businessjournals.BusinessJournalsUpdateVo;
import com.spring.base.vo.pay.payonekeypay.*;
import com.spring.base.vo.pay.queryrecord.QueryRecordVo;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.exception.ServiceException;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.sequence.BusinessCodeConstant;
import com.spring.common.util.DateUtils;
import com.spring.common.util.SnowflakeIdWorker;
import com.spring.common.util.arithmetic.BigDecimalUtil;
import com.spring.common.util.id.UUIDFactory;
import com.spring.pay.dao.*;
import com.spring.pay.feign.PayAccountFeignClient;
import com.spring.pay.service.IBusinessJournalsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 交流流水记录业务接口实现类
 */

@Slf4j
@Service("businessJournalsService")
public class BusinessJournalsServiceImpl extends BaseServiceImpl<BusinessJournalsEntity, String> implements IBusinessJournalsService {
	

	@Autowired
	private IBillDetailsDao billDetailsDao;
	@Autowired
	private IPayBillsDao payBillsDao;
	@Autowired
	private IBusinessJournalsDao businessJournalsDao;
	@Autowired
	private IBusinessExtendDao businessExtendDao;
	@Autowired
	private IDisttransJournalsDao disttransJournalsDao;
	@Autowired
	private ITransJournalsDao transJournalsDao;
	@Autowired
	private ITranspayJournalsDao transpayJournalsDao;
	private static final String STYLE = "yyyyMMddHHmmss";
	@Autowired
	private BaseInfoFeignClient companyInfoFeignClient;
	@Autowired
	private PayAccountFeignClient accountFeignClient;

	@Override
	public BaseDao getBaseMapper() {
		return businessJournalsDao;
	}
	
	/**
	 * 新增交流流水记录
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult addBusinessJournals(BusinessJournalsAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		BusinessJournalsEntity entity = new BusinessJournalsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setStatus(1);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = businessJournalsDao.insert(entity);
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
	 * 更新交流流水记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult updateBusinessJournals(BusinessJournalsUpdateVo vo) throws Exception {
		BusinessJournalsEntity entity = new BusinessJournalsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = businessJournalsDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}



	 @Override
	 //@GlobalTransactional(timeoutMills = 600000,rollbackFor = Exception.class)
	 public ApiResponseResult payOneKeyPay(PayOneVo vo) throws Exception {
		 Date date = new Date();//开始时间
		 vo.setDate(date);
		 log.error(">>>>>>>>>>>>交易执行开始时间："+date);
		 CompanyEntity companyInfo = companyInfoFeignClient.queryCompanyEntity(vo.getCompanyId());
		 vo.setCompanyName(companyInfo.getCompanyName());
		 CommunityEntity community = companyInfoFeignClient.queryCommunityEntity(vo.getPlotId());
		 vo.setPlotName(community.getCommunityName());
		 String transId = "TRADE"+SnowflakeIdWorker.generateId();
		 vo.setTransId(transId);

		 //账单收费
		 List<BillDetailVo> billDetailVos = vo.getBillDetailVos();
		 if(CollectionUtils.isNotEmpty(billDetailVos)){
			 payBill(vo,billDetailVos);
		 }

		 //临时收费
		 List<TemporaryPayVo> temporaryPayVos = vo.getTemporaryPayVos();
		 if(CollectionUtils.isNotEmpty(temporaryPayVos)){
			 payTemporary(vo,temporaryPayVos);
		 }

		 //String xid = RootContext.getXID();
		 //log.error("xid:"+xid);

		 //综合预缴
		 List<PrePayVo> prePayVos = vo.getPrePayVos();
		 if(CollectionUtils.isNotEmpty(prePayVos)){
			 prePay(vo,prePayVos);
			 accountFeignClient.createPrePay(vo);
		 }


		 //缴费记录表
		 List<TransJournalsEntity> transJournalsEntities = new ArrayList<>();
		 //支付记录表
		 List<TranspayJournalsEntity> transpayJournalsEntities = new ArrayList<>();

		 String transJournalsId = SnowflakeIdWorker.generateId()+"";
		 String transpayJournalsId = SnowflakeIdWorker.generateId()+"";
		 String actualAmount = vo.getActualAmount();
		 //缴费记录表
		 TransJournalsEntity transJournals = new TransJournalsEntity();
		 transJournals.setId(transJournalsId);
		 transJournals.setTransId(transId);
		 transJournals.setCid(vo.getPlotId());
		 transJournals.setCname(vo.getPlotName());
		 transJournals.setCompanyId(vo.getCompanyId());
		 transJournals.setCompanyName(vo.getCompanyName());
		 transJournals.setTerminIdentify(vo.getTerminIdentify());
		 if(vo.getPayDate()!=null){
			 transJournals.setTransTime(vo.getPayDate());
		 }else {
			 transJournals.setTransTime(date);
		 }
		 transJournals.setTransStatus("03");
		 transJournals.setUpdateTime(date);
		 transJournals.setPhone(vo.getPhone());
		 transJournals.setChargeMark(vo.getChargeMark());
		 transJournals.setStatus(Constants.Status.ENABLE);
		 transJournals.setTransAmount(actualAmount);
		 transJournals.setTenantId(RequestUtils.getTenantId());
		 transJournals.setCreateUser(RequestUtils.getUserId());
		 transJournals.setCreateDate(date);
		 transJournals.setObservation(0);
		 transJournals.setOperatorName(vo.getOperatorName());
		 transJournals.setOperatorNo(vo.getOperatorNo());
		 transJournals.setHouseCode(vo.getHouseCode());

		 transJournalsEntities.add(transJournals);

		 //支付记录表
		 TranspayJournalsEntity	transpayJournals = new TranspayJournalsEntity();
		 transpayJournals.setId(transpayJournalsId);
		 transpayJournals.setTransId(transId);
		 transpayJournals.setPaytype(vo.getPaytype());
		 transpayJournals.setRecordCompanyId(vo.getCompanyId());
		 transpayJournals.setRecordCompanyName(vo.getCompanyName());
		 transpayJournals.setUpdateTime(date);
		 transpayJournals.setStatus(Constants.Status.ENABLE);
		 transpayJournals.setTenantId(RequestUtils.getTenantId());
		 transpayJournals.setCreateUser(RequestUtils.getUserId());
		 transpayJournals.setCreateDate(date);
		 transpayJournals.setObservation(0);

		 transpayJournalsEntities.add(transpayJournals);

		 //存入数据库
		 try {
			 businessJournalsDao.addList(vo.getBusinessJournalsEntities());
			 businessExtendDao.addList(vo.getBusinessExtendEntities());
			 transJournalsDao.addList(transJournalsEntities);
			 transpayJournalsDao.addList(transpayJournalsEntities);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }

		 Date endDate = new Date();
		 log.error(">>>>>>>>>>>>程序执行结束时间：" + endDate);
		 log.error("------------交易结束,用时：" + DateUtils.timeDifAndHumanize(date, endDate));

		 return createSuccessResult(vo.getTransId());
	 }

	 //综合预缴
	 private void prePay(PayOneVo vo,List<PrePayVo> prePayVos) throws Exception{
		 Date date = vo.getDate();
		 String transId = vo.getTransId();
		 //账户号
		 for(PrePayVo temporaryPayVo : prePayVos){
			 //交易流水记录
			 BusinessJournalsEntity businessJournals = new BusinessJournalsEntity();
			 String businessJournalsId = SnowflakeIdWorker.generateId()+"";
			 businessJournals.setId(businessJournalsId);
			 businessJournals.setTransId(transId);
			 String businessAmount = temporaryPayVo.getUnpaidAmount();
			 businessJournals.setBusinessAmount(businessAmount);
			 businessJournals.setBusinessType("综合预缴");
			 businessJournals.setHid(vo.getHid());
			 if(temporaryPayVo.getUpdateTime()==null){
				 businessJournals.setUpdateTime(date);
			 }else {
				 businessJournals.setUpdateTime(temporaryPayVo.getUpdateTime());
			 }

			 businessJournals.setRoomNo(vo.getHouseName());
			 businessJournals.setStatus(Constants.Status.ENABLE);
			 businessJournals.setTenantId(RequestUtils.getTenantId());
			 businessJournals.setCreateUser(RequestUtils.getUserId());
			 businessJournals.setCreateDate(date);
			 businessJournals.setObservation(0);
			 businessJournals.setPname(vo.getOwnerName());
			 businessJournals.setPid(vo.getOwnerNo());
			 businessJournals.setPaymentAmount(businessAmount);

			 vo.getBusinessJournalsEntities().add(businessJournals);

			 //业务流水扩展表
			 BusinessExtendEntity businessExtend = new BusinessExtendEntity();
			 String businessExtendId =  SnowflakeIdWorker.generateId()+"";
			 businessExtend.setId(businessExtendId);
			 businessExtend.setBusinessId(businessJournalsId);
			 businessExtend.setTransId(transId);
			 businessExtend.setChargeWay("");
			 businessExtend.setArea(vo.getArea());
			 businessExtend.setHouseType(vo.getHouseType());
			 businessExtend.setHouseStatus(vo.getHouseStatus());
			 businessExtend.setUpdateTime(date);
			 businessExtend.setStatus(Constants.Status.ENABLE);
			 businessExtend.setTenantId(RequestUtils.getTenantId());
			 businessExtend.setCreateUser(RequestUtils.getUserId());
			 businessExtend.setCreateDate(date);
			 businessExtend.setObservation(0);

			 vo.getBusinessExtendEntities().add(businessExtend);
		 }

	 }



	 //临时缴费
	 private void payTemporary(PayOneVo vo,List<TemporaryPayVo> temporaryPayVos) throws Exception{
		 Date date = vo.getDate();
		 String transId = vo.getTransId();


		 	for(TemporaryPayVo temporaryPayVo : temporaryPayVos){
				//交易流水记录
				BusinessJournalsEntity businessJournals = new BusinessJournalsEntity();
				String businessJournalsId = SnowflakeIdWorker.generateId()+"";
				businessJournals.setId(businessJournalsId);
				businessJournals.setTransId(transId);
				String businessAmount = temporaryPayVo.getUnpaidAmount();
				businessJournals.setBusinessAmount(businessAmount);
				businessJournals.setBusinessType("临时缴费");
				businessJournals.setChargeName(temporaryPayVo.getChargeName());
				businessJournals.setChargeNo(temporaryPayVo.getChargeNo());
				businessJournals.setChargeType(temporaryPayVo.getChargeType());
				businessJournals.setChargeTypeName(temporaryPayVo.getChargeTypeName());
				businessJournals.setHid(vo.getHid());
				businessJournals.setUpdateTime(date);
				businessJournals.setRoomNo(vo.getHouseName());
				businessJournals.setStatus(Constants.Status.ENABLE);
				businessJournals.setTenantId(RequestUtils.getTenantId());
				businessJournals.setCreateUser(RequestUtils.getUserId());
				businessJournals.setCreateDate(date);
				businessJournals.setObservation(0);
				businessJournals.setPname(vo.getOwnerName());
				businessJournals.setPid(vo.getOwnerNo());
				businessJournals.setPaymentAmount(businessAmount);

				vo.getBusinessJournalsEntities().add(businessJournals);

				//业务流水扩展表
				BusinessExtendEntity businessExtend = new BusinessExtendEntity();
				String businessExtendId =  SnowflakeIdWorker.generateId()+"";
				businessExtend.setId(businessExtendId);
				businessExtend.setBusinessId(businessJournalsId);
				businessExtend.setTransId(transId);
				businessExtend.setChargeWay(temporaryPayVo.getChargeWay());
				businessExtend.setArea(vo.getArea());
				businessExtend.setHouseType(vo.getHouseType());
				businessExtend.setHouseStatus(vo.getHouseStatus());
				businessExtend.setStartDate(temporaryPayVo.getStartDate());
				businessExtend.setEndDate(temporaryPayVo.getEndDate());
				businessExtend.setUpdateTime(date);
				businessExtend.setNum(temporaryPayVo.getNum());
				businessExtend.setUnitPrice(temporaryPayVo.getUnitPrice());
				businessExtend.setStatus(Constants.Status.ENABLE);
				businessExtend.setTenantId(RequestUtils.getTenantId());
				businessExtend.setCreateUser(RequestUtils.getUserId());
				businessExtend.setCreateDate(date);
				businessExtend.setObservation(0);

				vo.getBusinessExtendEntities().add(businessExtend);
			}
	 }


	 //账单缴费
	private void payBill(PayOneVo vo,List<BillDetailVo> billDetailVos) throws Exception {
			Date date = vo.getDate();
			String transId = vo.getTransId();
			//优惠流水记录
			List<DisttransJournalsEntity> disttransJournalsEntities = new ArrayList<>();

			//实收金额
			String actualAmount = vo.getActualAmount();
			//数据库得到的实际欠费金额
			String databaseActualAmount = "0";
			//非周期性费用
			String aperiodicityAmount = "0";
			//周期性费用
			String recurringAmount = "0";
			for(BillDetailVo billDetailVo : billDetailVos){
				if(!"periodicity".equals(billDetailVo.getFeeCategory())){
					//非周期性费用未缴金额
					String unpaidAmount = billDetailVo.getUnpaidAmount();
					aperiodicityAmount = BigDecimalUtil.add(aperiodicityAmount, unpaidAmount, 2);
				}else {
					QueryWrapper<BillDetailsEntity> queryWrapper = new QueryWrapper<>();
					queryWrapper.lambda().eq(BillDetailsEntity::getDetailNo,billDetailVo.getDetailNo());
					BillDetailsEntity billDetailsEntity = billDetailsDao.selectOne(queryWrapper);
					//账单详情之待缴金额
					String paidAmount = billDetailsEntity.getPaymentAmount();
					recurringAmount = BigDecimalUtil.add(recurringAmount,paidAmount,2);
					//1代表收取滞纳金
					if("1".equals(vo.getIscollDelayPay())){
						String dPenalty = billDetailsEntity.getDPenalty();
						recurringAmount = BigDecimalUtil.add(recurringAmount,dPenalty,2);
					}
				}
			}
			//（实收金额-非周期性费项金额的汇总）
			int number = BigDecimalUtil.compareToOther(actualAmount, aperiodicityAmount);

			if(number==-1){
				throw new ServiceException("实收金额小于非周期性费项金额的汇总！");
			}
			//账单缴费可用金额 = 实收金额-非周期性费项金额
			String periodicityAmount = BigDecimalUtil.sub(actualAmount, aperiodicityAmount, 2);
			if(BigDecimalUtil.compareToOther(periodicityAmount,recurringAmount)!=0 && "1".equals(vo.getIscollDelayPay())){
				throw new ServiceException("有滞纳金暂不支持部分缴费！");
			}


			//根据时间倒序
			//一次性
			List<BillDetailVo>	 disposableList =  billDetailVos.stream().filter(k->!"periodicity".equals(k.getFeeCategory())).collect(Collectors.toList());
			//周期性
			List<BillDetailVo> periodicityList = billDetailVos.stream().filter(k->"periodicity".equals(k.getFeeCategory())).sorted(Comparator.comparing(BillDetailVo::getStartDate).reversed()).collect(Collectors.toList());

			//一次性
			if(CollectionUtils.isNotEmpty(disposableList)){
				for(BillDetailVo billDetailVo : disposableList){
					//交易流水记录
					BusinessJournalsEntity businessJournals = new BusinessJournalsEntity();
					String businessJournalsId = SnowflakeIdWorker.generateId()+"";
					businessJournals.setId(businessJournalsId);
					businessJournals.setTransId(transId);
					businessJournals.setBusinessAmount(billDetailVo.getUnpaidAmount());
					businessJournals.setBusinessType(billDetailVo.getFeeCategory());
					businessJournals.setChargeName(billDetailVo.getChargeName());
					businessJournals.setChargeNo(billDetailVo.getChargeNo());
					businessJournals.setChargeType(billDetailVo.getChargeType());
					businessJournals.setChargeTypeName(billDetailVo.getChargeTypeName());
					businessJournals.setHid(vo.getHid());
					businessJournals.setLateFee(billDetailVo.getDPenalty());
					businessJournals.setUpdateTime(date);
					businessJournals.setRoomNo(vo.getHouseName());
					businessJournals.setStatus(Constants.Status.ENABLE);
					businessJournals.setTenantId(RequestUtils.getTenantId());
					businessJournals.setCreateUser(RequestUtils.getUserId());
					businessJournals.setCreateDate(date);
					businessJournals.setObservation(0);
					businessJournals.setPname(vo.getOwnerName());
					businessJournals.setPid(vo.getOwnerNo());
					businessJournals.setPaymentAmount(billDetailVo.getPaymentAmount());

					vo.getBusinessJournalsEntities().add(businessJournals);

					//业务流水扩展表
					BusinessExtendEntity businessExtend = new BusinessExtendEntity();
					String businessExtendId =  SnowflakeIdWorker.generateId()+"";
					businessExtend.setId(businessExtendId);
					businessExtend.setBusinessId(businessJournalsId);
					businessExtend.setTransId(transId);
					businessExtend.setChargeWay(billDetailVo.getChargeWay());
					businessExtend.setArea(vo.getArea());
					businessExtend.setHouseType(vo.getHouseType());
					businessExtend.setHouseStatus(vo.getHouseStatus());
					businessExtend.setStartDate(billDetailVo.getStartDate());
					businessExtend.setEndDate(billDetailVo.getEndDate());
					businessExtend.setUpdateTime(date);
					businessExtend.setNum(billDetailVo.getNum());
					businessExtend.setUnitPrice(billDetailVo.getUnitPrice());
					businessExtend.setStatus(Constants.Status.ENABLE);
					businessExtend.setTenantId(RequestUtils.getTenantId());
					businessExtend.setCreateUser(RequestUtils.getUserId());
					businessExtend.setCreateDate(date);
					businessExtend.setObservation(0);

					vo.getBusinessExtendEntities().add(businessExtend);
				}
			}


			//周期性
			if(CollectionUtils.isNotEmpty(periodicityList)){
				for(BillDetailVo billDetailVo : periodicityList){
					//判断是周期性费用且（实收金额-非周期性费项金额的汇总）>0

					if( number > 0){
						boolean flag = false;

						Map<String, Object> map2 = new HashMap<>(16);
						String chargeNo = billDetailVo.getChargeNo();
						String billNo = billDetailVo.getBillNo();
						if (StringUtils.isNotEmpty(chargeNo)) {
							map2.put("chargeNo", chargeNo);
						}
						map2.put("billNo", billNo);
						map2.put("chargeStatus", "02");
						List<Map<String, String>> detaillist = billDetailsDao.queryPayDetial(map2);
						if(CollectionUtils.isNotEmpty(detaillist)){

							for (Map<String, String> detail : detaillist) {

								//业务流水扩展表
								BusinessExtendEntity businessExtend = new BusinessExtendEntity();

								//交易流水记录
								BusinessJournalsEntity businessJournals = new BusinessJournalsEntity();

								//账单详情表
								BillDetailsEntity updateDetailsEntity = new BillDetailsEntity();

								//账单主表
								PayBillsEntity updateBillEntity = new PayBillsEntity();


								//账单详情之待缴总金额
								String paidAmount = detail.get("paidAmount");
								//账单详情之已收金额
								String receiveAmount = StringUtils.isEmpty(detail.get("receiveAmount")) ? "0" : detail.get("receiveAmount");
								//账单详情之本次代缴金额(欠费金额)
								String debitAmount = BigDecimalUtil.sub(paidAmount,receiveAmount,2);

								//1代表收取滞纳金
								if("1".equals(vo.getIscollDelayPay())){
									String dPenalty = StringUtils.isEmpty(detail.get("penalty")) ? "0.00" : detail.get("penalty");
									periodicityAmount = BigDecimalUtil.sub(periodicityAmount,dPenalty,2);
								}
								//先比较剩余的钱和欠费的钱
								int biStatus = BigDecimalUtil.compareToOther(periodicityAmount,debitAmount);

								//账单之已缴金额
								String receivedAmount = StringUtils.isEmpty(detail.get("receivedAmount")) ? "0" : detail.get("receivedAmount");
								//账单之账单金额
								String billAmount = detail.get("billAmount");

								//账单详情
								QueryWrapper<BillDetailsEntity> queryDetailWrapper=new QueryWrapper<>();
								queryDetailWrapper.lambda().eq(BillDetailsEntity::getDetailNo,billDetailVo.getDetailNo());


								//本次交易金额
								String businessAmount = "0.00";
								if(biStatus == -1){
									//账单详情
									receiveAmount = BigDecimalUtil.add(receiveAmount,periodicityAmount,2);
									//账单主表
									receivedAmount = BigDecimalUtil.add(receivedAmount,periodicityAmount,2);
									//交易流水记录
									businessAmount = periodicityAmount;

									flag = true;
								}else if(biStatus == 1){
									//账单详情
									receiveAmount = BigDecimalUtil.add(receiveAmount,debitAmount,2);
									//账单主表
									receivedAmount = BigDecimalUtil.add(receivedAmount,debitAmount,2);
									//交易流水记录
									businessAmount = debitAmount;

								}else if(biStatus == 0){
									//账单详情
									receiveAmount = BigDecimalUtil.add(receiveAmount,debitAmount,2);
									//账单主表
									receivedAmount = BigDecimalUtil.add(receivedAmount,debitAmount,2);
									//交易流水记录
									businessAmount = debitAmount;

								}
								updateDetailsEntity.setReceivedAmount(receiveAmount);
								updateDetailsEntity.setPaymentDate(date);
								updateDetailsEntity.setUpdateTime(date);
								updateDetailsEntity.setChargeStatus("02");
								//账单主表
								QueryWrapper<PayBillsEntity> queryBillWrapper=new QueryWrapper<>();
								queryBillWrapper.lambda().eq(PayBillsEntity::getBillNo,billDetailVo.getBillNo());


								int billNumberStatus = BigDecimalUtil.compareToOther(billAmount, receivedAmount);
								if(billNumberStatus == 0){
									//已缴
									updateBillEntity.setBillStatus("02");
								}else if(billNumberStatus == 1){
									//未结清
									updateBillEntity.setBillStatus("03");
								}
								updateBillEntity.setReceivedAmount(receivedAmount);
								updateBillEntity.setUpdateTime(date);
								try {
									billDetailsDao.update(updateDetailsEntity,queryDetailWrapper);
									payBillsDao.update(updateBillEntity,queryBillWrapper);
								} catch (Exception e) {
									e.printStackTrace();
									TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
									throw new Exception(e);
								}
								//交易流水记录
								String businessJournalsId = UUIDFactory.createId();
								businessJournals.setId(businessJournalsId);
								businessJournals.setTransId(transId);
								businessJournals.setBillDetialNo(billDetailVo.getDetailNo());
								businessJournals.setBillNo(billDetailVo.getBillNo());
								businessJournals.setBusinessAmount(businessAmount);
								businessJournals.setBusinessType(billDetailVo.getFeeCategory());
								businessJournals.setChargeName(billDetailVo.getChargeName());
								businessJournals.setChargeNo(billDetailVo.getChargeNo());
								businessJournals.setChargeType(billDetailVo.getChargeType());
								businessJournals.setChargeTypeName(billDetailVo.getChargeTypeName());
								businessJournals.setHid(vo.getHid());
								//1代表收取滞纳金
								if("1".equals(vo.getIscollDelayPay())){
									businessJournals.setLateFee(billDetailVo.getDPenalty());
								}
								businessJournals.setRoomNo(vo.getHouseName());
								businessJournals.setStatus(Constants.Status.ENABLE);
								businessJournals.setTenantId(RequestUtils.getTenantId());
								businessJournals.setCreateUser(RequestUtils.getUserId());
								businessJournals.setCreateDate(date);
								businessJournals.setObservation(0);
								businessJournals.setUpdateTime(date);
								businessJournals.setPname(vo.getOwnerName());
								businessJournals.setPid(vo.getOwnerNo());
								businessJournals.setPaymentAmount(billDetailVo.getPaymentAmount());
								vo.getBusinessJournalsEntities().add(businessJournals);

								//业务流水扩展表
								String businessExtendId =  UUIDFactory.createId();
								businessExtend.setId(businessExtendId);
								businessExtend.setBusinessId(businessJournalsId);
								businessExtend.setTransId(transId);
								businessExtend.setChargeWay(billDetailVo.getChargeWay());
								businessExtend.setArea(vo.getArea());
								businessExtend.setHouseType(vo.getHouseType());
								businessExtend.setHouseStatus(vo.getHouseStatus());
								businessExtend.setStartDate(billDetailVo.getStartDate());
								businessExtend.setEndDate(billDetailVo.getEndDate());
								businessExtend.setNum(billDetailVo.getNum());
								businessExtend.setUnitPrice(billDetailVo.getUnitPrice());
								businessExtend.setStatus(Constants.Status.ENABLE);
								businessExtend.setTenantId(RequestUtils.getTenantId());
								businessExtend.setCreateUser(RequestUtils.getUserId());
								businessExtend.setCreateDate(date);
								businessExtend.setObservation(0);
								businessExtend.setUpdateTime(date);

								vo.getBusinessExtendEntities().add(businessExtend);

								periodicityAmount = BigDecimalUtil.sub(periodicityAmount,debitAmount,2);

								//如果当前剩余金额小于此次循环待缴金额，则终止整个循环


							}


						}else {
							throw new ServiceException("账单信息不存在");
						}
						//退出循环
						if(flag){
							break;
						}
					}



				}
			}

	}


	@Override
	public ApiResponseResult payQueryRecordList(RequestPageVO<QueryRecordVo> requestPageVO) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		List<TransJournalsEntity> list = transJournalsDao.payQueryRecordList(requestPageVO.getEntity());
		PageInfo<TransJournalsEntity> pageInfo = new PageInfo<TransJournalsEntity>(list);
		return createSuccessResult(pageInfo);
	}


	/**
	 * @Desc:收费历史记录导出
	 * @param recordVo
	 * @Author:邓磊
	 * @UpdateDate:2020/5/20 14:28
	 * @return: 返回
	 */
	@Override
	public void exportRecordInfo(QueryRecordVo recordVo) {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		List<TransJournalsEntity> list = transJournalsDao.payQueryRecordList(recordVo);
		try{
			excelDownload(response,list);
			log.info("收费历史记录导出数据信息exportRecordInfo"+list);
		}catch (Exception e){
			log.error("收费历史记录导出exportRecordInfo异常"+e);
		}
	}

	@Override
	public ApiResponseResult payBillOffset(BillOffsetVo vo) throws Exception {
		BillDetailParamVo entity = new BillDetailParamVo();
		BeanUtils.copyProperties(vo,entity);
		List<BillDetailsEntity> billDetailsEntities = billDetailsDao.queryAllBills(entity);
		List<SubAccountEntity> subAccountEntities = accountFeignClient.queryPrePayList(vo);
		if(CollectionUtils.isNotEmpty(subAccountEntities)){
			for(SubAccountEntity subAccountEntity:subAccountEntities){
				//得到该房间可冲抵余额
				String currentBalance = subAccountEntity.getCurrentBalance();
				if(CollectionUtils.isNotEmpty(billDetailsEntities)) {
					for (BillDetailsEntity billDetailsEntity : billDetailsEntities) {
						if (subAccountEntity.getHid().equals(billDetailsEntity.getPayBillsEntity().getHid())){

						}
					}
				}
			}


		}


		return null;
	}





	public void excelDownload(HttpServletResponse response,List<TransJournalsEntity> list) throws Exception {
		String[] header = {"订单编号", "管理区", "房屋编号","客户名称","收款金额","收款时间","收款渠道","收款方式","状态"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("收费历史记录信息");
		sheet.setDefaultColumnWidth(15);
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		HSSFRow headrow = sheet.createRow(0);
		for (int i = 0; i < header.length; i++) {
			HSSFCell cell = headrow.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(header[i]);
			cell.setCellValue(text);
			cell.setCellStyle(headerStyle);
		}
		for(int i=0;i<list.size();i++){
			HSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getTransId()));
			row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getCname()));
			row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getHouseCode()));
			if(CollectionUtils.isNotEmpty(list.get(i).getBusinessJournalsEntities())){
				String pname = list.get(i).getBusinessJournalsEntities().get(0).getPname();
				row1.createCell(3).setCellValue(new HSSFRichTextString(StringUtils.isEmpty(pname)==true?"":pname));
			}else{
				row1.createCell(3).setCellValue(new HSSFRichTextString(""));
			}
			row1.createCell(4).setCellValue(new HSSFRichTextString(list.get(i).getTransAmount()));
			Date transTime = list.get(i).getTransTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(transTime);
			row1.createCell(5).setCellValue(new HSSFRichTextString(dateString));
			row1.createCell(6).setCellValue(new HSSFRichTextString(list.get(i).getTerminIdentify()));
			if(null != list.get(i).getTranspayJournalsEntity()) {
				row1.createCell(7).setCellValue(new HSSFRichTextString(StringUtils.isEmpty(list.get(i).getTranspayJournalsEntity().getPaytype())==true?"":list.get(i).getTranspayJournalsEntity().getPaytype()));
			}else{
				row1.createCell(7).setCellValue(new HSSFRichTextString(""));
			}
			switch(list.get(i).getTransStatus()){
				case "00":
					row1.createCell(8).setCellValue(new HSSFRichTextString("未明"));
					break;
				case "01":
					row1.createCell(8).setCellValue(new HSSFRichTextString("未支付"));
					break;
				case "02":
					row1.createCell(8).setCellValue(new HSSFRichTextString("待复核"));
					break;
				case "03":
					row1.createCell(8).setCellValue(new HSSFRichTextString("完成"));
					break;
				case "04":
					row1.createCell(8).setCellValue(new HSSFRichTextString("撤销"));
					break;
				case "05":
					row1.createCell(8).setCellValue(new HSSFRichTextString("失败"));
					break;
				default:
					row1.createCell(8).setCellValue(new HSSFRichTextString("复核失败"));
			}
		}
		String name = "收费历史记录信息";
		response.setHeader("content-Type","application/ms-excel");
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}


	public String getNextId(BusinessCodeConstant bsCode, String refId){
		switch (bsCode) {
			case SUB_CHARGE_NO:
				return getNextId(bsCode.getBsCode(), refId);
			default :
				return getNextId(bsCode.getBsCode());
		}
	}

	public String getNextId(String preSuffix){
		try {
			return transJournalsDao.getNextId(preSuffix);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public String getNextId(String preSuffix, String refId){
		try {
			if (StringUtil.isEmpty(refId)) {
			}
			Map<String, String> params = new HashMap<>(16);
			params.put("refId", refId);
			params.put("bsCode", preSuffix);
			return transJournalsDao.getNextRid(params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}


 }
