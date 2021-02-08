package com.spring.baseinfo.service;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairUpdateVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 15:49:31
 * @Desc类说明: 报事报修业务接口类
 */
public interface IReportingRepaiService extends IBaseService<ReportingRepairEntity,String> {


	/**
	 * 更新
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult reportingRepairUpdate(ReportingRepairUpdateVo vo) throws Exception;

	/**
	 * 根据主键id查询对象
	 * @param vo
	 * @return
	 */
    ApiResponseResult getReportingRepair(ReportingRepairEntity vo) throws Exception;

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
    ApiResponseResult queryReportingRepairPage(RequestPageVO<ReportingRepairEntity> requestPageVO) throws Exception;



	/**
	 * @Desc: 报事报修列表
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/28 19:46
	 * @return: 返回
	 */
    ApiResponseResult queryReportingRepairList(ReportingRepairEntity vo) throws Exception;



	/**
	 * @Desc:  报事报修导出
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/24 20:23
	 * @return: 返回
	 */
    ApiResponseResult asynExportReportingRepair(ReportingRepairEntity vo) throws Exception;




	/**
	 * @Desc:报事报修导出数据
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 15:24
	 * @return: 返回
	 */
	 void  exportReportingRepairEntityInfo(ReportingRepairEntity  vo) throws Exception;

}
