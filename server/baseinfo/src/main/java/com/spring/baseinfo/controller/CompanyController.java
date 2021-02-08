package com.spring.baseinfo.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.vo.baseinfo.company.AddSaasVo;
import com.spring.base.vo.baseinfo.company.CompanyAddVo;
import com.spring.base.vo.baseinfo.company.CompanyUpdateVo;
import com.spring.baseinfo.service.ICompanyService;
import com.spring.baseinfo.service.IExcelService;
import com.spring.common.annotation.TenantPagePower;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  公司控制器
 */
@RestController
@Api(value = " 公司", tags = " 公司接口")
@RequestMapping("company")
public class CompanyController extends BaseController{

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private IExcelService excelService;

	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated CompanyAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return companyService.addCompany(vo);
	}

	/**
	 * 更新
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "更新")
	@PostMapping(value = "/update")
	public ApiResponseResult update(@RequestBody @Validated CompanyUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return companyService.updateCompany(vo);
	}


	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = CompanyEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return companyService.queryObject(id);
	}



	 @ApiOperation(value = "根据主键id查询对象", response = CompanyEntity.class)
	 @GetMapping(value = "/getCompanyInfoByID")
	 public CompanyEntity getCompanyInfo(
			 @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			 throws Exception {
		 return companyService.getCompanyInfo(id);
	 }

	 /**
	  * 查询对象数据
	  * @param vo
	  * @return
	  */
	 @ApiOperation(value = "根据对象查询", response = CompanyEntity.class)
	 @PostMapping(value = "/queryCompanyEntity")
	 public ApiResponseResult queryCompanyEntity(@RequestBody CompanyUpdateVo vo)throws Exception {
		 return companyService.queryCompanyEntity(vo);
	 }

	 /**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = CompanyEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody CompanyEntity vo) throws Exception {
		return companyService.queryList(vo);
	}

	 /**
	  * @description:异步导出Excel
	  * @return:
	  * @author: 赵进华
	  * @time: 2020/3/28 22:00
	  */
	 @ApiOperation(value = "异步导出Excel", httpMethod = "POST", response = Object.class)
	 @RequestMapping(value = "/exportExcelAsync", method = RequestMethod.POST)
	 @ResponseBody
	 public ApiResponseResult exportExcelAsync(@RequestBody CompanyEntity vo)
			 throws Exception {
		 return excelService.exportExcelAsync(vo);
	 }

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@TenantPagePower
	@ApiOperation(value = "根据条件分页查询", response = CompanyEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody @Validated  RequestPageVO<CompanyEntity> requestPageVO) throws Exception {

		return companyService.queryPageCompany(requestPageVO);
	}

	/**
	 * 查询树结构
	 * @return
	 * @throws Exception
	 */
	@TenantPagePower
	@ApiOperation(value = "查询公司树形结构", response = CompanyEntity.class)
	@GetMapping(value = "/queryCompanyTree")
	public ApiResponseResult queryCompanyTree() throws Exception {
		return companyService.queryCompanyTree();
	}

	 /**
	  * @description:查询excel文件导出列表
	  * @return:
	  * @author: 赵进华
	  * @time: 2020/3/29 10:11
	  */
	 @ApiOperation(value = "查询excel文件导出列表(Excel类型: 公司-company)")
	 @GetMapping(value = "/queryExcelFile")
	 public ApiResponseResult queryExcelFile(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId", required = true) String userId,@ApiParam(value = "excel类型", required = true) @RequestParam(value = "excelType", required = true) String excelType) throws Exception {
		 return excelService.queryExcelFile(userId,excelType);
	 }


	 /**
	  * @Desc: 公司导出数据信息
	  * @param requestPageVO
	  * @Author:邓磊
	  * @UpdateDate:2020/5/14 11:13
	  * @return: 返回
	  */
	 @ApiOperation(value = "公司导出数据信息", response = CompanyEntity.class)
	 @GetMapping(value = "/exportCompanyEntityInfo")
	 public void  exportCompanyEntityInfo(CompanyEntity requestPageVO) throws Exception {

		  companyService.exportCompanyEntityInfo(requestPageVO);
	 }

     /**
      * @description:查询公司树形结构
      * @return:
      * @author: 赵进华
      * @time: 2020/6/17 16:13
      */
/*	@ApiOperation(value = "查询公司树形结构", response = CompanyEntity.class)
	@GetMapping(value = "/queryCompanyTree")
	public ApiResponseResult queryCompanyTree()
			throws Exception {
		return companyService.queryCompanyTree();
	}*/

	/**
	 * @description:新增saas租户
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/7/3 18:30
	 */
	@ApiOperation(value = "新增saas租户")
	@PostMapping(value = "/addTenant")
	public ApiResponseResult addTenant(@RequestBody @Validated AddSaasVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return companyService.addTenant(vo);
	}

}
