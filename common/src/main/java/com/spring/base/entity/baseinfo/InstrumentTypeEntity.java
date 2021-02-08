package com.spring.base.entity.baseinfo;

import java.io.Serializable;

import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-02 17:17:10
* @Desc类说明: 仪类型实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_instrument_type")
public class InstrumentTypeEntity extends BaseEntity implements Vo<Long>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键id
   @ApiModelProperty(value = "主键id")
   private Long id;

   //仪表类型
   @ApiModelProperty(value = "仪表类型")
   private String instrumentType;

   //计量单位
   @ApiModelProperty(value = "计量单位")
   private String measuringUnit;

   //仪表备注
   @ApiModelProperty(value = "仪表备注")
   private String remark;

}
