package com.spring.base.entity.item;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.common.util.date.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 房屋管理
 *
 * @author zwb
 * @date 2020-04-13 17:35:56
 */
@Data
@TableName("b_house_charge")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "房屋管理")
public class HouseCharge extends Model<HouseCharge> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value = "主键")
    private String bhcId;
    /**
     * 收费配置
     */
    @ApiModelProperty(value = "收费配置")
    private String chargeConfigId;
    /**
     * 小区ID
     */
    @NotBlank(message = "小区ID不能为空")
    @ApiModelProperty(value = "小区ID")
    private String cid;
    /**
     * 房号
     */
    @ApiModelProperty(value = "房号")
    private String hid;
    /**
     * 费项编码
     */
    @ApiModelProperty(value = "费项编码")
    private String chargeNo;
    /**
     * 费项开始时间
     */
    @ApiModelProperty(value = "费项开始时间")
    private String startDate;
    /**
     * 费项截止时间
     */
    @ApiModelProperty(value = "费项截止时间")
    private String endDate;

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
