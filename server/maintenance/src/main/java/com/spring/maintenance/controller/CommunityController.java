package com.spring.maintenance.controller;

import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.ICommunityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *小区列表
 * @author dell
 */
@RestController
@Api(value = "查看小区信息", tags = "查看小区信息")
@RequestMapping("/inner/community")
public class CommunityController {

    @Autowired
    private ICommunityService communityService;

    @ApiOperation(value = "根据条件查询小区列表", response = CommunityEntity.class)
    @PostMapping(value = "/queryCommunityList")
    public ApiResponseResult queryCommunityList(@RequestBody CommunityEntity vo) throws Exception {
        return communityService.queryCommunityList(vo);
    }

}
