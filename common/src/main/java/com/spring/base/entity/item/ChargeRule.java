package com.spring.base.entity.item;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 计算公式
 *
 * @author zwb
 * @date 2020-04-16 17:32:36
 */
@Data
@TableName("b_charge_rule")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "计算公式")
public class ChargeRule extends Model<ChargeRule> {
    private static final long serialVersionUID = 1L;

    /**
     * 配置规则编号
     */
    @TableId
    @ApiModelProperty(value = "配置规则编号")
    private String conRuleNo;
    /**
     * 计费方式
     */
    @ApiModelProperty(value = "计费方式(1固定，2计算公式，3.每次输入)")
    private Integer ruleType;
    /**
     * 固定金额
     */
    @ApiModelProperty(value = "固定金额")
    private String fixedAmount;
    /**
     * 公式ID
     */
    @ApiModelProperty(value = "公式ID")
    private String ruleNo;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private String price;
    /**
     * 周期
     */
    @ApiModelProperty(value = "周期")
    private String cycle;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyDate;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifyUser;

}
