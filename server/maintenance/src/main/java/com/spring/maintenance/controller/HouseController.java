package com.spring.maintenance.controller;

import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.IHouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *房屋列表
 *@author dell
 */
@RestController
@Api(value = "查看房屋信息", tags = "查看房屋信息")
@RequestMapping("/inner/house")
public class HouseController {
   @Autowired
   private IHouseService houseService;

    /**
     * 根据条件查询列表
     * @param vo
     * @return
     */
    @ApiOperation(value = "根据条件查询列表", response = HouseEntity.class)
    @PostMapping(value = "/queryHouseList")
    public ApiResponseResult queryHouseList(@RequestBody HouseEntity vo) throws Exception {
        return houseService.queryHouseList(vo);
    }


    /**
     * @Desc: 查看房屋审核信息
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/5/13 10:28
     * @return: 返回
     */
    @ApiOperation(value = "房屋审核确认房屋信息业主", response = HouseEntity.class)
    @PostMapping(value = "/queryNameMobile")
    public ApiResponseResult queryNameMobile(@RequestBody HouseEntity vo) throws Exception {
        return houseService.queryNameMobile(vo);
    }

}
