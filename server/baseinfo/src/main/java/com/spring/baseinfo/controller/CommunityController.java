package com.spring.baseinfo.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.dto.CommunityDto;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.vo.baseinfo.community.CommunityAddVo;
import com.spring.base.vo.baseinfo.community.CommunityUpdateVo;
import com.spring.baseinfo.service.ICommunityService;
import com.spring.baseinfo.service.IExcelService;
import com.spring.common.annotation.CommunityDataPower;
import com.spring.common.annotation.CommunityPagePower;
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

import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 小区信息控制器
 */
@RestController
@Api(value = "小区信息", tags = "小区信息接口")
@RequestMapping("community")
public class CommunityController extends BaseController{
	@Autowired
	private ICommunityService communityService;

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
	public ApiResponseResult add(@RequestBody @Validated CommunityAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return communityService.addCommunity(vo);
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
	public ApiResponseResult update(@RequestBody @Validated CommunityUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return communityService.updateCommunity(vo);
	}

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = CommunityEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return communityService.queryObject(id);
	}

	 /**
	  * 根据主键id查询对象
	  * @param id
	  * @return
	  */
	 @ApiOperation(value = "根据主键id查询对象", response = CommunityEntity.class)
	 @GetMapping(value = "/queryCommunityEntity")
	 public ApiResponseResult queryCommunityEntity(
			 @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			 throws Exception {
		 return communityService.queryCommunityEntity(id);
	 }

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@CommunityDataPower
	@ApiOperation(value = "根据条件查询列表", response = CommunityEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody CommunityEntity vo) throws Exception {
		return communityService.queryCommunityList(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@CommunityPagePower
	@ApiOperation(value = "根据条件分页查询", response = CommunityEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody  @Validated RequestPageVO<CommunityEntity> requestPageVO) throws Exception {
		return communityService.queryCommunityPage(requestPageVO);
	}

	 /**
	  * @description:异步导出Excel
	  * @return:
	  * @author: 吕文祥
	  * @time: 2020/3/28 22:00
	  */
	 @ApiOperation(value = "异步导出小区Excel", httpMethod = "POST", response = Object.class)
	 @RequestMapping(value = "/exportCommunityExcelAsync", method = RequestMethod.POST)
	 @ResponseBody
	 public ApiResponseResult exportCommunityExcelAsync(@RequestBody CommunityEntity vo)
			 throws Exception {
		 return excelService.exportCommunityExcelAsync(vo);
	 }

	 /**
	  * @description:查询excel文件导出列表
	  * @return:
	  * @author: 赵进华
	  * @time: 2020/3/29 10:11
	  */
	 @ApiOperation(value = "查询excel文件导出列表(Excel类型:小区-Community)")
	 @GetMapping(value = "/queryExcelFile")
	 public ApiResponseResult queryExcelFile(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId", required = true) String userId,@ApiParam(value = "excel类型", required = true) @RequestParam(value = "excelType", required = true) String excelType) throws Exception {
		 return excelService.queryExcelFile(userId,excelType);
	 }

	 /**
	  * @Desc:小区导出数据信息
	  * @param requestPageVO
	  * @Author:邓磊
	  * @UpdateDate:2020/5/14 16:55
	  * @return: 返回
	  */
	 @ApiOperation(value = "小区导出数据信息", response = CommunityEntity.class)
	 @GetMapping(value = "/exportCommunityEntityInfo")
	 public void exportCommunityEntityInfo(CommunityEntity requestPageVO) throws Exception {
		 communityService.exportCommunityEntityInfo(requestPageVO);
	 }

	 /**
	  * @Desc:根据公司id查询项目信息
	  * @param companyId
	  * @Author:邓磊
	  * @UpdateDate:2020/5/14 16:55
	  * @return: 返回
	  */
	 @ApiOperation(value = "根据公司id查询项目信息")
	 @GetMapping(value = "/getCommunityInfo")
	 public List<CommunityDto> getCommunityInfo(String companyId) throws Exception {

		 return communityService.getCommunityInfo(companyId);
	 }

	/**
	 * @Desc:根据公司id查询项目信息
	 * @Author:邓磊
	 * @UpdateDate:2020/5/14 16:55
	 * @return: 返回
	 */
	@ApiOperation(value = "根据公司id查询项目信息下拉框")
	@GetMapping(value = "/getCommunityInfoByCompanyId")
	public ApiResponseResult getCommunityInfoByCompanyId() throws Exception {

		return communityService.getCommunityInfoByCompanyId();
	}

	/**
	 * @Desc:根据公司id查询项目信息
	 * @Author:邓磊
	 * @UpdateDate:2020/5/14 16:55
	 * @return: 返回
	 */
	@ApiOperation(value = "根据公司id查询项目信息下拉框")
	@GetMapping(value = "inner/getCommunityInfoByCompanyId")
	public List<CommunityDto> queryCommunityInfo() throws Exception {

		return communityService.queryCommunityInfo();
	}
 }
