package com.spring.item.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.spring.base.entity.item.BusDict;
import com.spring.base.entity.item.ItemCharge;
import com.spring.common.constants.MessageCode;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ResponseData;
import com.spring.common.validate.ValidateGroups;
import com.spring.item.common.constants.BusinessCodeConstant;
import com.spring.item.common.constants.Constants;
import com.spring.item.service.BusDictService;
import com.spring.item.service.ItemChargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 费项类表
 *
 * @author zwb
 * @date 2020-04-13 16:46:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/bitemcharge" )
@Api(value = "bitemcharge", tags = "费项类表")
public class ItemChargeController {

    private final ItemChargeService bItemChargeService;

    private final BusDictService bBusDictService;

    /**
     * 查询科目
     * @param page 查询科目
     * @param bBusDict 数据字典
     * @return
     */
    @GetMapping("/getBBusDictList" )
    @ApiOperation(value = "查询科目", notes = "查询科目", httpMethod = "GET",response = BusDict.class)
    public ResponseData getBusDictList() {
        BusDict bBusDict = new BusDict();
        bBusDict.setDictName(Constants.CHAEGE_TYPE);
        return new ResponseData<>(bBusDictService.list(Wrappers.query(bBusDict)));
    }

    /**
     * 分页查询
     * @param page 分页对象
     * @param BItemCharge 收费项目表
     * @return ResponseData
     */
    @PostMapping("/page" )
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST",response = ItemCharge.class)
    public ResponseData getItemChargePage(@RequestBody RequestPageVO<ItemCharge> requestPageVO) {
        PageInfo<ItemCharge> resultData =  bItemChargeService.getPageItemCharge(requestPageVO);
        return new ResponseData<>(resultData);
    }

    /**
     *通过科目查费项
     * @param chargeType
     * @return
     */
    @GetMapping("/getItemChargeByChargeType/{chargeType}" )
    @ApiOperation(value = "通过科目查费项", notes = "通过科目查费项", httpMethod = "GET",response = ItemCharge.class)
    public ResponseData getItemChargeByChargeType(@PathVariable("chargeType" ) String chargeType) {
        LambdaQueryWrapper<ItemCharge> lambda = new LambdaQueryWrapper<>();
        lambda.eq(ItemCharge::getChargeType, chargeType).orderByDesc(ItemCharge::getModifyDate);
        return new ResponseData<>(bItemChargeService.getBaseMapper().selectList(lambda));
    }


    /**
     * 通过id查询费项类表
     * @param chargeNo id
     * @return ResponseData
     */
    @GetMapping("/{chargeNo}" )
    @ApiOperation(value = "通过chargeNo查询费项类表", notes = "通过chargeNo查询费项类表", httpMethod = "GET",response = Object.class)
    public ResponseData getById(@PathVariable("chargeNo" ) String chargeNo) {
        return new ResponseData<>(bItemChargeService.getById(chargeNo));
    }

    /**
     * 新增费项类表
     * @param bItemCharge 费项类表
     * @return ResponseData
     */
    @PostMapping
    @ApiOperation(value = "新增费项类表", notes = "新增费项类表", httpMethod = "POST",response = ItemCharge.class)
    public ResponseData save(@Valid @RequestBody ItemCharge bItemCharge, BindingResult result) {
        if(result !=null && result.hasErrors()){
            throw new ValidationException(result);
        }
        Integer num = bItemChargeService.getBaseMapper().selectCount(Wrappers.query(bItemCharge));
        if(num>0){
            return new ResponseData<>("该费项已存在.",MessageCode.FAIL);
        }
        bItemCharge.setChargeNo(bItemChargeService.getNextId(BusinessCodeConstant.SUB_CHARGE_NO.getBsCode(),bItemCharge.getChargeType()));
        return new ResponseData<>(bItemChargeService.save(bItemCharge));
    }

    /**
     * 修改费项类表
     * @param bItemCharge 费项类表
     * @return ResponseData
     */
    @PutMapping
    @ApiOperation(value = "修改费项类表", notes = "修改费项类表", httpMethod = "PUT",response = ItemCharge.class)
    public ResponseData updateById(@Validated({ValidateGroups.Update.class}) @RequestBody ItemCharge bItemCharge, BindingResult result) {
        if(result !=null && result.hasErrors()){
            throw new ValidationException(result);
        }
        return new ResponseData<>(bItemChargeService.updateById(bItemCharge));
    }

    /**
     * 通过id删除费项类表
     * @param chargeNo id
     * @return R
     */
    @DeleteMapping("/{chargeNo}" )
    @ApiOperation(value = "删除费项类表", notes = "删除费项类表", httpMethod = "DELETE",response = Object.class)
    public ResponseData removeById(@PathVariable String chargeNo) {
        return new ResponseData<>(bItemChargeService.removeById(chargeNo));
    }
    
    /**
     * @Desc:费项科目管理导出
     * @param bItemCharge
     * @Author:邓磊
     * @UpdateDate:2020/5/20 9:59
     * @return: 返回
     */
    @ApiOperation(value = "费项科目管理导出", response = ItemCharge.class)
    @GetMapping(value = "/exportBitemChargeInfo")
    public void  exportBitemChargeInfo(ItemCharge bItemCharge){
        bItemChargeService.exportBitemChargeInfo(bItemCharge);
    }

}
