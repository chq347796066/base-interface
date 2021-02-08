package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 枚举值键值对Vo
 *
 * @author WuJiaQuan
 */
@ApiModel
@Data
@ToString
public class EnumKeyValueVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 枚举类型名称
     */
    @ApiModelProperty(value = "枚举类型")
    private String enumType;

    /**
     * 枚举编号
     */
    @ApiModelProperty(value = "枚举编号")
    private String enumCode;

    /**
     * 枚举值
     */
    @ApiModelProperty(value = "枚举值")
    private String enumValue;

    public EnumKeyValueVo() {
    }

    public String getEnumType() {
        return enumType;
    }

    public void setEnumType(String enumType) {
        this.enumType = enumType;
    }

    public String getEnumCode() {
        return enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(String enumValue) {
        this.enumValue = enumValue;
    }
}
