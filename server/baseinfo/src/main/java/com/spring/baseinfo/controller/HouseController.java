package com.spring.baseinfo.controller;
import com.spring.base.controller.BaseController;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.vo.baseinfo.house.HouseAddVo;
import com.spring.base.vo.baseinfo.house.HouseTemplateVo;
import com.spring.base.vo.baseinfo.house.HouseUpdateVo;
import com.spring.base.vo.baseinfo.housingcertification.HouseDeleteParamVo;
import com.spring.baseinfo.service.IExcelService;
import com.spring.baseinfo.service.IHouseService;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 房产信息控制器
 */
@RestController
@Slf4j
@Api(value = "房产信息", tags = "房产信息接口")
@RequestMapping("house")
public class HouseController extends BaseController{
	@Autowired
	private IHouseService houseService;

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
	public ApiResponseResult add(@RequestBody @Validated HouseAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return houseService.addHouse(vo);
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
	public ApiResponseResult update(@RequestBody @Validated HouseUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return houseService.updateHouse(vo);
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
		return houseService.deleteCustomer(id);
	}

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = HouseEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return houseService.queryObject(id);
	}

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = HouseEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody HouseEntity vo) throws Exception {
		vo.setFloor(null);
		return houseService.queryList(vo);
	}

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = HouseEntity.class)
	@PostMapping(value = "/queryHouseInfo")
	public HouseEntity queryHouseInfo(@RequestBody HouseEntity vo) throws Exception {
		return houseService.queryHouseInfo(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@CommunityPagePower
	@ApiOperation(value = "根据条件分页查询", response = HouseEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody @Validated RequestPageVO<HouseEntity> requestPageVO) throws Exception {
		return houseService.queryHousePage(requestPageVO);
	}


	/**
     * @description:异步导出房产Excel
     * @return:
     * @author: 赵进华
     * @time: 2020/3/28 22:00
     */
    @ApiOperation(value = "异步导出房产Excel", httpMethod = "POST", response = Object.class)
    @RequestMapping(value = "/exportHouseExcelAsync", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseResult exportHouseExcelAsync(@RequestBody HouseEntity vo)
            throws Exception {
        return excelService.exportHouseExcelAsync(vo);
    }
	/**
	 * @description:查询excel文件导出列表
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/3/29 10:11
	 */
	@ApiOperation(value = "查询excel文件导出列表(Excel类型:房产-house)")
	@GetMapping(value = "/queryExcelFile")
	public ApiResponseResult queryExcelFile(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId", required = true) String userId,@ApiParam(value = "excel类型", required = true) @RequestParam(value = "excelType", required = true) String excelType) throws Exception {
		return excelService.queryExcelFile(userId,excelType);
	}


	/**
	 * @Title: impUser
	 * @Description: excle导入
	 * @param file
	 * @return String
	 */
	@ApiOperation(value = "房屋信息批量导入模板", httpMethod = "POST", response = Object.class)
	@PostMapping("/batchImportHouse")
	public ApiResponseResult batchImportHouse(MultipartFile file,@ApiParam(value = "公司Id", required = true) @RequestParam(value = "tenantCompanyArray", required = true) String[] tenantCompanyArray,
											  @ApiParam(value = "小区Id", required = true) @RequestParam(value = "communityId", required = true) String communityId){
		ApiResponseResult result = new ApiResponseResult();
		if (file == null || file.isEmpty()) {
			result.setCode(MessageCode.FAIL);
			result.setMsg("上传文件为空！");
			return result;
		}
		List<HouseTemplateVo>  houseVoList= EasyExcelUtils.importData(file, 1, HouseTemplateVo.class);
		if(CollectionUtils.isNotEmpty(houseVoList)){
			if(StringUtils.isEmpty(houseVoList.get(0).getCommunityName())){
				result.setCode(MessageCode.FAIL);
				result.setMsg("导入时，请删除第一行说明单元格！");
				return result;
			}
		}
		ApiResponseResult apiResponseResult = houseService.batchImportHouse(houseVoList,tenantCompanyArray,communityId);
		return apiResponseResult;
	}


	/**
	 * @Desc: 车位信息批量导入下载模板
	 * @param response
	 * @Author:邓磊
	 * @UpdateDate:2020/4/24 16:16
	 */
	@ApiOperation(value = "房屋信息批量导入下载模板", httpMethod = "GET", response = Object.class)
	@GetMapping("/expUserHouseDownload")
	public void expUserHouseDownload(HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String fileName = "房产信息.xlsx";
		response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes(StandardCharsets.UTF_8),"iso8859-1"));

		try {
			InputStream inputStream =this.getClass().getClassLoader().getResourceAsStream("/excelModel/house_template.xlsx");
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
	 * @Desc: 房屋审核确认房屋信息
	 * @Author:邓磊
	 * @UpdateDate:2020/5/13 10:11
	 * @return: 返回
	 */
	@ApiOperation(value = "房屋审核确认房屋信息", response = HouseEntity.class)
	@PostMapping(value = "/queryOwnerNameMobile")
	public ApiResponseResult queryOwnerNameMobile(@RequestBody HouseEntity vo) throws Exception {
		return houseService.queryOwnerNameMobile(vo);
	}



	/**
	 * @Desc:房产导出数据
	 * @param houseEntity
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 10:13
	 * @return: 返回
	 */
	@ApiOperation(value = "房产导出数据信息", response = HouseEntity.class)
	@GetMapping(value = "/exportHouseEntityInfo")
	public void  exportHouseEntityInfo(HouseEntity houseEntity) throws Exception {
		houseService.exportHouseEntityInfo(houseEntity);
	}

	/**
	 * 导出预缴房屋信息
	 * @param params
	 * @return
	 */
	@PostMapping(value = "/queryExportList")
	public List<LinkedHashMap<String, String>> queryExportList(@RequestBody Map<String, Object> params){
	  return houseService.queryExportList(params);
	}

	/**
	 * 新增住户信息
	 * @param myHouseEntity
	 * @return
	 */
	@PostMapping(value = "/addHouseUser")
	public ApiResponseResult addHouseUser(@RequestBody MyHouseEntity myHouseEntity){
		return houseService.addHouseUser(myHouseEntity);
	}

	/**
	 * 业主删除亲属和租客
	 * @param houseDeleteParamVo
	 * @return
	 */
	@PostMapping(value = "/house/deleteHouseUser")
	public ApiResponseResult deleteHouseUser(@RequestBody HouseDeleteParamVo houseDeleteParamVo){
		return houseService.deleteHouseUser(houseDeleteParamVo);
	}
}
