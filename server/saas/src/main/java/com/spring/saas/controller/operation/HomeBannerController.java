package com.spring.saas.controller.operation;


import com.spring.base.entity.saas.HomeBannerEntity;
import com.spring.base.vo.saas.HomeBannerAddVo;
import com.spring.common.page.RequestPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.base.controller.BaseController;
import com.spring.common.exception.ValidationException;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.IHomeBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-06-30 15:55:33
 * @Desc类说明: 首页轮播信息控制器
 */
@RestController
@Api(value = "首页轮播信息", tags = "首页轮播信息接口")
@RequestMapping("homeBanner")
public class HomeBannerController extends BaseController{

	@Autowired
	private IHomeBannerService homeBannerService;

	/**
	 * 上传banner图
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "上传banner图")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated HomeBannerAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return homeBannerService.addHomeBanner(vo);
	}

	/**
	 * 删除首页Banner图
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除首页Banner图", notes = "删除首页Banner图")
	@GetMapping(value = "/delete")
	public ApiResponseResult delete(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return homeBannerService.deleteBanner(id);
	}

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表(不分页)", response = HomeBannerEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody HomeBannerEntity vo) throws Exception {
		return homeBannerService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = HomeBannerEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<HomeBannerEntity> requestPageVO) throws Exception {
		return homeBannerService.queryPage(requestPageVO);
	}

	 @ApiOperation(value = "上下移动Banner图")
	 @GetMapping(value = "/moveBanner")
	 public ApiResponseResult moveBanner(
			 @ApiParam(value = "源Id", required = true) @RequestParam(value = "sourceId") String sourceId,
			 @ApiParam(value = "目标Id", required = true) @RequestParam(value = "targetId") String targetId) throws Exception {
		 return homeBannerService.moveBanner(sourceId, targetId);
	 }
}
