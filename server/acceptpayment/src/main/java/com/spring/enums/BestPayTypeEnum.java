package com.spring.enums;

import com.spring.exception.BestPayException;

import static com.spring.enums.BestPayPlatformEnum.ALIPAY;
import static com.spring.enums.BestPayPlatformEnum.WX;

/**
 * 支付方式
 *
 * @author null
 * @date 2017/2/14
 */
public enum BestPayTypeEnum {

    /**
     支付宝app
     */
    ALIPAY_APP("alipay_app", ALIPAY, "支付宝app"),

    /**
     支付宝pc
     */
    ALIPAY_PC("alipay_pc", ALIPAY, "支付宝pc"),

    /**
     支付宝wap
     */
    ALIPAY_WAP("alipay_wap", ALIPAY, "支付宝wap"),

    /**
     支付宝统一下单(h5)
     */
    ALIPAY_H5("alipay_h5", ALIPAY, "支付宝统一下单(h5)"),

    /**
     微信公众账号支付
     */
    WXPAY_MP("JSAPI", WX,"微信公众账号支付"),

    /**
     微信H5支付
     */
    WXPAY_MWEB("MWEB", WX, "微信H5支付"),

    /**
     微信Native支付
     */
    WXPAY_NATIVE("NATIVE", WX, "微信Native支付"),

    /**
     微信小程序支付
     */
    WXPAY_MINI("JSAPI", WX, "微信小程序支付"),

    /**
     微信APP支付
     */
    WXPAY_APP("APP", WX, "微信APP支付"),
    ;

    private final String code;

    private final BestPayPlatformEnum platform;

    private final String desc;

    BestPayTypeEnum(String code, BestPayPlatformEnum platform, String desc) {
        this.code = code;
        this.platform = platform;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public BestPayPlatformEnum getPlatform() {
        return platform;
    }

    public String getDesc() {
        return desc;
    }

    public static BestPayTypeEnum getByName(String code) {
        for (BestPayTypeEnum bestPayTypeEnum : BestPayTypeEnum.values()) {
            if (bestPayTypeEnum.name().equalsIgnoreCase(code)) {
                return bestPayTypeEnum;
            }
        }
        throw new BestPayException(BestPayResultEnum.PAY_TYPE_ERROR);
    }
}
