package com.spring.baseinfo.service;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.community.CommunityStatisticsVo;
import com.spring.base.vo.baseinfo.home.HomeVo;
import com.spring.common.response.ApiResponseResult;

/**
 * @Desc:  统计首页工单
 * @Author:邓磊
 * @UpdateDate:2020/4/29 9:59
 * @return: 返回
 */
public interface IHomeStatsReportingService extends IBaseService<HomeVo,String> {


	/**
	 * @Desc: 首页工单统计
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/29 10:03
	 */
	 ApiResponseResult statisticsWorkOrder(HomeVo vo) throws Exception;
	 /**
	  * @Desc:    首页管理状况
	  * @param vo
	  * @Author:邓磊
	  * @UpdateDate:2020/5/12 10:36
	  * @return: 返回
	  */
	  ApiResponseResult managementOverview(CommunityStatisticsVo vo) throws Exception;
}
