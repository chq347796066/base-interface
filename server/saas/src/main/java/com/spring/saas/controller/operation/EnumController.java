package com.spring.saas.controller.operation;

import com.spring.base.vo.saas.EnumKeyValueVo;
import com.spring.common.constants.Constants;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.IEnumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dell
 */
@RestController
@Api(value = "枚举", tags = {"枚举接口"})
@RequestMapping("enum")
public class EnumController {

    @Autowired
    private IEnumService enumService;

    @ApiOperation(value = "按枚举类型名称查询枚举列表", response = EnumKeyValueVo.class)
    @GetMapping(value = "/queryEnumKeyValue")
    public ApiResponseResult queryEnumKeyValueListByEnumType(
            @ApiParam(value = "枚举类型名称", required = true)
            @RequestParam(value = "enumType", required = true) String enumType) throws Exception {
        return enumService.queryEnumListByEnumType(enumType);
    }

    @ApiOperation(value = "查询发票状态枚举", response = EnumKeyValueVo.class)
    @GetMapping(value = "/queryInvoiceStatusEnum")
    public ApiResponseResult queryInvoiceStatusEnum() throws Exception {
        return enumService.queryEnumListByEnumType(Constants.EnumTypeConstant.INVOICE_STATUS_ENUM_TYPE);
    }

    @ApiOperation(value = "查询订单支付类型枚举", response = EnumKeyValueVo.class)
    @GetMapping(value = "/queryOrderPaymentTypeEnum")
    public ApiResponseResult queryOrderPaymentTypeEnum() throws Exception {
        return enumService.queryEnumListByEnumType(Constants.EnumTypeConstant.ORDER_PAYMENT_TYPE_ENUM_TYPE);
    }

    @ApiOperation(value = "查询订单状态枚举", response = EnumKeyValueVo.class)
    @GetMapping(value = "/queryOrderStatusEnum")
    public ApiResponseResult queryOrderStatusEnum() throws Exception {
        return enumService.queryEnumListByEnumType(Constants.EnumTypeConstant.ORDER_STATUS_ENUM_TYPE);
    }

    @ApiOperation(value = "查询订单类型枚举", response = EnumKeyValueVo.class)
    @GetMapping(value = "/queryOrderTypeEnum")
    public ApiResponseResult queryOrderTypeEnum() throws Exception {
        return enumService.queryEnumListByEnumType(Constants.EnumTypeConstant.ORDER_TYPE_ENUM_TYPE);
    }

    @ApiOperation(value = "查询租户状态枚举", response = EnumKeyValueVo.class)
    @GetMapping(value = "/queryTenantStatusEnum")
    public ApiResponseResult queryTenantStatusEnum() throws Exception {
        return enumService.queryEnumListByEnumType(Constants.EnumTypeConstant.TENANT_STATUS_ENUM_TYPE);
    }
}
