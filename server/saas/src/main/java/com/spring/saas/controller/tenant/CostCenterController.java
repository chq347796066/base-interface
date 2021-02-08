package com.spring.saas.controller.tenant;

import com.spring.base.vo.saas.PayResult;
import com.spring.base.vo.saas.PayVo;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.saas.service.IOrderService;
import com.spring.saas.service.IPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 熊锋
 * @version 1.0.0
 * CreateDate 2020/7/7 9:59
 * description
 */
@RestController
@Api(value = "应用订购", tags = {"应用订购接口"})
@RequestMapping("cost/center")
@Slf4j
public class CostCenterController {

    @Autowired
    private IPayService payService;

    @Autowired
    private IOrderService orderService;

    /**
     * @author 熊锋
     * @param payVo
     * @param response
     * @date 2020/7/7 19:20
     * @description: 生成支付二维码
     * @return void
     * @throws Exception
     */
    @ApiOperation(value = "微信支付生成二维码")
    @RequestMapping(value = "/createQRCode",method = RequestMethod.POST)
    public ApiResponseResult createCode(@RequestBody PayVo payVo, HttpServletResponse response) throws Exception {
        ApiResponseResult result=new ApiResponseResult();
        String qrCode = payService.createCode(payVo);
//        Map<EncodeHintType, Object> hints = new HashMap<>(16);
//        //设置纠错等级
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//        //编码类型
//        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
//        //生成二维码
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCode, BarcodeFormat.QR_CODE, 200, 200);
//        OutputStream outputStream = response.getOutputStream();
//        MatrixToImageWriter.writeToStream(bitMatrix,"png",outputStream);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("成功");
        result.setData(qrCode);
        return result;
    }

    /**
     * @author 熊锋
     * @param request
     * @param response
     * @date 2020/7/7 19:20
     * @description: 支付完成微信回调
     * @return void
     * @throws Exception
     */
    @ApiOperation(value = "微信回调")
    @RequestMapping(value = "/notify",method = RequestMethod.POST)
    public void wxNotify(HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (request==null||response==null) {
            log.error("请求出错");
        }
        payService.getWxPayResult(request, response);
    }
    /**
     * @author 熊锋
     * @param orderNum
     * @date 2020/7/7 19:20
     * @description: 支付完成微信回调
     * @return void
     * @throws Exception
     */
    @ApiOperation(value = "查询微信支付订单状态", response = PayResult.class)
    @GetMapping(value = "/queryPayStatus")
    public ApiResponseResult queryPayStatus(String orderNum){

        return payService.queryPayStatus(orderNum);
    }
}
