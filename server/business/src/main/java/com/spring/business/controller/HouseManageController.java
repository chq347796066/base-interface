package com.spring.business.controller;

import com.spring.base.entity.baseinfo.BuildEntity;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.vo.baseinfo.housingcertification.CertificationHouseAddVo;
import com.spring.base.vo.baseinfo.housingcertification.HouseDeleteParamVo;
import com.spring.business.service.IHousingCertificationService;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房屋管理api
 */
@Api(value = "美泰-房屋管理", tags = "美泰-房屋管理接口")
@RestController
@RequestMapping("houseManage")
public class HouseManageController {

    @Autowired
    private IHousingCertificationService certificationService;

    @ApiOperation(value = "获取用户房屋列表")
    @GetMapping("/queryList")
    public ApiResponseResult queryList(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId) {
        return certificationService.queryHousingList(userId);
    }

    @ApiOperation(value = "查看当前业主的该房屋的租客和亲属")
    @PostMapping("/getOwnerTenantsRelatives")
    public ApiResponseResult getOwnerTenantsRelatives(@ApiParam(value = "房号id", required = true) @RequestParam(value = "houseId", required = true) String houseId){
        return certificationService.getOwnerTenantsRelatives(houseId);
    }

    @ApiOperation(value = "新增住户")
    @PostMapping("/addHouseUser")
    public ApiResponseResult addHouseUser(@RequestBody CertificationHouseAddVo vo){
        return certificationService.addHouseUser(vo);
    }



    @ApiOperation(value = "业主删除亲属租客")
    @PostMapping("/deleteHouseUser")
    public ApiResponseResult deleteHouseUser(@RequestBody HouseDeleteParamVo houseDeleteParamVo){
        return certificationService.deleteHouseUser(houseDeleteParamVo);
    }

    /**
     * 添加业主租客亲属和房屋关系的数据
     * @param houseEntity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "添加业主租客亲属和房屋关系的数据")
    @PostMapping(value = "/addHouseUserInfo")
    public ApiResponseResult addHouseUserInfo(@RequestBody HouseEntity houseEntity) throws Exception{
        return certificationService.addHouseUserInfo(houseEntity);
    }

    /**
     * 批量添加业主租客亲属和房屋关系的数据
     * @param houseEntityList
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "批量添加业主租客亲属和房屋关系的数据")
    @PostMapping(value = "/batchAddHouseUserInfo")
    public ApiResponseResult batchAddHouseUserInfo(List<HouseEntity> houseEntityList) throws Exception{
        for (HouseEntity entity : houseEntityList) {
            certificationService.addHouseUserInfo(entity);
        }
        return null;
    }
}
