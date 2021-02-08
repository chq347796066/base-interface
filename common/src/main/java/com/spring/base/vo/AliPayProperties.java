package com.spring.base.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 熊锋
 * @version 1.0.0
 * CreateDate 2020/7/7 10:36
 * description
 */
@Data
@Component
public class AliPayProperties {

    /**
     * appid
     */
    @Value("${alipay.appid}")
    private String appId;

    /**
     * 网关地址
     */
    @Value("${alipay.gatewayUrl}")
    private String gatewayUrl;

    /**
     * 数据格式
     */
    @Value("${alipay.format}")
    private String format;

    /**
     * 编码格式
     */
    @Value("${alipay.charset}")
    private String charset;

    /**
     * 加密类型
     */
    @Value("${alipay.signType}")
    private String signType;

    /**
     * 私钥
     */
    @Value("${alipay.app-private-key}")
    private String appPrivateKey;

    /**
     * 公钥
     */
    @Value("${alipay.alipay-public-key}")
    private String aliPayPublicKey;

    /**
     * 返回路径
     */
    @Value("${alipay.returnUrl}")
    private String returnUrl;

    /**
     * 回调地址
     */
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
}
