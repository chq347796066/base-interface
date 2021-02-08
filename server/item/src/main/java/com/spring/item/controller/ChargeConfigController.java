package com.spring.item.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.entity.item.BusDict;
import com.spring.base.entity.item.ChargeConfig;
import com.spring.common.constants.MessageCode;
import com.spring.common.exception.ValidationException;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.response.ResponseData;
import com.spring.common.sequence.SequeceName;
import com.spring.common.sequence.SequenceUtil;
import com.spring.item.common.constants.Constants;
import com.spring.item.service.BusDictService;
import com.spring.item.service.ChargeConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 费项配置
 *
 * @author zwb
 * @date 2020-04-13 17:35:46
 */
@RestController
@AllArgsConstructor
@RequestMapping("/bchargeconfig" )
@Api(value = "bchargeconfig", tags = "费项配置")
public class ChargeConfigController {

    private final ChargeConfigService bChargeConfigService;

    private final BaseInfoFeignClient remotePCService;

    private final BaseInfoFeignClient remoteCIDService;

    private final BusDictService bBusDictService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param bChargeConfig 费项配置
     * @return
     */
    @PostMapping("/page" )
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST",response = ChargeConfig.class)
    public ResponseData getChargeConfigPage(@RequestBody RequestPageVO<ChargeConfig> requestPageVO) {
        PageInfo<ChargeConfig> resultData =  bChargeConfigService.getPageChargeConfig(requestPageVO);
        return new ResponseData<>(resultData);
    }

    /**
     * 查询费项类别
     * @param bBusDict 数据字典
     * @return
     */
    @GetMapping("/getChargeModeList" )
    @ApiOperation(value = "查询费项类别", notes = "查询费项类别", httpMethod = "GET",response = BusDict.class)
    public ResponseData getChargeModeList() {
        LambdaQueryWrapper<BusDict> lambda = new LambdaQueryWrapper<>();
        lambda.eq(BusDict::getDictName, Constants.CHAEGE_MOLD);
        return new ResponseData<>(bBusDictService.list(lambda));
    }

    /**
     * 查询计算公式
     * @param bBusDict 数据字典
     * @return
     */
    @GetMapping("/getCalculationList" )
    @ApiOperation(value = "查询计算公式", notes = "查询计算公式", httpMethod = "GET",response = BusDict.class)
    public ResponseData getCalculationList() {
        LambdaQueryWrapper<BusDict> lambda = new LambdaQueryWrapper<>();
        lambda.eq(BusDict::getDictName, Constants.CHAEGE_CALCULATION);
        return new ResponseData<>(bBusDictService.list(lambda));
    }

    /**
     *查询公司
     * @return
     */
    @GetMapping("/getCompanyList" )
    @ApiOperation(value = "查询公司", notes = "查询公司", httpMethod = "GET",response = CompanyEntity.class)
    public ApiResponseResult getCompanyList(String companyName) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyName(companyName);
        return remotePCService.queryList(companyEntity);
    }

    /**
     *查询公司下小区
     * @return
     */
    @GetMapping("/getCommunityList" )
    @ApiOperation(value = "查询公司下小区", notes = "查询公司下小区", httpMethod = "GET",response = CompanyEntity.class)
    public ApiResponseResult getCommunityList(String companyName) {
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setCompanyName(companyName);
        return remoteCIDService.queryList(communityEntity);
    }

    /**
     * 查询科目
     * @param page 查询科目
     * @param bBusDict 数据字典
     * @return
     */
    @GetMapping("/getBBusDictList" )
    @ApiOperation(value = "查询科目", notes = "查询科目", httpMethod = "GET",response = BusDict.class)
    public ResponseData getBusDictList() {
        LambdaQueryWrapper<BusDict> lambda = new LambdaQueryWrapper<>();
        lambda.eq(BusDict::getDictName, Constants.CHAEGE_TYPE);
        return new ResponseData<>(bBusDictService.list(lambda));
    }


    /**
     * 通过id查询费项配置
     * @param chargeConfigId id
     * @return ResponseData
     */
    @GetMapping("/{chargeConfigId}" )
    @ApiOperation(value = "通过id查询费项配置", notes = "通过id查询费项配置", httpMethod = "GET",response = Object.class)
    public ResponseData getById(@PathVariable("chargeConfigId" ) String chargeConfigId) {
        return new ResponseData<>(bChargeConfigService.getById(chargeConfigId));
    }

    /**
     * 新增费项配置
     * @param bChargeConfig 费项配置
     * @return ResponseData
     */
    @PostMapping
    @ApiOperation(value = "新增费项配置", notes = "新增费项配置", httpMethod = "POST",response = ChargeConfig.class)
    public ResponseData save(@Valid  @RequestBody ChargeConfig bChargeConfig, BindingResult result) {
        if(result.hasErrors()){
            throw new ValidationException(result);
        }
        bChargeConfig.setChargeConfigId(SequenceUtil.getSequence(SequeceName.TablePrefix.CHARGE_CONFIG_ID));
        try {
            bChargeConfigService.saveChargeConfig(bChargeConfig);
        } catch (Exception e) {
            return new ResponseData<>("增加失败!", MessageCode.FAIL);
        }
        return new ResponseData<>();
    }

    /**
     * 修改费项配置
     * @param bChargeConfig 费项配置
     * @return ResponseData
     */
    @PutMapping
    @ApiOperation(value = "修改费项配置", notes = "修改费项配置", httpMethod = "PUT",response = ChargeConfig.class)
    public ResponseData updateById(@Valid  @RequestBody ChargeConfig bChargeConfig, BindingResult result) {
        if(result.hasErrors()){
            throw new ValidationException(result);
        }
        try {
            bChargeConfigService.updatebchargeconfig(bChargeConfig);
        } catch (Exception e) {
              return new ResponseData<>("修改数据!", MessageCode.FAIL);
        }
        return new ResponseData<>();
    }

    /**
     * 通过id删除费项配置
     * @param chargeConfigId id
     * @return R
     */
    @DeleteMapping("/{chargeConfigId}" )
    @ApiOperation(value = "删除费项配置", notes = "删除费项配置", httpMethod = "DELETE",response = Object.class)
    public ResponseData removeById(@PathVariable String chargeConfigId) {
        return new ResponseData<>(bChargeConfigService.removeById(chargeConfigId));
    }


    /**
     * @Desc:费项参数设置导出
     * @param bChargeConfig
     * @Author:邓磊
     * @UpdateDate:2020/5/20 10:41
     * @return: 返回
     */
    @ApiOperation(value = "费项参数设置导出", response = ChargeConfig.class)
    @GetMapping(value = "/exportBchargeConfigInfo")
    public void  exportBchargeConfigInfo(ChargeConfig bChargeConfig){
        bChargeConfigService.exportBchargeConfigInfo(bChargeConfig);
    }

}
