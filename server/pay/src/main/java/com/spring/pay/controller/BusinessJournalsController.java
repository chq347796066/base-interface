package com.spring.pay.controller;


import com.spring.base.entity.pay.BusinessJournalsEntity;
import com.spring.base.entity.pay.TransJournalsEntity;
import com.spring.base.vo.ReturnInfo;
import com.spring.base.vo.pay.businessjournals.BusinessJournalsAddVo;
import com.spring.base.vo.pay.businessjournals.BusinessJournalsUpdateVo;
import com.spring.base.vo.pay.payonekeypay.BillOffsetVo;
import com.spring.base.vo.pay.payonekeypay.PayOneVo;
import com.spring.base.vo.pay.queryrecord.QueryRecordVo;
import com.spring.common.importExcel.dto.RowValidateResultDto;
import com.spring.pay.service.ImportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
import com.spring.pay.service.IBusinessJournalsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 交流流水记录控制器
 */
@RestController
@Api(value = "交易流水记录", tags = "交易流水记录接口")
@RequestMapping("businessjournals")
@Slf4j
public class BusinessJournalsController extends BaseController{
	@Autowired
	private IBusinessJournalsService businessJournalsService;
	@Autowired
	private ImportService importService;
	
	/**
	 * 新增
	 * 
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated BusinessJournalsAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return businessJournalsService.addBusinessJournals(vo);
	}

	/**
	 * 更新
	 * 
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "更新")
	@PostMapping(value = "/update")
	public ApiResponseResult update(@RequestBody @Validated BusinessJournalsUpdateVo vo,
			BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return businessJournalsService.updateBusinessJournals(vo);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@GetMapping(value = "/delete")
	public ApiResponseResult delete(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return businessJournalsService.delete(id);
	}

	/**
	 * 根据主键id查询对象
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = BusinessJournalsEntity.class)
	@GetMapping(value = "/queryObject")
	public ApiResponseResult queryObject(
			@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
			throws Exception {
		return businessJournalsService.queryObject(id);
	}


	/**
	 * 根据条件查询列表
	 * 
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据条件查询列表", response = BusinessJournalsEntity.class)
	@PostMapping(value = "/queryList")
	public ApiResponseResult queryList(@RequestBody BusinessJournalsEntity vo) throws Exception {
		return businessJournalsService.queryList(vo);
	}

	/**
	 * 根据条件分页查询
	 * 
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", response = BusinessJournalsEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody RequestPageVO<BusinessJournalsEntity> requestPageVO) throws Exception {
		return businessJournalsService.queryPage(requestPageVO);
	}

	 /**
	  * 一键缴费
	  *
	  * @param vo
	  * @param result
	  * @return
	  * @throws Exception
	  */
	 @ApiOperation(value = "一键缴费")
	 @PostMapping(value = "/payOneKeyPay")
	 public ApiResponseResult payOneKeyPay(@RequestBody @Validated PayOneVo vo, BindingResult result) throws Exception {
		 if (result != null && result.hasErrors()) {
			 throw new ValidationException(result);
		 }

		 return businessJournalsService.payOneKeyPay(vo);
	 }

	 /**
	  * 查询缴费记录
	  *
	  * @return
	  * @throws Exception
	  */
	 @ApiOperation(value = "查询缴费记录",response = TransJournalsEntity.class)
	 @PostMapping(value = "/payQueryRecordList")
	 public ApiResponseResult payQueryRecordList(@RequestBody RequestPageVO<QueryRecordVo> requestPageVO) throws Exception {
		 return businessJournalsService.payQueryRecordList(requestPageVO);
	 }

	 
	 /**
	  * @Desc:收费历史记录导出
	  * @param recordVo
	  * @Author:邓磊
	  * @UpdateDate:2020/5/20 13:45
	  * @return: 返回
	  */
	 @ApiOperation(value = "收费历史记录导出", response = QueryRecordVo.class)
	 @GetMapping(value = "/exportRecordInfo")
	 public void  exportRecordInfo(QueryRecordVo recordVo){
		 businessJournalsService.exportRecordInfo(recordVo);
	 }


	 /**
	  * 预收批量导入
	  */
	 @ApiOperation(value = "预收批量导入", response = QueryRecordVo.class)
	 @PostMapping(value = "/importPrePay")
	 public Object doImport(@RequestParam("excelfile") MultipartFile file) throws Exception{

		 ReturnInfo rInfo = new ReturnInfo();
		 Map<String, Object> map = importService.readToBeanList(file.getInputStream());
		 if(MapUtils.isEmpty(map)){
			 rInfo.setCode(0);
			 return rInfo;
		 }
		 List<RowValidateResultDto> list = (List<RowValidateResultDto>)map.get("rvrDtoList");
		 if (CollectionUtils.isEmpty(list)){
			 rInfo.setCode(1);
		 }else {
			 rInfo.setCode(2);
		 }
		 rInfo.setResult(map);
		 return rInfo;

	 }

	@ApiOperation(value = "预收余额批量抵扣")
	@PostMapping(value = "/payBillOffset")
	public ApiResponseResult payBillOffset(@RequestBody BillOffsetVo vo) throws Exception {

		return businessJournalsService.payBillOffset(vo);
	}

 }
