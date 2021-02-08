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
 * 数据字典
 *
 * @author zwb
 * @date 2020-04-13 17:35:36
 */
@Data
@TableName("b_bus_dict")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "数据字典")
public class BusDict extends Model<BusDict> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value = "主键")
    private String dictId;
    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;
    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典值")
    private String dictValue;
    /**
     * 字典含义
     */
    @ApiModelProperty(value = "字典含义")
    private String dictDestination;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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
