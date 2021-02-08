package com.spring.maintenance.controller;

import com.spring.base.entity.baseinfo.BuildEntity;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.IBuildService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *楼栋列表
 * @author dell
 */
@RestController
@Api(value = "查看楼栋信息", tags = "查看楼栋信息")
@RequestMapping("/inner/build")
public class BuildController {
    @Autowired
    private IBuildService buildService;

    /**
     * 根据条件查询楼栋列表
     * @param vo
     * @return
     */
    @ApiOperation(value = "根据条件查询楼栋列表", response = BuildEntity.class)
    @PostMapping(value = "/queryBuildList")
    public ApiResponseResult queryBuildList(@RequestBody BuildEntity vo) throws Exception {
        return buildService.queryBuildList(vo);
    }

}
