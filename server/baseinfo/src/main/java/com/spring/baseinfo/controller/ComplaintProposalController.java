package com.spring.baseinfo.controller;
import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalEntityVo;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalUpdateVo;
import com.spring.baseinfo.service.IComplaintProposaService;
import com.spring.baseinfo.service.IExcelService;
import com.spring.common.annotation.CommunityPagePower;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 作者：denglei
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  投诉建议处理
 */
@RestController
@Api(value = " 投诉建议处理", tags = " 投诉建议处理接口")
@RequestMapping("complaint")
public class ComplaintProposalController {
    @Autowired
    private IComplaintProposaService complaintProposaService;
    @Autowired
    private IExcelService excelService;

    /**
     * 更新
     * @param vo
     * @param result
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public ApiResponseResult update(@RequestBody @Validated ComplaintProposalUpdateVo vo, BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return complaintProposaService.update(vo);
    }

    /**
     * 根据条件分页查询
     * @param requestPageVO
     * @return
     */
    @CommunityPagePower
    @ApiOperation(value = "物业端根据条件分页查询", response = ComplaintProposalEntityVo.class)
    @PostMapping(value = "/queryPage")
    public ApiResponseResult queryPage(@RequestBody @Validated RequestPageVO<ComplaintProposalEntityVo> requestPageVO) throws Exception {
        return complaintProposaService.queryComplaintProposalPage(requestPageVO);
    }

    /**
     * 根据主键id查询对象
     * @param vo
     * @return
     */
    @ApiOperation(value = "根据主键id查询对象", response = ComplaintProposalEntityVo.class)
    @PostMapping(value = "/queryComplaintProposal")
    public ApiResponseResult queryComplaintProposal(@RequestBody ComplaintProposalEntityVo vo) throws Exception {
        return complaintProposaService.queryComplaintProposal(vo);
    }



    /**
     * @Desc:投诉建议导出数据信息
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/5/15 16:57
     * @return: 返回
     */
    @ApiOperation(value = "投诉建议导出数据信息", response = ComplaintProposalEntity.class)
    @GetMapping(value = "/exportComplaintProposalEntityInfo")
    public void  exportComplaintProposalEntityInfo(ComplaintProposalEntity  vo) throws Exception {
        complaintProposaService.exportComplaintProposalEntityInfo(vo);
    }

}
