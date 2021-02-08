package com.spring.baseinfo.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.community.CommunityStatisticsVo;
import com.spring.base.vo.baseinfo.home.HomeVo;
import com.spring.baseinfo.dao.ICommunityDao;
import com.spring.baseinfo.service.IHomeStatsReportingService;
import com.spring.common.constants.MessageCode;
import com.spring.common.feign.client.BusinessFeignClient;
import com.spring.common.feign.client.MaintenanceFeignClient;
import com.spring.common.response.ApiResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Desc:  统计首页工单管理状况
 * @Author:邓磊
 * @UpdateDate:2020/4/29 9:59
 * @return: 返回
 */
@Slf4j
@Service("homeStatsReportingService")
public class HomeStatsReportingServiceImpl extends BaseServiceImpl<HomeVo, String> implements IHomeStatsReportingService {
    @Autowired
    private MaintenanceFeignClient reportingRepaiFeignCilnet;
	@Autowired
	private ICommunityDao communityDao;

	@Override
	public BaseDao getBaseMapper() {
		return null;
	}

	/**
	 * @Desc: 工单统计
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/29 10:07
	 * @return: 返回
	 */
	@Override
	public ApiResponseResult statisticsWorkOrder(HomeVo vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		HomeVo homeVo = new HomeVo();
		String companyId = vo.getCompanyId();
		ReportingRepairEntity repairEntity  = new ReportingRepairEntity();
		List<ReportingRepairEntity> repairList = reportingRepaiFeignCilnet.queryReporList(repairEntity);
		Integer sum = 0;
		Integer fulfilnNumber = 0;
		Integer waitingNumber = 0;
		Integer stayNumber = 0;
		Integer processingNumber = 0;
		if(repairList.size() >0){
			if(CollectionUtils.isNotEmpty(repairList)){
				for(int i=0;i<repairList.size();i++){
					ReportingRepairEntity repairEntity1 = repairList.get(i);
					CommunityEntity comm = new CommunityEntity();
					comm.setId(repairEntity1.getCommunityId());
					CommunityEntity entity = communityDao.queryCommunity(comm);
					//报修状态（1 待派单 2 待接单 3 处理中　４待确认 5 已完成 6 已评价 7 已取消）
					if(entity !=null && entity.getCompanyId().equals(companyId)){
						if(7 !=repairEntity1.getReportingStatus()){
							sum++;
						}
						if(5 == repairEntity1.getReportingStatus()){
							fulfilnNumber++;
						}else if(1 == repairEntity1.getReportingStatus()){
							waitingNumber++;
						}else if(2 == repairEntity1.getReportingStatus()){
							stayNumber++;
						}if(3 == repairEntity1.getReportingStatus()) {
							processingNumber++;
						}if(4 == repairEntity1.getReportingStatus()){
							processingNumber++;
						}
					}
				}
			}
		}
		if(null != fulfilnNumber && fulfilnNumber != 0 && null != sum && sum != 0){
			BigDecimal money = new BigDecimal(100);
			BigDecimal bigNumber = new BigDecimal(fulfilnNumber);
			BigDecimal bigSum = new BigDecimal(sum);
			BigDecimal completionRate = bigNumber.divide(bigSum,2, BigDecimal.ROUND_HALF_UP).multiply(money);
			BigDecimal processing = (bigSum.subtract(bigNumber)).divide(bigSum,2, BigDecimal.ROUND_HALF_UP).multiply(money);
			homeVo.setCompletionRate(completionRate);
			homeVo.setProcessing(processing);
		}
		homeVo.setSum(sum);
		homeVo.setFulfilnNumber(fulfilnNumber);
		homeVo.setWaitingNumber(waitingNumber);
		homeVo.setStayNumber(stayNumber);
		homeVo.setProcessingNumber(processingNumber);
		result.setCode(MessageCode.SUCCESS);
		result.setMsg(MessageCode.SUCCESS_TEXT);
		result.setData(homeVo);
		return result;
	}


    /**
     * @Desc: 管理状况统计
     * @Author:邓磊
     * @UpdateDate:2020/5/12 10:43
     */
	@Override
	public ApiResponseResult managementOverview(CommunityStatisticsVo vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		CommunityEntity entity = new CommunityEntity();
		entity.setCompanyId(vo.getCompanyId());
		if(entity !=null){
			List<CommunityEntity> communityList = communityDao.queryList(entity);
			Integer communityTotal = 0;
			Integer  allHouseholds = 0;
			Integer architectureArea = 0;
			if(CollectionUtils.isNotEmpty(communityList)){
				communityTotal = communityList.size();
				for(int i=0;i<communityList.size();i++){
					if(null !=communityList.get(i).getAllHouseholds() && communityList.get(i).getAllHouseholds()>0){
						allHouseholds+=communityList.get(i).getAllHouseholds();
					}
					if(null != communityList.get(i).getArchitectureArea() && communityList.get(i).getArchitectureArea().intValue()>0){
						architectureArea += communityList.get(i).getArchitectureArea().intValue();
					}
				}
			}
			vo.setCommunityTotal(communityTotal);
			vo.setAllHouseholds(allHouseholds);
			vo.setArchitectureArea(architectureArea);
		}
		result.setCode(MessageCode.SUCCESS);
		result.setMsg(MessageCode.SUCCESS_TEXT);
		result.setData(vo);
		return result;
	}



	public <E> void cpMap(List<E> list, List<LinkedHashMap> map,Class clazz) throws Exception {
		for (LinkedHashMap linkedHashMap : map) {
			Object o1 = clazz.newInstance();
			Field[] declaredFields = clazz.getDeclaredFields();
			for (Field declaredField : declaredFields) {
				String name = declaredField.getName();
				Object o = linkedHashMap.get(name);
				declaredField.setAccessible(true);
				if ("java.math.BigDecimal".equals(declaredField.getType().getName()) && StringUtils.isNotEmpty(declaredField.getType().getName()) &&  o != null){
					BigDecimal bigDecimal = new BigDecimal((Integer) o);
					declaredField.set(o1,bigDecimal);
					continue;
				}
				if(o != null) {
					declaredField.set(o1,o);
				}
			}
			list.add((E) o1);
		}
	}



}
