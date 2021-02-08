package com.spring.item.controller;


import com.spring.base.entity.pay.CommonResult;
import com.spring.item.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "订单", tags = "订单")
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    /**
     * 扣减库存
     */
    @ApiOperation(value = "扣减库存")
    @RequestMapping("/decrease")
    public CommonResult decrease(@RequestParam Long productId, @RequestParam Integer count) throws Exception{
        storageService.decrease(productId, count);
        return new CommonResult("扣减库存成功！",200);
    }
}
