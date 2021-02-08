package com.spring.base.entity.item;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.common.util.date.DateUtil;
import com.spring.common.validate.ValidateGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 费项类表
 *
 * @author zwb
 * @date 2020-04-13 17:24:11
 */
@Data
@TableName("b_item_charge")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "费项类表")
public class ItemCharge extends Model<ItemCharge> {
    private static final long serialVersionUID = 1L;
    /**
     * 费项编码
     */
    @TableId
    @ApiModelProperty(value = "费项编码")
    @NotNull(groups = {ValidateGroups.Update.class},message = "费项编码为空")
    private String chargeNo;

    /**
     * 费项名称
     */
    @ApiModelProperty(value = "费项名称")
    @NotBlank(message="费项名称不能为空")
    private String chargeName;
    /**
     * 费项科目
     */
    @ApiModelProperty(value = "费项科目")
    @NotBlank(message="费项科目不能为空")
    private String chargeType;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 费项类别名称
     */
    @ApiModelProperty(value = "费项类别名称")
    @TableField(exist = false)
    private String chargeTypeName;
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
