package com.spring.baseinfo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.spring.base.entity.baseinfo.CustomerEntity;
import com.spring.base.vo.baseinfo.customer.CustomerAddVo;
import com.spring.base.vo.baseinfo.customer.CustomerUpdateVo;
import com.spring.base.vo.baseinfo.customer.CustomerVo;
import com.spring.base.vo.baseinfo.housingcertification.CertificationHouseAddVo;
import com.spring.baseinfo.service.IExcelService;
import com.spring.common.annotation.CommunityPagePower;
import com.spring.common.constants.MessageCode;
import com.spring.common.util.excel.EasyExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.base.controller.BaseController;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.baseinfo.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 17:35:27
 * @Desc类说明: 客户信息控制器
 */
@RestController
@Slf4j
@Api(value = "客户信息", tags = "客户信息接口")
@RequestMapping("customer")
public class CustomerController extends BaseController{
	@Autowired
	private ICustomerService customerService;

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
	public ApiResponseResult add(@RequestBody @Validated CustomerAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return customerService.addCustomer(vo);
	}

	/**
	 * 新增房产和客户的绑定关系
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增房产和客户的绑定关系")
	@PostMapping(value = "/addCustomerHouseInfo")
	public ApiResponseResult addCustomerHouseInfo(@RequestBody CertificationHouseAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return customerService.addCustomerHouseInfo(vo);
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
	public ApiResponseResult update(@RequestBody @Validated CustomerUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return customerService.updateCustomer(vo);
	}

	/**
	 * 关联删除车位房产
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "关联删除车位房产", notes = "关联删除车位房产")
	@GetMapping(value = "/deleteCarHouse")
	public ApiResponseResult deleteCarHouse(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return customerService.deleteCarHouse(id);
	}


	 /**
	  * 删除
	  * @param id
	  * @return
	  */
	 @ApiOperation(value = "删除", notes = "删除")
	 @GetMapping(value = "/delete")
	 public ApiResponseResult delete(
			 @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			 throws Exception {
		 return customerService.delete(id);
	 }

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = CustomerEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return customerService.viewCustomInfo(id);
	}


	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = CustomerEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody CustomerEntity vo) throws Exception {
		return customerService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@CommunityPagePower
	@ApiOperation(value = "根据条件分页查询", response = CustomerEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody @Validated RequestPageVO<CustomerEntity> requestPageVO) throws Exception {
		return customerService.queryCustomerPage(requestPageVO);
	}

     /**
      * 根据条件分页查询,业主APP用户
      * @param requestPageVO
      * @return
      */
     @ApiOperation(value = "根据条件分页查询,业主APP用户", response = CustomerEntity.class)
     @PostMapping(value = "/queryCustormAppUserPage")
     public ApiResponseResult queryCustormAppUserPage(@RequestBody @Validated RequestPageVO<CustomerEntity> requestPageVO) throws Exception {
         return customerService.queryCustormAppUserPage(requestPageVO);
     }


	 /**
	  * @description:异步导出客户Excel
	  * @return:
	  * @author: 吕文祥
	  * @time: 2020/3/28 22:00
	  */
	 @ApiOperation(value = "异步导出客户Excel", httpMethod = "POST", response = Object.class)
	 @RequestMapping(value = "/exportCustomerExcelAsync", method = RequestMethod.POST)
	 @ResponseBody
	 public ApiResponseResult exportCustomerExcelAsync(@RequestBody CustomerEntity vo)
			 throws Exception {
		 return excelService.exportCustomerExcelAsync(vo);
	 }


	/**
	 * @Desc:   导入客户信息
	 * @param file
	 * @Author:邓磊
	 * @UpdateDate:2020/4/21 17:26
	 */
	@ApiOperation(value = "impUser客户信息批量导入", httpMethod = "POST", response = Object.class)
	@PostMapping("/impUser")
	public ApiResponseResult impUser(MultipartFile file,@ApiParam(value = "公司Id", required = true) @RequestParam(value = "tenantCompanyArray", required = true) String[] tenantCompanyArray,
									 @ApiParam(value = "小区Id", required = true) @RequestParam(value = "communityId", required = true) String communityId){
		ApiResponseResult result = new ApiResponseResult();
		if (file == null || file.isEmpty()) {
			result.setCode(MessageCode.FAIL);
			result.setMsg("上传文件为空！");
			return result;
		}
		List<CustomerVo> users = EasyExcelUtils.importData(file, 1, CustomerVo.class);
		if(CollectionUtils.isNotEmpty(users)){
			if(StringUtils.isEmpty(users.get(0).getCommunityName())){
				result.setCode(MessageCode.FAIL);
				result.setMsg("导入时，请删除第一行说明单元格！");
				return result;
			}
		}
		ApiResponseResult apiResponseResult = customerService.impUser(users,tenantCompanyArray,communityId);
		return apiResponseResult;
	}


	/**
	 * @Desc: 客户信息批量导入下载模板
	 * @param response
	 * @Author:邓磊
	 * @UpdateDate:2020/4/24 16:16
	 */
	@ApiOperation(value = "客户信息批量导入下载模板", httpMethod = "GET", response = Object.class)
	@GetMapping("/expUserCustomerDemo")
	public void expUserDemo(HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String fileName = "客户信息.xlsx";
		response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes(StandardCharsets.UTF_8),"iso8859-1"));

		try {
			InputStream inputStream =this.getClass().getClassLoader().getResourceAsStream("/excelModel/customer_template.xlsx");
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while((length = inputStream.read(b))>0){
				os.write(b,0,length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}


	/**
	 * @description:查询excel文件导出列表
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/3/29 10:11
	 */
	@ApiOperation(value = "查询excel文件导出列表(Excel类型:客户-custorm)")
	@GetMapping(value = "/queryExcelFile")
	public ApiResponseResult queryExcelFile(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId", required = true) String userId,@ApiParam(value = "excel类型", required = true) @RequestParam(value = "excelType", required = true) String excelType) throws Exception {
		return excelService.queryExcelFile(userId,excelType);
	}



	/**
	 * @Desc:客户信息导出数据
	 * @param customerEntity
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 13:57
	 * @return: 返回
	 */
	@ApiOperation(value = "客户信息导出数据", response = CustomerEntity.class)
	@GetMapping(value = "/exportCustomerEntityInfo")
	public void  exportCustomerEntityInfo(CustomerEntity customerEntity) throws Exception {
		customerService.exportCustomerEntityInfo(customerEntity);
	}


}
