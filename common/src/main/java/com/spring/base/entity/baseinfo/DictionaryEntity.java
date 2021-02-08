package com.spring.base.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-18 17:32:18
* @Desc类说明: 字典实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@TableName("b_dictionary")
public class DictionaryEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //父ID
   @ApiModelProperty(value = "父ID")
   private String dictParentId;

    //类型
    @ApiModelProperty(value = "类型")
    private String dictParentCode;

   //代码
   @ApiModelProperty(value = "代码")
   private String dictCode;

   //名称
   @ApiModelProperty(value = "名称")
   private String dictName;

   //字典类型
   @ApiModelProperty(value = "字典类型")
   private String dictType;

   //层级
   @ApiModelProperty(value = "层级")
   private Integer dictLevel;

   //排序
   @ApiModelProperty(value = "排序")
   private Integer dictOrder;

   //备注
   @ApiModelProperty(value = "备注")
   private String dictDesc;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;

   //租户id
   @ApiModelProperty(value = "租户id")
   private String tenantId;
}
