package com.spring.maintenance.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalAddVo;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalUpdateVo;
import com.spring.common.annotation.CommunityDataPower;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.IAsynExcelService;
import com.spring.maintenance.service.IComplaintProposalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 15:49:31
 * @Desc类说明: 业主投诉建议控制器
 */
@RestController
@Api(value = "业主投诉建议", tags = "业主投诉建议接口")
@RequestMapping("complaintproposal")
public class ComplaintProposalController extends BaseController{
	@Autowired
	private IComplaintProposalService complaintProposalService;

	 @Autowired
	 private IAsynExcelService excelService;
	
	/**
	 * 新增
	 * @param vo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增")
	@PostMapping(value = "/add")
	public ApiResponseResult add(@RequestBody @Validated ComplaintProposalAddVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return complaintProposalService.addComplaintProposal(vo);
	}

	/**
	 * 更新
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "更新")
	@PostMapping(value = "/update")
	public ApiResponseResult update(@RequestBody @Validated ComplaintProposalUpdateVo vo) throws Exception {
		return complaintProposalService.updateComplaintProposal(vo);
	}

	/**
	 * 根据主键id查询对象
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "根据主键id查询对象", response = ComplaintProposalEntity.class)
	@PostMapping(value = "/queryComplaintProposal")
	public ApiResponseResult queryComplaintProposal(@RequestBody @Validated  ComplaintProposalEntity vo) throws Exception {
		return complaintProposalService.queryComplaintProposal(vo);
	}

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 */
	@CommunityDataPower
	@ApiOperation(value = "APP端根据条件查询列表", response = ComplaintProposalEntity.class)
	@PostMapping(value = "/queryListComplaintProposal")
	public ApiResponseResult queryListComplaintProposal(@RequestBody ComplaintProposalEntity vo) throws Exception {
		return complaintProposalService.queryListComplaintProposal(vo);
	}

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 */
	@ApiOperation(value = "物业端根据条件分页查询", response = ComplaintProposalEntity.class)
	@PostMapping(value = "/queryPage")
	public ApiResponseResult queryPage(@RequestBody @Validated RequestPageVO<ComplaintProposalEntity> requestPageVO) throws Exception {
		return complaintProposalService.queryPage(requestPageVO);
	}



	/**
	 * @Desc:物业管理系统-物业服务-投诉建议导出调用列表
	 * @param requestPageVO
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 17:04
	 * @return: 返回
	 */
	@ApiOperation(value = "物业管理系统-物业服务-投诉建议导出调用列表", response = ComplaintProposalEntity.class)
	@PostMapping(value = "/queryProposalList")
	public List<ComplaintProposalEntity> queryProposalList(@RequestBody ComplaintProposalEntity requestPageVO) throws Exception {
		return complaintProposalService.queryProposalList(requestPageVO);
	}

 }
