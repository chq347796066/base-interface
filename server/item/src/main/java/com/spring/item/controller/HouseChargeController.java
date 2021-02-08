package com.spring.item.controller;

import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.entity.item.ChargeConfig;
import com.spring.base.entity.item.HouseCharge;
import com.spring.base.vo.item.HouseChargeVO;
import com.spring.common.exception.ValidationException;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.response.ResponseData;
import com.spring.item.service.ChargeConfigService;
import com.spring.item.service.HouseChargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 房屋管理
 *
 * @author zwb
 * @date 2020-04-13 17:35:56
 */
@RestController
@AllArgsConstructor
@RequestMapping("/bhousecharge" )
@Api(value = "bhousecharge", tags = "房屋管理")
public class HouseChargeController {

    private final HouseChargeService bHouseChargeService;

    private final ChargeConfigService bChargeConfigService;

    private final BaseInfoFeignClient remotePCService;

    private final BaseInfoFeignClient remoteCIDService;

    private final BaseInfoFeignClient remoteHIDService;

    /**
     * getBChargeConfigByCid
     * @param page 分页对象
     * @param bChargeConfig 费项配置
     * @return
     */
    @PostMapping("/getBChargeConfigByCid" )
    @ApiOperation(value = "查询小区费项", notes = "查询小区费项", httpMethod = "POST",response = ChargeConfig.class)
    public ResponseData getChargeConfigByCid(@RequestBody ChargeConfig bChargeConfig) {
        List<ChargeConfig> resultData =  bChargeConfigService.getbchargeconfigbycid(bChargeConfig);
        return new ResponseData<>(resultData);
    }

    /**
     * getBChargeConfigByCid
     * @param page 分页对象
     * @param bChargeConfig 费项配置
     * @return
     */
    @GetMapping("/getChargeTypeByHid" )
    @ApiOperation(value = "查询小区费项科目", notes = "查询小区费项科目", httpMethod = "GET",response = ChargeConfig.class)
    public ResponseData getChargeTypeByHid(@RequestParam("cid" ) String cid,@RequestParam("hid" ) String hid) {
        ChargeConfig bChargeConfig = new ChargeConfig();
        bChargeConfig.setCid(cid);
        bChargeConfig.setHid(hid);
        List<ChargeConfig> resultData =  bChargeConfigService.getChargeTypeByHid(bChargeConfig);
        return new ResponseData<>(resultData);
    }

    /**
     * getBChargeConfigByCid
     * @param page 分页对象
     * @param bChargeConfig 费项配置
     * @return
     */
    @GetMapping("/getChargeNoByChargeType" )
    @ApiOperation(value = "查询小区费项科目下面的费项", notes = "查询小区费项科目下面的费项", httpMethod = "GET",response = ChargeConfig.class)
    public ResponseData getChargeNoByChargeType(@RequestParam("cid" ) String cid,@RequestParam("hid" ) String hid,@RequestParam("chargeType" ) String chargeType) {
        ChargeConfig bChargeConfig = new ChargeConfig();
        bChargeConfig.setCid(cid);
        bChargeConfig.setHid(hid);
        bChargeConfig.setChargeType(chargeType);
        List<ChargeConfig> resultData =  bChargeConfigService.getChargeNoByChargeType(bChargeConfig);
        return new ResponseData<>(resultData);
    }

    /**
     *查询公司
     * @return
     */
    @GetMapping("/getCompanyList" )
    @ApiOperation(value = "查询公司", notes = "查询公司", httpMethod = "GET",response = CompanyEntity.class)
    public ApiResponseResult getCompanyList(String pCid) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(pCid);
        return remotePCService.queryList(companyEntity);
    }


    /**
     *查询小区房间
     * @return
     */
    @PostMapping("/getHidByCidList" )
    @ApiOperation(value = "查询小区房间", notes = "查询小区房间", httpMethod = "POST",response = CompanyEntity.class)
    public ApiResponseResult getHidByCidList(@RequestBody HouseEntity houseEntity) {
        return remoteHIDService.queryList(houseEntity);
    }

    /**
     *查询公司下小区
     * @return
     */
    @GetMapping("/getCommunityList" )
    @ApiOperation(value = "查询公司下小区", notes = "查询公司下小区", httpMethod = "GET",response = CompanyEntity.class)
    public ApiResponseResult getCommunityList(String pCid) {
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setCompanyId(pCid);
        return remoteCIDService.queryList(communityEntity);
    }

    /**
     * 批量新增房屋管理
     * @param bHouseCharge 房屋管理
     * @return ResponseData
     */
    @PostMapping
    @ApiOperation(value = "批量新增房屋管理", notes = "批量新增房屋管理", httpMethod = "POST",response = HouseCharge.class)
    public ResponseData saveList(@Valid @RequestBody HouseChargeVO bHouseChargeVO, BindingResult result) {
        if(result.hasErrors()){
            throw new ValidationException(result);
        }
        return new ResponseData<>(bHouseChargeService.saveList(bHouseChargeVO));
    }


    /**
     * getBChargeConfigByCid
     * @param page 分页对象
     * @param bChargeConfig 费项配置
     * @return
     */
    @PostMapping("/getBChargeConfigByBatch" )
    @ApiOperation(value = "批量查询小区费项", notes = "查询小区费项", httpMethod = "POST",response = ChargeConfig.class)
    public ResponseData getChargeConfigByBatch(@RequestBody ChargeConfig bChargeConfig) {
        List<ChargeConfig> resultData =  bChargeConfigService.getbchargeconfigbybatch(bChargeConfig);
        return new ResponseData<>(resultData);
    }
}
