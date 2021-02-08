package com.spring.common.util.wxpay;

/**
 * @author 熊锋
 * @version 1.0.0
 * CreateDate 2020/7/7 19:49
 * description
 */
public class WxSet {

    /**
     * @author 熊锋
     * @param amount
     * @date 2020/7/7 16:30
     * @description: 元转分
     * @return java.lang.String
     * @throws Exception
     */
    public static String getMoney(String amount) {
        if(amount==null){
            return "1";
        }
        // 金额转化为分为单位
        // 处理包含, ￥ 或者$的金额
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0L;
        if(index == -1){
            amLong = Long.valueOf(currency+"00");
        }else if(length - index >= 3){
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));
        }else if(length - index == 2){
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);
        }else{
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");
        }
        return amLong.toString();
    }

    /**
     * @author 熊锋
     * @param return_code
     * @param return_msg
     * @date 2020/7/7 16:30
     * @description: 通知微信收到回调请求
     * @return java.lang.String
     * @throws Exception
     */
    public static String setXml(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]>"
                + "</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }
}
