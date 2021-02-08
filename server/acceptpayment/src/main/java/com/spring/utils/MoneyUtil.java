package com.spring.utils;

import java.math.BigDecimal;

/**
 * 2017-07-02 13:53
 * @author dell
 */
public class MoneyUtil {

    /**
     * 元转分
     * @param yuan
     * @return
     */
    public static Integer yuan2Fen(Double yuan) {
        return BigDecimal.valueOf(yuan).movePointRight(2).intValue();
    }

    /**
     * 分转元
     * @param fen
     * @return
     */
    public static Double fen2Yuan(Integer fen) {
        return new BigDecimal(fen).movePointLeft(2).doubleValue();
    }
}
