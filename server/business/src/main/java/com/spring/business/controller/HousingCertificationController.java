package com.spring.business.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.vo.baseinfo.housingcertification.CertificationHouseAddVo;
import com.spring.base.vo.baseinfo.housingcertification.CertificationStatusUpdateVo;
import com.spring.business.service.IHousingCertificationService;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: HousingCertificationController
 * @Description: 美泰房屋认证Api
 * @author: shangshanshan
 * @date: 2021/1/4 15:01
 * @version: v1.0
 */

@Api(value = "美泰-房屋认证", tags = "美泰-房屋认证接口")
@RestController
@RequestMapping("housingCertification")
public class HousingCertificationController extends BaseController {

    @Autowired
    private IHousingCertificationService certificationService;

    @ApiOperation(value = "获取用户房屋列表")
    @GetMapping("/queryList")
    public ApiResponseResult queryList(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId) {
        return certificationService.queryHousingList(userId);
    }

    @ApiOperation(value = "查询用户是否绑定房屋")
    @GetMapping("/bindingStatus")
    public ApiResponseResult bindingStatus(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId,
                                           @ApiParam(value = "小区id", required = true) @RequestParam(value = "communityId", required = true) String communityId){
        return certificationService.getBindingStatus(userId,communityId);
    }

    @ApiOperation(value = "修改房屋认证状态")
    @PostMapping("/update/certificationStatus")
    public ApiResponseResult updateCertificationStatus(@RequestBody CertificationStatusUpdateVo vo){
        return certificationService.updateCertificationStatus(vo);
    }

    @ApiOperation(value = "查看房屋认证详情")
    @GetMapping("/get/certificationDetail")
    public ApiResponseResult getCertificationDetail(@ApiParam(value = "数据id", required = true) @RequestParam(value = "id", required = true) String id){
        return certificationService.getCertificationDetail(id);
    }

    @ApiOperation(value = "新增房屋认证")
    @PostMapping("/add/houseCertification")
    public ApiResponseResult addHouseCertification(@RequestBody CertificationHouseAddVo vo){
        return certificationService.addHouseCertification(vo);
    }

    @ApiOperation(value = "修改房屋认证信息")
    @PostMapping("/update/houseCertification")
    public ApiResponseResult updateHouseCertification(@RequestBody CertificationHouseAddVo vo){
        return certificationService.updateHouseCertification(vo);
    }
}
