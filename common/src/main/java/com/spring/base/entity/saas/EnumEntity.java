package com.spring.base.entity.saas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.SaasBaseEntity;
import com.spring.base.vo.Vo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 枚举实体类
 *
 * @author WuJiaQuan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("s_enum")
public class EnumEntity extends SaasBaseEntity implements Vo<String>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 枚举Id
     */
    private String id;

    /**
     * 枚举父Id
     */
    private String parentEnumId;

    /**
     * 枚举名称
     */
    private String enumName;

    /**
     * 枚举编号
     */
    private String enumCode;

    /**
     * 枚举值
     */
    private String enumValue;

    /**
     * 枚举类型Id
     */
    private String enumTypeId;

    /**
     * 枚举类型名称
     */
    private String enumType;

    public EnumEntity() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getParentEnumId() {
        return parentEnumId;
    }

    public void setParentEnumId(String parentEnumId) {
        this.parentEnumId = parentEnumId;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
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

    public String getEnumTypeId() {
        return enumTypeId;
    }

    public void setEnumTypeId(String enumTypeId) {
        this.enumTypeId = enumTypeId;
    }

    public String getEnumType() {
        return enumType;
    }

    public void setEnumType(String enumType) {
        this.enumType = enumType;
    }
}
