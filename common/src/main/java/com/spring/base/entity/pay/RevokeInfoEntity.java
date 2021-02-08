package com.spring.base.entity.pay;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-05-20 10:37:32
* @Desc类说明: 撤销记录实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("p_revoke_info")
public class RevokeInfoEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //小区编号
   @ApiModelProperty(value = "小区编号")
   private String plotId;

   //流水号
   @ApiModelProperty(value = "流水号")
   private String transId;

   //撤销原因
   @ApiModelProperty(value = "撤销原因")
   private String undoReason;

   //操作员编号
   @ApiModelProperty(value = "操作员编号")
   private String operatorNo;

   //操作员姓名
   @ApiModelProperty(value = "操作员姓名")
   private String operatorName;

   //最后更新时间
   @ApiModelProperty(value = "最后更新时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
   private String updateTime;

   //反向流水号
   @ApiModelProperty(value = "反向流水号")
   private String revokeTransId;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;


}
