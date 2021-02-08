package com.spring.saas.controller.operation;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.saas.TenantEntity;
import com.spring.base.vo.baseinfo.user.LoginReturnVo;
import com.spring.base.vo.baseinfo.user.LoginVo;
import com.spring.base.vo.saas.*;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.ITenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 17:10:11
 * @Desc类说明: 租户信息控制器
 */
@RestController
@Api(value = "租户信息", tags = {"租户信息接口"})
@RequestMapping("tenant")
public class TenantController extends BaseController {

    @Autowired
    private ITenantService tenantService;

    /**
     * 注册租户
     *
     * @param vo
     * @param result
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "注册租户")
    @PostMapping(value = "/registerTenant")
    public ApiResponseResult registerTenant(@RequestBody @Validated TenantAddVo vo, BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return tenantService.registerTenant(vo);
    }

    /**
     * 根据主键id查询对象
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据主键id查询对象", response = TenantResponseVo.class)
    @GetMapping(value = "/queryTenantDetail")
    public ApiResponseResult queryTenantDetail(
            @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
            throws Exception {
        return tenantService.queryTenantDetail(id);
    }


    /**
     * 根据条件查询列表
     *
     * @param vo
     * @return
     */
    @ApiOperation(value = "根据条件查询列表", response = TenantEntity.class)
    @PostMapping(value = "/queryList")
    public ApiResponseResult queryTenantList(@RequestBody TenantQueryVo vo) throws Exception {
        return tenantService.queryTenantList(vo);
    }

    /**
     * 根据条件分页查询
     *
     * @param requestPageVO
     * @return
     */
    @ApiOperation(value = "根据条件分页查询", response = TenantPageVo.class)
    @PostMapping(value = "/queryPage")
    public ApiResponseResult queryPage(@RequestBody RequestPageVO<TenantQueryVo> requestPageVO) throws Exception {
        return tenantService.queryTenantList(requestPageVO);
    }

    /**
     * @description:检测手机号码是否则注册
     * @return:
     * @author: 赵进华
     * @time: 2020/7/3 11:24
     */
    @ApiOperation(value = "检测手机号码是否则注册")
    @GetMapping(value = "/checkMobileExist")
    public ApiResponseResult checkMobileExist(
            @ApiParam(value = "手机", required = true) @RequestParam(value = "mobile", required = true) String mobile)
            throws Exception {
        return tenantService.checkMobileExist(mobile);
    }

    /**
     * @description:租户登录
     * @return:
     * @author: 赵进华
     * @time: 2020/7/3 15:45
     */
    @ApiOperation(value = "租户登录", response = LoginReturnVo.class)
    @PostMapping(value = "/tenantLogin")
    public ApiResponseResult tenantLogin(@RequestBody LoginVo vo) throws Exception {
        return tenantService.tenantLogin(vo);
    }

    /**
     * @description:租户单独登录
     * @return:
     * @author: 赵进华
     * @time: 2020/7/15 14:22
     */
    @ApiOperation(value = "租户单独登录", response = LoginReturnVo.class)
    @PostMapping(value = "/tenantLoginAlone")
    public ApiResponseResult tenantLoginAlone(@RequestBody LoginVo vo) throws Exception {
        return tenantService.tenantLoginAlone(vo);
    }

    /**
     * @description:审核租户
     * @return:
     * @author: 赵进华
     * @time: 2020/7/3 18:04
     */
    @ApiOperation(value = "审核租户")
    @PostMapping(value = "/auditTenant")
    public ApiResponseResult auditTenant(
            @RequestBody @Validated TenantAuditVo vo, BindingResult result)
            throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return tenantService.auditTenant(vo);
    }

    /**
     * @description:租户启用停用
     * @return:
     * @author: 赵进华
     * @time: 2020/7/15 14:56
     */
    @ApiOperation(value = "更新租户状态")
    @PostMapping(value = "/updateTenantStatus")
    public ApiResponseResult updateTenantStatus(
            @ApiParam(value = "租户Id", required = true) @RequestParam(value = "tenantId", required = true) String tenantId,
            @ApiParam(value = "租户状态", required = true) @RequestParam(value = "tenantStatus", required = true) Integer tenantStatus)
            throws Exception {
        return tenantService.updateTenantStatus(tenantId, tenantStatus);
    }
}
