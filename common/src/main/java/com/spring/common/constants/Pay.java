package com.spring.common.constants;

/**
 * @author 熊锋
 * @version 1.0.0
 * CreateDate 2020/7/7 20:05
 * description:支付信息
 */
public class Pay {

    /**
     * 微信支付appId
     */
    public static final String APP_ID= "appid";

    /**
     * 商户号
     */
    public static final String MCH_ID = "mch_id";

    /**
     * nonce_str
     */
    public static final String NONCE_STR = "nonce_str";

    /**
     * 商品内容
     */
    public static final String BODY = "body";

    /**
     * 微信支付订单号
     */
    public static final String OUT_TRADE_NO = "out_trade_no";

    /**
     * 微信支付金额
     */
    public static final String TOTAL_FEE = "total_fee";

    /**
     * 创建ip
     */
    public static final String SPBILL_CREATE_IP = "spbill_create_ip";

    /**
     * 微信回调地址
     */
    public static final String NOTIFY_URL = "notify_url";

    /**
     * 支付类型
     */
    public static final String TRADE_TYPE = "trade_type";

    /**
     * 商品id
     */
    public static final String PRODUCT_ID = "product_id";

    /**
     * 内容信息
     */
    public static final String BODY_VALUE = "物业管理系统";

    /**
     * 本机地址
     */
    public static final String SPBILL_CREATE_IP_VALUE = "127.0.0.1";

    /**
     * 调用类型(pc端 扫码支付)
     */
    public static final String TRADE_TYPE_VALUE = "NATIVE";

    /**
     * 调用类型(app 小程序)
     */
    public static final String TRADE_TYPE_APP_VALUE = "APP";

    /**
     * 二维码过期时间
     */
    public static final String TIME_EXPIRE= "time_expire";
}
