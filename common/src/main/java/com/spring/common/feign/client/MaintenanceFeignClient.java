package com.spring.common.feign.client;


import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalEntityVo;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalUpdateVo;
import com.spring.base.vo.buiness.myhouse.MyHouseUpdateVo;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairUpdateVo;
import com.spring.common.constants.ServiceNameConstants;
import com.spring.common.feign.client.fallback.MaintenanceFeignClientFallbackFactory;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dell
 * @Model: bao'shi
 * @Description: MaintenanceFeignClient
 * @Auter: Mollen
 * @Date: 2020/5/23  19:19
 *      
 **/
@FeignClient(name = ServiceNameConstants.MAINTENANCE, fallbackFactory = MaintenanceFeignClientFallbackFactory.class, decode404 = true)
public interface MaintenanceFeignClient {


    /**
     * 投诉分页列表
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "complaintproposal/queryPage")
    ApiResponseResult queryPageComplaintproposal(RequestPageVO<ComplaintProposalEntityVo> requestPageVO) throws Exception;

    /**
     * 查看投诉详情
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "complaintproposal/queryComplaintProposal")
    ApiResponseResult queryComplaintProposal(ComplaintProposalEntityVo vo) throws Exception;


    /**
     * 更新
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "complaintproposal/update")
    ApiResponseResult update(ComplaintProposalUpdateVo vo) throws Exception;


    /**
     * @Desc:物业管理系统-物业服务-投诉建议导出调用列表
     * @param requestPageVO
     * @Author:邓磊
     * @UpdateDate:2020/5/15 17:11
     */
    @PostMapping(value = "complaintproposal/queryProposalList")
    List<ComplaintProposalEntity> queryProposalList(ComplaintProposalEntity requestPageVO) throws Exception;


    /**
     * 查询我的房产信息列表
     *
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "myhouse/queryList")
    ApiResponseResult queryList(MyHouseEntity vo) throws Exception;


    /**
     * 查询我的房产信息列表
     *
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "myhouse/queryPage")
    ApiResponseResult queryPage(RequestPageVO<MyHouseEntity> requestPageVO) throws Exception;

    /**
     * 分页查询APP用户绑定房产审核
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "myhouse/queryPage")
    ApiResponseResult queryPage( MyHouseEntity vo) throws Exception;


    /**
     * 查看APP用户绑定房产审核
     * @param id
     * @return
     */
    @GetMapping(value = "myhouse/queryObject")
    ApiResponseResult queryObject(@RequestParam("id") String id);


    /**
     * 同意或审批房屋认证
     *
     * @param vo
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "myhouse/update")
    ApiResponseResult update( MyHouseUpdateVo vo) throws Exception;


    /**
     * @Desc:    根据条件系统管理用户管理业主APP用户查询列表
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/5/7 16:32
     * @return: 返回
     */
    @PostMapping(value = "myhouse/queryUserAppHouseList")
    List<MyHouseEntity> queryUserAppHouseList(MyHouseEntity vo) throws Exception;


    /**
     * 更新
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "reportingrepair/update")
    ApiResponseResult update(ReportingRepairUpdateVo vo) throws Exception;


    /**
     * 根据主键id查询对象
     * @param vo
     * @return
     */
    @PostMapping(value = "reportingrepair/getReportingRepair")
    ApiResponseResult getReportingRepair(ReportingRepairEntity vo)throws Exception;


    /**
     * 根据条件分页查询
     * @param requestPageVO
     * @return
     */
    @PostMapping(value = "reportingrepair/queryPage")
    ApiResponseResult queryPageReportingrepair(RequestPageVO<ReportingRepairEntity> requestPageVO) throws Exception;


    /**
     * @Desc: 报事报修列表
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/28 19:47
     * @return: 返回
     */
    @PostMapping(value = "reportingrepair/queryList")
    ApiResponseResult queryReportingRepairList(ReportingRepairEntity vo) throws Exception;


    /**
     * 报事报修导出
     * @param vo
     * @return
     */
    @RequestMapping(value = "reportingrepair/exportExcelAsync", method = RequestMethod.POST)
    @ResponseBody
    ApiResponseResult exportExcelAsync(ReportingRepairEntity vo) throws Exception;


    /**
     * 根据条件查询列表
     * @param vo
     * @return
     */
    @PostMapping(value = "reportingrepair/queryReportingRepairList")
    List<ReportingRepairEntity> queryReporList(ReportingRepairEntity vo) throws Exception;


    /**
     * 查看房屋信息
     * @param userId
     * @return
     * @throws Exception
     */
    @PostMapping(value = "myHouse/queryHouseInfo")
    ApiResponseResult queryHouseInfo(String userId) throws Exception;

}
