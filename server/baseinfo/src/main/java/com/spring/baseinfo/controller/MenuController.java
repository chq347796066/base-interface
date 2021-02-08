package com.spring.baseinfo.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.vo.baseinfo.menu.MenuAddVo;
import com.spring.base.vo.baseinfo.menu.MenuOneVo;
import com.spring.base.vo.baseinfo.menu.MenuUpdateVo;
import com.spring.common.exception.ValidationException;
import com.spring.common.response.ApiResponseResult;
import com.spring.baseinfo.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-13 11:18:07
* @Desc类说明: 菜单控制器
*/
@RestController
@Api(value = "菜单", tags = "菜单接口")
@RequestMapping("menu")
public class MenuController extends BaseController {
   @Autowired
   private IMenuService menuService;

   /**
    * 新增
    *
    * @param vo
    * @param result
    * @return
    * @throws Exception
    */
   @ApiOperation(value = "新增")
   @PostMapping(value = "/add")
   public ApiResponseResult add(@RequestBody @Validated MenuAddVo vo, BindingResult result) throws Exception {
       if (result != null && result.hasErrors()) {
           throw new ValidationException(result);
       }
       return menuService.addMenu(vo);
   }

   /**
    * 更新
    *
    * @param vo
    * @param result
    * @return
    * @throws Exception
    */
   @ApiOperation(value = "更新")
   @PostMapping(value = "/update")
   public ApiResponseResult update(@RequestBody @Validated MenuUpdateVo vo,
                                   BindingResult result) throws Exception {
       if (result != null && result.hasErrors()) {
           throw new ValidationException(result);
       }
       return menuService.updateMenu(vo);
   }

   /**
    * 删除
    *
    * @param id
    * @return
    */
   @ApiOperation(value = "删除", notes = "删除")
   @GetMapping(value = "/delete")
   public ApiResponseResult delete(
           @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
           throws Exception {
       return menuService.deleteMenu(id);
   }

    /**
     *  查询管理系统树形菜单
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年5月24日 下午3:21:52
     */
    @ApiOperation(value = " 查询管理系统树形菜单", response = MenuOneVo.class)
    @PostMapping(value = "/queryManageTreeMenu")
    public ApiResponseResult queryManageTreeMenu() throws Exception {
        return menuService.queryManageTreeMenu();
    }

    /**
     *  查询收费系统树形菜单
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年5月24日 下午3:21:52
     */
    @ApiOperation(value = " 查询收费系统树形菜单", response = MenuOneVo.class)
    @PostMapping(value = "/queryPaymentTreeMenu")
    public ApiResponseResult queryPaymentTreeMenu() throws Exception {
        return menuService.queryPaymentTreeMenu();
    }

    /**
     *  查询App系统树形菜单
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年5月24日 下午3:21:52
     */
    @ApiOperation(value = " 查询App系统树形菜单", response = MenuOneVo.class)
    @PostMapping(value = "/queryAppTreeMenu")
    public ApiResponseResult queryAppTreeMenu() throws Exception {
        return menuService.queryAppTreeMenu();
    }
}
