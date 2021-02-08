package com.spring.business.controller;

import com.spring.base.vo.baseinfo.housingcertification.AuditHouseVo;
import com.spring.base.vo.baseinfo.housingcertification.CertificationHouseAddVo;
import com.spring.base.vo.baseinfo.housingcertification.CertificationVo;
import com.spring.business.service.IHousingCertificationService;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 房屋审核Api
 */
@Api(value = "美泰-房屋审核", tags = "美泰-房屋审核接口")
@RestController
@RequestMapping("houseAudit")
public class HouseAuditController {

    @Autowired
    private IHousingCertificationService certificationService;


    @ApiOperation(value = "我的审批列表")
    @PostMapping("getCertifications")
    public ApiResponseResult getCertifications(@RequestBody AuditHouseVo auditHouseVo){
        return certificationService.getCertifications(auditHouseVo);
    }

    @ApiOperation(value = "审批详情")
    @GetMapping("certificationDetail")
    public ApiResponseResult certificationDetail(@ApiParam(value = "数据id", required = true) @RequestParam(value = "id", required = true) String id){
        return certificationService.certificationDetail(id);
    }



    @ApiOperation(value = "房屋审核")
    @PostMapping("housingAudit")
    public ApiResponseResult housingAudit(@RequestBody CertificationVo vo){
        return certificationService.housingAudit(vo);
    }





}
