package com.spring.meter.controller;

import com.spring.base.entity.meter.MMeterRecordEntity;
import com.spring.base.entity.meter.MMeterTypeEntity;
import com.spring.base.vo.meter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.base.controller.BaseController;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.meter.service.IMMeterRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 房屋抄记录控制器
 */
@RestController
@Api(value = "房屋抄表记录", tags = {"房屋抄表记录接口"})
@RequestMapping("mmeterrecord")
public class MMeterRecordController extends BaseController{

	@Autowired
	private IMMeterRecordService mMeterRecordService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated MMeterRecordAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return mMeterRecordService.addMMeterRecord(vo);
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
	public ApiResponseResult update(@RequestBody @Validated MMeterRecordUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return mMeterRecordService.updateMMeterRecord(vo);
	}

	/**
	 * 自定义删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@GetMapping(value = "/deleteMeterRecord")
	public ApiResponseResult deleteMeterRecord(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) Long id)
			throws Exception {
		return mMeterRecordService.deleteMeterRecord(id);
	}

	 /**
	  * 自定义删除
	  * @param id
	  * @return
	  */
	 @ApiOperation(value = "批量删除", notes = "批量删除")
	 @PostMapping(value = "/batchDeleteMeterRecord")
	 public ApiResponseResult batchDeleteMeterRecord(@RequestBody MeterIds ids)
			 throws Exception {
		 return mMeterRecordService.batchDeleteMeterRecord(ids);
	 }

	/**
	 * 根据主键id查询对象
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = MMeterRecordEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return mMeterRecordService.queryObject(id);
	}


	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询仪表列表", response = MMeterRecordEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<MMeterRecordEntity> requestPageVO) throws Exception {
		return mMeterRecordService.queryPage(requestPageVO);
	}

	/**
	 * @Desc:抄表记录列表导出
	 * @param vo
	 * @Author:宋雄
	 * @UpdateDate:2020/5/20 13:45
	 * @return: 返回
	 */
	@ApiOperation(value = "抄表记录列表导出", response = MMeterRecordEntity.class)
	@PostMapping(value = "/exportMeterRecord")
	public void  exportMeterRecord(@RequestBody MMeterRecordEntity vo) throws Exception {
		mMeterRecordService.exportMeterRecord(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询抄表统计列表", response = MMeterRecordStatisVo.class)
	@PostMapping(value = "/queryStatisPage")
	public ApiResponseResult queryStatisPage(@RequestBody RequestPageVO<MMeterRecordStatisParam> requestPageVO) throws Exception {
		return mMeterRecordService.queryStatisPage(requestPageVO);
	}

	/**
	 * @Desc:抄表统计列表导出
	 * @param vo
	 * @Author:宋雄
	 * @UpdateDate:2020/5/20 13:45
	 * @return: 返回
	 */
	@ApiOperation(value = "抄表统计列表导出", response = MMeterRecordStatisVo.class)
	@PostMapping(value = "/exportMeterStatis")
	public void  exportMeterStatis(@RequestBody MMeterRecordStatisParam vo) throws Exception {
		mMeterRecordService.exportMeterStatis(vo);
	}


	/**
	 * 根据条件分页查询
	 * @param paramVo
	 *
	 *
	 * @return
	 */
	@ApiOperation(value = "根据小区Id查询抄表统计数据", response = MMeterRecordStatisVo.class)
	@PostMapping(value = "/queryStatisByCommunity")
	public ApiResponseResult queryStatisByCommunity(@RequestBody MMeterRecordStatisParam paramVo) throws Exception {
		return mMeterRecordService.queryStatisByCommunity(paramVo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询抄表明细列表", response = MMeterRecordStatisVo.class)
	@PostMapping(value = "/queryStatisDetailPage")
	public ApiResponseResult queryStatisDetailPage(@RequestBody RequestPageVO<MMeterRecordStatisParam> requestPageVO) throws Exception {
		return mMeterRecordService.queryStatisDetailPage(requestPageVO);
	}


	/**
	 * @Desc:抄表统计明细列表导出
	 * @param vo
	 * @Author:宋雄
	 * @UpdateDate:2020/5/20 13:45
	 * @return: 返回
	 */
	@ApiOperation(value = "抄表统计明细列表导出", response = MMeterRecordStatisVo.class)
	@PostMapping(value = "/exportMeterStatisDetail")
	public void  exportMeterStatisDetail(@RequestBody MMeterRecordStatisParam vo) throws Exception {
		mMeterRecordService.exportMeterStatisDetail(vo);
	}
	
}
