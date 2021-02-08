package com.spring.maintenance.controller;

import com.spring.base.entity.pay.ModuleEntity;
import com.spring.base.vo.pay.module.ModuleAddVo;
import com.spring.base.vo.pay.module.ModuleUpdateVo;
import com.spring.maintenance.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.base.controller.BaseController;
import com.spring.common.exception.ValidationException;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-18 17:32:18
 * @Desc类说明: 控制器
 */
@RestController
@Api(value = "", tags = "组件接口")
@RequestMapping("module")
public class ModuleController extends BaseController {
    @Autowired
    private IModuleService moduleService;

    /**
     * 新增
     * @param vo
     * @param result
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public ApiResponseResult add(@RequestBody @Validated ModuleAddVo vo, BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return moduleService.addModule(vo);
    }

    /**
     * 更新
     * @param vo
     * @param result
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public ApiResponseResult update(@RequestBody @Validated ModuleUpdateVo vo,
                                    BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return moduleService.updateModule(vo);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除", notes = "删除")
    @GetMapping(value = "/delete")
    public ApiResponseResult delete(
            @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
            throws Exception {
        return moduleService.delete(id);
    }


    /**
     * 根据主键id查询对象
     * @param id
     * @return
     */
    @ApiOperation(value = "根据主键id查询对象", response = ModuleEntity.class)
    @GetMapping(value = "/queryObject")
    public ApiResponseResult queryObject(
            @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
            throws Exception {
        return moduleService.queryObject(id);
    }


    /**
     * @description:查询组件列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/23 16:10
     */
    @ApiOperation(value = "查询组件列表", response = ModuleUpdateVo.class)
    @PostMapping(value = "/queryModuleList")
    public ApiResponseResult queryModuleList() throws Exception {
        return moduleService.queryModuleList();
    }


}
