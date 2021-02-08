package com.spring.maintenance.controller;

import com.spring.base.entity.baseinfo.CellEntity;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.ICellService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *单元列表
 * @author dell
 */
@RestController
@Api(value = "查看单元信息", tags = "查看单元信息")
@RequestMapping("/inner/cell")
public class CellController {
    @Autowired
    private ICellService cellService;

    /**
     * 根据条件查询列表
     * @param vo
     * @return
     */
    @ApiOperation(value = "根据条件查询单位列表", response = CellEntity.class)
    @PostMapping(value = "/queryCellList")
    public ApiResponseResult queryBuildList(@RequestBody CellEntity vo) throws Exception {
        return cellService.queryBuildList(vo);
    }

}
