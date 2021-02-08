package com.spring.base.entity.baseinfo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-31 19:02:26
* @Desc类说明:  楼栋实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_build")
public class BuildEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   // 主键
   @ApiModelProperty(value = " 主键")
   private String id;

   //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

    //小区名称
    @ApiModelProperty(value = "小区名称")
    private String communityName;

   //楼栋名称
   @ApiModelProperty(value = "楼栋名称")
   private String buildName;

   //描述
   @ApiModelProperty(value = "楼栋描述")
   private String buildDesc;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

   //公司id
   @ApiModelProperty(value = "公司id")
   private String companyId;

   //小区id集合
   @ApiModelProperty(value = "小区id集合")
   @TableField(exist = false)
   private List<String> communityIds;

}

