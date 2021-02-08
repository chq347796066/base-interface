package com.spring.baseinfo.controller;

import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairUpdateVo;
import com.spring.baseinfo.service.IExcelService;
import com.spring.baseinfo.service.IReportingRepaiService;
import com.spring.common.annotation.CommunityPagePower;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 作者：denglei
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  报事报修处理
 */
@RestController
@Api(value = " 报事报修处理", tags = " 报事报修处理接口")
@RequestMapping("inner/reportingrepair")
public class ReportingRepaiController {
    @Autowired
    private IReportingRepaiService iReportingRepaiService;

    @Autowired
    private IExcelService excelService;
    /**
     * 更新
     * @param vo
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "更新")
    @PostMapping(value = "/reportingRepairUpdate")
    public ApiResponseResult reportingRepairUpdate(@RequestBody @Validated ReportingRepairUpdateVo vo) throws Exception {
        return iReportingRepaiService.reportingRepairUpdate(vo);
    }

    /**
     * 根据主键id查询对象
     * @param vo
     * @return
     */
    @ApiOperation(value = "根据主键id查询对象", response = ReportingRepairEntity.class)
    @PostMapping(value = "/getReportingRepair")
    public ApiResponseResult getReportingRepair(@RequestBody ReportingRepairEntity vo)
            throws Exception {
        return iReportingRepaiService.getReportingRepair(vo);
    }

    /**
     * 根据条件分页查询
     * @param requestPageVO
     * @return
     */
    @CommunityPagePower
    @ApiOperation(value = "根据条件分页查询", response = ReportingRepairEntity.class)
    @PostMapping(value = "/queryPage")
    public ApiResponseResult queryPage(@RequestBody @Validated RequestPageVO<ReportingRepairEntity> requestPageVO) throws Exception {
        return iReportingRepaiService.queryReportingRepairPage(requestPageVO);
    }


    /**
     * 根据条件查询列表
     * @param vo
     * @return
     */
    @ApiOperation(value = "根据条件查询列表", response = ReportingRepairEntity.class)
    @PostMapping(value = "/queryList")
    public ApiResponseResult queryReportingRepairList(@RequestBody ReportingRepairEntity vo) throws Exception {
        return iReportingRepaiService.queryReportingRepairList(vo);
    }


    /**
     * @Desc:报事报修信息数据导出
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/5/15 15:17
     * @return: 返回
     */
    @ApiOperation(value = "报事报修信息数据导出", response = ReportingRepairEntity.class)
    @GetMapping(value = "/exportReportingRepairEntityInfo")
    public void  exportReportingRepairEntityInfo(ReportingRepairEntity  vo) throws Exception {
        iReportingRepaiService.exportReportingRepairEntityInfo(vo);
    }



}
