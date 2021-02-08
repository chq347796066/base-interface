package com.spring.baseinfo.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.baseinfo.CarEntity;
import com.spring.base.vo.baseinfo.car.CarAddVo;
import com.spring.base.vo.baseinfo.car.CarUpdateVo;
import com.spring.base.vo.baseinfo.car.CarVo;
import com.spring.baseinfo.service.ICarService;
import com.spring.baseinfo.service.IExcelService;
import com.spring.common.annotation.CommunityPagePower;
import com.spring.common.constants.MessageCode;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.excel.EasyExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  车位信息控制器
 */
@RestController
@Slf4j
@Api(value = " 车位信息", tags = " 车位信息接口")
@RequestMapping("car")
public class CarController extends BaseController{
	@Autowired
	private ICarService carService;

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
	public ApiResponseResult add(@RequestBody @Validated CarAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return carService.addCar(vo);
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
	public ApiResponseResult update(@RequestBody @Validated CarUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return carService.updateCar(vo);
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@GetMapping(value = "/delete")
	public ApiResponseResult deleteCra(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return carService.deleteCra(id);
	}

	 /**
	  * 关联删除
	  * @param id
	  * @return
	  */
	 @ApiOperation(value = "关联删除客户", notes = "关联删除客户")
	 @GetMapping(value = "/deleteCustomer")
	 public ApiResponseResult deleteCustomer(
			 @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			 throws Exception {
		 return carService.deleteCustomer(id);
	 }

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = CarEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return carService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = CarEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody CarEntity vo) throws Exception {
		return carService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@CommunityPagePower
	@ApiOperation(value = "根据条件分页查询", response = CarEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody @Validated RequestPageVO<CarEntity> requestPageVO) throws Exception {
		return carService.queryCarPage(requestPageVO);
	}

	 /**
	  * @description:异步导出车位Excel
	  * @return:
	  * @author: 赵进华
	  * @time: 2020/3/28 22:00
	  */
	 @ApiOperation(value = "异步导出车位Excel", httpMethod = "POST", response = Object.class)
	 @RequestMapping(value = "/exportCarExcelAsync", method = RequestMethod.POST)
	 @ResponseBody
	 public ApiResponseResult exportCarExcelAsync(@RequestBody CarEntity vo)
			 throws Exception {
		 return excelService.exportCarExcelAsync(vo);
	 }


	/**
	 *
	 * @Title: impUser
	 * @Description: excle导入
	 * @param file
	 * @return String
	 */
	@ApiOperation(value = "impUser车位信息批量导入", httpMethod = "POST", response = Object.class)
	@PostMapping("/impUser")
	public ApiResponseResult impUser(MultipartFile file,
									 @ApiParam(value = "公司Id", required = true) @RequestParam(value = "tenantCompanyArray", required = true) String[] tenantCompanyArray,
									 @ApiParam(value = "小区Id", required = true) @RequestParam(value = "communityId", required = true) String communityId){
		ApiResponseResult result = new ApiResponseResult();
		if (file == null || file.isEmpty()) {
			result.setCode(MessageCode.FAIL);
			result.setMsg("上传文件为空！");
			return result;
		}
		List<CarVo> carVoList = EasyExcelUtils.importData(file, 1, CarVo.class);
		if(CollectionUtils.isNotEmpty(carVoList)){
			if(StringUtils.isEmpty(carVoList.get(0).getCommunityName())){
				result.setCode(MessageCode.FAIL);
				result.setMsg("导入时，请删除第一行说明单元格！");
				return result;
			}
		}
        ApiResponseResult apiResponseResult = carService.impUser(carVoList,tenantCompanyArray,communityId);
        return apiResponseResult;
	}

	/**
	 * @Desc: 车位信息批量导入下载模板
	 * @param response
	 * @Author:邓磊
	 * @UpdateDate:2020/4/24 16:16
	 */
	@ApiOperation(value = "车位信息批量导入下载模板", httpMethod = "GET", response = Object.class)
	@GetMapping("/expUserCarDemo")
	public void expUserDemo(HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String fileName = "车位信息.xlsx";
		response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes(StandardCharsets.UTF_8),"iso8859-1"));

		try {
			InputStream inputStream =this.getClass().getClassLoader().getResourceAsStream("/excelModel/car_template.xlsx");
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
	@ApiOperation(value = "查询excel文件导出列表(Excel类型:车位-car)")
	@GetMapping(value = "/queryExcelFile")
	public ApiResponseResult queryExcelFile(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId", required = true) String userId,@ApiParam(value = "excel类型", required = true) @RequestParam(value = "excelType", required = true) String excelType) throws Exception {
		return excelService.queryExcelFile(userId,excelType);
	}

	/**
	 * @Desc:车位信息导出
	 * @param requestPageVO
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 11:03
	 * @return: 返回
	 */
	@ApiOperation(value = "车位数据信息", response = CarEntity.class)
	@GetMapping(value = "/exportCarEntityInfo")
	public void  exportCarEntityInfo(CarEntity requestPageVO) throws Exception {
		carService.exportCarEntityInfo(requestPageVO);
	}
}
