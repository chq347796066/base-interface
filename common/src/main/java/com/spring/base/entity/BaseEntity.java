package com.spring.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 熊锋
 * @date 2020/4/1 14:21
 * @Desc类说明:数据库公共属性
 */
@Data
@ApiModel
public class BaseEntity implements Serializable {

    //创建人
    @ApiModelProperty(value = "创建人")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    //修改人
    @ApiModelProperty(value = "修改人")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifyUser;

    //修改时间
    @ApiModelProperty(value = "修改时间")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyDate;

    //删除标志
    @ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;

    //租户id
    @ApiModelProperty(value = "租户id")
    @JsonIgnore
    private String tenantId;

//    //状态(1启用,2禁用)
//    @ApiModelProperty(value = "状态(1启用,2禁用)")
//    private Integer status;
}
