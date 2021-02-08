package com.spring.maintenance.service;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairAddVo;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairUpdateVo;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 16:03:31
 * @Desc类说明: 业主报事报修业务接口类
 */
public interface IReportingRepairService extends IBaseService<ReportingRepairEntity,String>{
	
	/**
	 * 新增业主报事报修
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-11 16:03:31
	 */
    ApiResponseResult addReportingRepair(ReportingRepairAddVo vo) throws Exception;
	
	/**
	 * 更业主报事报修
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-11 16:03:31
	 */
    ApiResponseResult updateReportingRepair(ReportingRepairUpdateVo vo) throws Exception;

	 /**
	  * ID查询对象
	  * @param reportingRepairEntity
	  * @return
	  */
     ApiResponseResult getReportingRepair(ReportingRepairEntity reportingRepairEntity);


	 /**
	  * @Desc:  APP列表查询
	  * @param vo
	  * @Author:邓磊
	  * @UpdateDate:2020/4/22 15:36
	  * @return: 返回
	  */
     ApiResponseResult queryReportingRepairList(ReportingRepairEntity vo) throws Exception;
	
	
	/**
	 * @Desc:    列表
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/29 14:46
	 * @return: 返回
	 */
    List<ReportingRepairEntity> queryReporList(ReportingRepairEntity vo) throws Exception;
}
