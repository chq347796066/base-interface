package com.spring.business.controller;

import com.spring.base.controller.BaseController;
import com.spring.business.service.IRepairWorkService;
import com.spring.business.vo.ConfirmPayVo;
import com.spring.business.vo.RepairFinishVo;
import com.spring.business.vo.RepairTransferVo;
import com.spring.common.exception.ValidationException;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2021-01-05 14:57:47
* @Desc类说明: 维修控制器
*/
@RestController
@Api(value = "维修接口", tags = {"维修接口"})
@RequestMapping("work")
public class RepairWorkController extends BaseController{

   @Autowired
   private IRepairWorkService workService;

   /**
    * @description:维修确认接单
    * @return:
    * @author: 赵进华
    * @time: 2021/1/6 16:20
    */
    @ApiOperation(value = "维修确认接单", notes = "维修确认接单")
    @GetMapping(value = "/acceptRepair")
    public ApiResponseResult acceptRepair(
            @ApiParam(value = "维修id", required = true) @RequestParam(value = "id", required = true) Long id)
            throws Exception {
        return workService.acceptRepair(id);
    }

    /**
     * @description:转单
     * @return:
     * @author: 赵进华
     * @time: 2021/1/6 16:32
     */
    @ApiOperation(value = "转单")
    @PostMapping(value = "/repairTransfer")
    public ApiResponseResult repairTransfer(@RequestBody @Validated RepairTransferVo vo, BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return workService.repairTransfer(vo);
    }

    /**
     * @description:派单
     * @return:
     * @author: 赵进华
     * @time: 2021/1/6 16:32
     */
    @ApiOperation(value = "派单")
    @PostMapping(value = "/repairAllocation")
    public ApiResponseResult repairAllocation(@RequestBody @Validated RepairTransferVo vo, BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return workService.repairAllocation(vo);
    }

    /**
     * @description:维修完成
     * @return:
     * @author: 赵进华
     * @time: 2021/1/6 17:10
     */
    @ApiOperation(value = "维修完成")
    @PostMapping(value = "/repairFinish")
    public ApiResponseResult repairFinish(@RequestBody @Validated RepairFinishVo vo, BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return workService.repairFinish(vo);
    }

    /**
     * @description:确认支付
     * @return:
     * @author: 赵进华
     * @time: 2021/1/6 19:21
     */
    @ApiOperation(value = "确认支付")
    @PostMapping(value = "/confirmPay")
    public ApiResponseResult confirmPay(@RequestBody @Validated ConfirmPayVo vo, BindingResult result) throws Exception {
        if (result != null && result.hasErrors()) {
            throw new ValidationException(result);
        }
        return workService.confirmPay(vo);
    }
}
