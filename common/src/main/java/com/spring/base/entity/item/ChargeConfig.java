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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 费项配置
 *
 * @author zwb
 * @date 2020-04-13 17:35:46
 */
@Data
@TableName("b_charge_config")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "费项配置")
public class ChargeConfig extends Model<ChargeConfig> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value = "主键")
    private String chargeConfigId;
    /**
     * 小区ID
     */
    @NotBlank(message = "小区不能为空")
    @ApiModelProperty(value = "小区ID")
    private String cid;

    /**
     * 房间
     */
    @ApiModelProperty(value = "房间ID")
    private String hid;

    /**
     * 房间
     */
    @ApiModelProperty(value = "房间ID集合")
    @TableField(exist = false)
    private List<String> hids;

    /**
     * 小区名称
     */
    @ApiModelProperty(value = "小区名称")
    @TableField(exist = false)
    private String communityName;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @TableField(exist = false)
    private String startDate;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @TableField(exist = false)
    private String endDate;

    /**
     * 公司ID
     */
    @NotBlank(message = "公司不能为空")
    @ApiModelProperty(value = "公司ID")
    private String pcid;
    /**
     * 房屋绑定ID
     */
    @TableField(exist = false)
    private String bhcId;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @TableField(exist = false)
    private String companyName;
    /**
     * 费项类别
     */
    @NotBlank(message = "费项类别不能为空")
    @ApiModelProperty(value = "费项类别")
    private String chargeCategory;

    /**
     * 费项类别名称
     */
    @ApiModelProperty(value = "费项类别名称")
    @TableField(exist = false)
    private String chargeCategoryName;
    /**
     * 费项编码
     */
    @NotBlank(message = "费项编码为空")
    @ApiModelProperty(value = "费项编码")
    private String chargeNo;

    /**
     * 费项名称
     */
    @ApiModelProperty(value = "费项名称")
    @TableField(exist = false)
    private String chargeName;

    /**
     * 费项类型名称
     */
    @ApiModelProperty(value = "费项类型名称")
    @TableField(exist = false)
    private String chargeTypeName;
    /**
     * 费项类型
     */
    @ApiModelProperty(value = "费项类型")
    @TableField(exist = false)
    private String chargeType;
    /**
     * 费项类型
     */
    @ApiModelProperty(value = "费项类型")
    private String conRuleNo;

    /**
     * 计算公式ID
     */
    @ApiModelProperty(value = "计算公式ID")
    @TableField(exist = false)
    private String ruleNo;

    /**
     * 计算单位
     */
    @ApiModelProperty(value = "计算单位")
    @TableField(exist = false)
    private String computingUnit;

    /**
     * 计算公式
     */
    @ApiModelProperty(value = "计算公式")
    @TableField(exist = false)
    private String valuationDescription;

    /**
     * 计费方式
     */
    @ApiModelProperty(value = "计费方式(1固定，2计算公式，3.每次输入)")
    @TableField(exist = false)
    private Integer ruleType;
    /**
     * 固定金额
     */
    @ApiModelProperty(value = "固定金额")
    @TableField(exist = false)
    private String fixedAmount;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    @TableField(exist = false)
    private String price;
    /**
     * 周期
     */
    @ApiModelProperty(value = "周期")
    @TableField(exist = false)
    private String cycle;
    /**
     * 应收日期类型
     */
    @NotNull(message = "应收日期类型为空")
    @ApiModelProperty(value = "应收日期类型(1月初2月末3自定)")
    private Integer receivableType;
    /**
     * 应收日期
     */
    @ApiModelProperty(value = "应收日期")
    private String receivableDate;
    /**
     * 违约金类型
     */
    @NotNull(message = "违约金类型为空")
    @ApiModelProperty(value = "违约金类型(1应收时间2计费开始时间3计费结束时间)")
    private Integer penaltyType;
    /**
     * 违约金计费天数
     */
    @ApiModelProperty(value = "违约金计费天数")
    private String penaltyDate;
    /**
     * 违约金比例
     */
    @ApiModelProperty(value = "违约金比例")
    private String penaltyPercentage;
    /**
     * 违约金是否自动
     */
    @ApiModelProperty(value = "违约金是否自动")
    private Integer penaltyAuto;
    /**
     * 违约金计费方式
     */
    @NotNull(message = "违约金类型为空")
    @ApiModelProperty(value = "违约金计费方式(1单利2复利)")
    private Integer penaltyMode;
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
