package com.spring.common.enums;

/**
 * 统计时间类型枚举
 *
 * @author WuJiaQuan
 */
public enum StatisticsTimeTypeEnum {
    /**
     * 按年统计
     */
    STATICS_BY_YEAR(1, "按年统计"),

    /**
     * 按月统计
     */
    STATICS_BY_MONTH(2, "按月统计"),

    /**
     * 按日统计
     */
    STATICS_BY_DAY(3, "按日统计");

    /**
     * 枚举编号
     */
    private final int enumCode;

    /**
     * 枚举值
     */
    private final String enumValue;

    StatisticsTimeTypeEnum(int enumCode, String enumValue) {
        this.enumCode = enumCode;
        this.enumValue = enumValue;
    }

    public int getEnumCode() {
        return this.enumCode;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
