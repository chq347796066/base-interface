package com.spring.pay.service.impl;

import com.spring.base.entity.pay.TransJournalsEntity;
import com.spring.base.vo.pay.transjournals.*;
import com.spring.common.request.RequestUtils;
import com.spring.common.util.arithmetic.BigDecimalUtil;
import com.spring.common.util.timeconversion.DateUtil;
import com.spring.common.util.timeconversion.TimeUtil;
import com.spring.pay.dao.ITransJournalsDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.pay.service.ITransJournalsService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 缴费记录业务接口实现类
 */

 @Slf4j
@Service("transJournalsService")
public class TransJournalsServiceImpl extends BaseServiceImpl<TransJournalsEntity, String> implements ITransJournalsService {
	
	@Autowired
	private ITransJournalsDao transJournalsDao;

	private static final String STYLE = "yyyyMMddHHmmss";
	 private static final String STYLE_DATE = "yyyyMMdd";

	@Override
	public BaseDao getBaseMapper() {
		return transJournalsDao;
	}
	
	/**
	 * 新增缴费记录
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult addTransJournals(TransJournalsAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		TransJournalsEntity entity = new TransJournalsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setStatus(1);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = transJournalsDao.insert(entity);
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
	 * 更新缴费记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult updateTransJournals(TransJournalsUpdateVo vo) throws Exception {
		TransJournalsEntity entity = new TransJournalsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = transJournalsDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}


	 @Override
	 public ApiResponseResult queryTodayFees(TodayPramVo todayPramVo) throws Exception {
		 //总收入
		 String currentDate = TimeUtil.getCurrentDate(STYLE);
		 String firstCurrentDate = TimeUtil.getCurrentDate(STYLE_DATE)+"000000";
		 String yesterdayStartDate = TimeUtil.getLastDay(TimeUtil.getCurrentDate()).replace("-", "")+"000000";
		 String yesterdayEndDate = TimeUtil.getLastDay(TimeUtil.getCurrentDate()).replace("-", "")+"235959";
		 String fristDayOfCurrentMonth  = DateUtil.getFristDayOfCurrentMonth(new Date())+"000000";
		 TotalIncome totalIncome = new TotalIncome();
		 totalIncome.setPcid(todayPramVo.getPcid());
		 totalIncome.setCurrentDate(currentDate);
		 totalIncome.setFirstCurrentDate(firstCurrentDate);
		 totalIncome.setYesterdayStartDate(yesterdayStartDate);
		 totalIncome.setYesterdayEndDate(yesterdayEndDate);
		 List<TodayIncomeResult> totalIncome1 = transJournalsDao.getTotalIncome(totalIncome);
		 StringBuilder todayTransAmount = new StringBuilder();
		 StringBuilder yesterdayTransAmount = new StringBuilder();
		 if(CollectionUtils.isNotEmpty(totalIncome1)){
			 totalIncome1.forEach(k->{
				 if("today".equals(k.getDays())){
					 todayTransAmount.append(StringUtils.isEmpty(k.getTransAmount()) ? "0" : k.getTransAmount());
				 }
				 if("yesterday".equals(k.getDays())){
					 yesterdayTransAmount.append(StringUtils.isEmpty(k.getTransAmount()) ? "0" : k.getTransAmount());
				 }
			 });
		 }

		 TodayResult result = new TodayResult();

		 String incomeRate = "0";
		 if(!"0".equals(todayTransAmount.toString()) && !"0".equals(yesterdayTransAmount.toString())){
		 	incomeRate = BigDecimalUtil.mul(BigDecimalUtil.divs(BigDecimalUtil.sub(todayTransAmount.toString(), yesterdayTransAmount.toString(), 2),  yesterdayTransAmount.toString(), 2), "100", 2);
			 int compare = BigDecimalUtil.compareToOther(incomeRate, "0");
			 if(compare==-1){
				 incomeRate = "-"+incomeRate+"%";
			 }else if(compare == 1){
				 incomeRate = "+"+incomeRate+"%";
			 }
		 }
		 result.setTotalIncome(todayTransAmount.toString());
		 result.setIncomeRate(incomeRate);

		 //收费最高小区
		 TotalIncome highTotal = new TotalIncome();
		 highTotal.setPcid(todayPramVo.getPcid());
		 highTotal.setFirstCurrentDate(firstCurrentDate);
		 TodayResult result1 = transJournalsDao.highestPlotIncome(highTotal);
		 result.setHighestPlotIncome(result1 == null ? "0" : result1.getHighestPlotIncome());
		 result.setHighestPlotName(result1 == null ? "" : result1.getHighestPlotName());

		 //总收费订单
		 TotalIncome totalCharge = new TotalIncome();
		 totalCharge.setPcid(todayPramVo.getPcid());
		 totalCharge.setFirstCurrentDate(firstCurrentDate);
		 totalCharge.setYesterdayStartDate(yesterdayStartDate);
		 totalCharge.setYesterdayEndDate(yesterdayEndDate);
		 StringBuilder todayChargeOrder = new StringBuilder();
		 StringBuilder yesterdayChargeOrder = new StringBuilder();
		 List<TodayIncomeResult> totalChargeOrder = transJournalsDao.getTotalChargeOrder(totalCharge);
		 if(CollectionUtils.isNotEmpty(totalChargeOrder)){
			 totalChargeOrder.forEach(k->{
				 if("today".equals(k.getDays())){
					 todayChargeOrder.append(StringUtils.isEmpty(k.getTransAmount())?"0":k.getTransAmount());
				 }
				 if("yesterday".equals(k.getDays())){
					 yesterdayChargeOrder.append(StringUtils.isEmpty(k.getTransAmount())?"0":k.getTransAmount());
				 }
			 });
		 }
		 String totalChargeOrderRate = "0";
		 if(!"0".equals(todayChargeOrder.toString()) && !"0".equals(yesterdayChargeOrder.toString())){
			 totalChargeOrderRate = BigDecimalUtil.mul(BigDecimalUtil.divs(BigDecimalUtil.sub(todayChargeOrder.toString(), yesterdayChargeOrder.toString(), 2),  yesterdayChargeOrder.toString(), 2), "100", 2);
			 int compareOrder = BigDecimalUtil.compareToOther(totalChargeOrderRate, "0");
			 if(compareOrder==-1){
				 totalChargeOrderRate = "-"+totalChargeOrderRate+"%";
			 }else if(compareOrder == 1){
				 totalChargeOrderRate = "+"+totalChargeOrderRate+"%";
			 }
		 }
		 result.setHighestChannelMoney(todayChargeOrder.toString());
		 result.setHighestChannel(totalChargeOrderRate);

		 //收费最多渠道
		 TotalIncome highestChannel = new TotalIncome();
		 highestChannel.setPcid(todayPramVo.getPcid());
		 highestChannel.setFirstCurrentDate(firstCurrentDate);
		 TodayResult highest = transJournalsDao.getHighestChannel(highestChannel);
		 result.setHighestChannelMoney( highest == null ? "0" : highest.getHighestChannelMoney());
		 result.setHighestChannel(highest == null ? "" : highest.getHighestChannel());

		 return createSuccessResult(result);
	 }
 }
