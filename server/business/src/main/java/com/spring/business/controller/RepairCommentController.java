package com.spring.business.controller;

import com.spring.base.controller.BaseController;
import com.spring.business.service.IRepairCommentService;
import com.spring.business.vo.RepairCommentAddVo;
import com.spring.common.exception.ValidationException;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报修评价论控制器
 */
@RestController
@Api(value = "报修评价论", tags = {"报修评价论接口"})
@RequestMapping("repairComment")
public class RepairCommentController extends BaseController{

	@Autowired
	private IRepairCommentService repairCommentService;
	
	/**
	 * 用户评价
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "业户评论")
	@PostMapping(value = "/ownerComments")
	public ApiResponseResult add(@RequestBody @Validated RepairCommentAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return repairCommentService.ownerComments(vo);
	}

}
