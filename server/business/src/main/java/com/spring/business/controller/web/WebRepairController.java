package com.spring.business.controller.web;

import com.spring.business.dto.WebRepairDto;
import com.spring.business.service.IRepairService;
import com.spring.business.vo.WebRepairQueryVo;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 熊锋
 * @date 2021-01-07 16:01
 * @Describe:
 */
@RestController
@Api(value = "PC端报事报修", tags = {"PC端报事报修"})
@RequestMapping("webRepair")
public class WebRepairController {

    @Autowired
    private IRepairService repairService;

    /**
     * web端查询工单列表
     * @param webRepairQueryVo
     * @return
     */
    @ApiOperation(value = "查询报修单列表", response = WebRepairDto.class)
    @PostMapping(value = "/queryRepairList")
    public ApiResponseResult queryRepairList(@RequestBody @Validated WebRepairQueryVo webRepairQueryVo) throws Exception {
        return repairService.queryWebRepairList(webRepairQueryVo);
    }
}
