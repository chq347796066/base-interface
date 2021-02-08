package com.spring.maintenance.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairAddVo;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairUpdateVo;
import com.spring.common.annotation.CommunityDataPower;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.IAsynExcelService;
import com.spring.maintenance.service.IReportingRepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 16:03:31
 * @Desc类说明: 业主报事报修控制器
 */
@RestController
@Api(value = "业主报事报修", tags = "业主报事报修接口")
@RequestMapping("reportingrepair")
public class ReportingRepairController extends BaseController{
	@Autowired
	private IReportingRepairService reportingRepairService;
	 @Autowired
	 private IAsynExcelService excelService;
	
	/**
	 * 新增
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated ReportingRepairAddVo vo) throws Exception {
		return reportingRepairService.addReportingRepair(vo);
	}

	/**
	 * 更新
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "更新")
	@PostMapping(value = "/update")
	public ApiResponseResult update(@RequestBody @Validated ReportingRepairUpdateVo vo) throws Exception {
		return reportingRepairService.updateReportingRepair(vo);
	}


	/**
	 * 根据主键id查询对象
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = ReportingRepairEntity.class)
	@PostMapping(value = "/getReportingRepair")
	public ApiResponseResult getReportingRepair(@RequestBody ReportingRepairEntity vo)
			throws Exception {
		return reportingRepairService.getReportingRepair(vo);
	}

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@CommunityDataPower
	@ApiOperation(value = "App端不分页根据条件查询列表", response = ReportingRepairEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryReportingRepairList(@RequestBody ReportingRepairEntity vo) throws Exception {
		return reportingRepairService.queryReportingRepairList(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = ReportingRepairEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody @Validated RequestPageVO<ReportingRepairEntity> requestPageVO) throws Exception {
		return reportingRepairService.queryPage(requestPageVO);
	}



	 /**
	  * 根据条件查询列表
	  * @param vo
	  * @return
	  */
	 @ApiOperation(value = "根据条件查询列表", response = ReportingRepairEntity.class)
	 @PostMapping(value = "/queryReportingRepairList")
	 public List<ReportingRepairEntity> queryReporList(@RequestBody ReportingRepairEntity vo) throws Exception {
		 return reportingRepairService.queryReporList(vo);
	 }



	 /**
	  * @Desc: 投诉建议导出
	  * @param
	  * @Author:邓磊
	  * @UpdateDate:2020/4/20 10:12
	  * @return: ApiResponseResult
	  */
	 @ApiOperation(value = "异步导出Excel", httpMethod = "POST", response = Object.class)
	 @RequestMapping(value = "/exportExcelAsync", method = RequestMethod.POST)
	 @ResponseBody
	 public ApiResponseResult exportExcelAsync(@RequestBody ReportingRepairEntity vo)
			 throws Exception {
		 return excelService.asynExportReportingRepair(vo);
	 }
	
}
