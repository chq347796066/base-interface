package com.spring.saas.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * @author 熊锋
 * @version 1.0.0
 * CreateDate 2020/7/7 14:19
 * description:微信支付配置
 */
@Data
@Configuration
public class WxPayConfig {

    /**
     * AppId
     */
    @Value("${wx.appId}")
    private String appId;

    /**
     * 商户号
     */
    @Value("${wx.mchId}")
    private String mchId;

    /**
     * 商户秘钥
     */
    @Value("${wx.mchKey}")
    private String mchKey;

    /**
     * 回调地址
     */
    @Value("${wx.notifyUrl}")
    private String notifyUrl;

    /**
     * 统一下单接口
     */
    @Value("${wx.payUrl}")
    private String payUrl;

    /**
     * 微信查询支付支付状态接口
     */
    @Value("${wx.queryUrl}")
    private String queryUrl;

}
