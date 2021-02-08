package com.spring.item.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.base.entity.item.BusDict;
import com.spring.common.response.ResponseData;
import com.spring.item.service.BusDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 数据字典
 *
 * @author zwb
 * @date 2020-04-13 17:35:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/bbusdict" )
@Api(value = "bbusdict", tags = "数据字典")
public class BusDictController {

    private final BusDictService bBusDictService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param bBusDict 数据字典
     * @return
     */
    @GetMapping("/page" )
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET",response = BusDict.class)
    public ResponseData getBusDictPage(Page page, BusDict bBusDict) {
        return new ResponseData<>(bBusDictService.page(page, Wrappers.query(bBusDict)));
    }


    /**
     * 通过id查询数据字典
     * @param dictId id
     * @return ResponseData
     */
    @GetMapping("/{dictId}" )
    @ApiOperation(value = "通过id查询数据字典", notes = "通过id查询数据字典", httpMethod = "GET",response = Object.class)
    public ResponseData getById(@PathVariable("dictId" ) String dictId) {
        return new ResponseData<>(bBusDictService.getById(dictId));
    }

    /**
     * 新增数据字典
     * @param bBusDict 数据字典
     * @return ResponseData
     */
    @PostMapping
    @ApiOperation(value = "新增数据字典", notes = "新增数据字典", httpMethod = "POST",response = BusDict.class)
    public ResponseData save(@RequestBody BusDict bBusDict) {
        return new ResponseData<>(bBusDictService.save(bBusDict));
    }

    /**
     * 修改数据字典
     * @param bBusDict 数据字典
     * @return ResponseData
     */
    @PutMapping
    @ApiOperation(value = "修改数据字典", notes = "修改数据字典", httpMethod = "PUT",response = BusDict.class)
    public ResponseData updateById(@RequestBody BusDict bBusDict) {
        return new ResponseData<>(bBusDictService.updateById(bBusDict));
    }

    /**
     * 通过id删除数据字典
     * @param dictId id
     * @return R
     */
    @DeleteMapping("/{dictId}" )
    @ApiOperation(value = "删除数据字典", notes = "删除数据字典", httpMethod = "DELETE",response = Object.class)
    public ResponseData removeById(@PathVariable String dictId) {
        return new ResponseData<>(bBusDictService.removeById(dictId));
    }

}
