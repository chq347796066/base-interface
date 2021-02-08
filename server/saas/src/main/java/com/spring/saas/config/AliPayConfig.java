package com.spring.saas.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.spring.base.vo.AliPayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author 熊锋
 * @version 1.0.0
 * CreateDate 2020/7/7 10:39
 * description:支付宝支付配置
 */
@Configuration
public class AliPayConfig {

    @Autowired
    private AliPayProperties alipayProperties;

    @Bean
    public AlipayClient initAliPayClient(){
        return new DefaultAlipayClient(alipayProperties.getGatewayUrl(),
                alipayProperties.getAppId(),alipayProperties.getAppPrivateKey(),
                alipayProperties.getFormat(),alipayProperties.getCharset(),
                alipayProperties.getAliPayPublicKey(),alipayProperties.getSignType());
    }

}
