package com.spring.base.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-03 16:33:19
* @Desc类说明: 文档实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_document")
public class DocumentEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键
   @ApiModelProperty(value = "主键")
   private String id;

   //文档名称
   @ApiModelProperty(value = "文档名称")
   private String docName;

   //文档类型，普通附件或图片,或视频
   @ApiModelProperty(value = "文档类型，普通附件或图片,或视频")
   private String docType;

   //文档路径
   @ApiModelProperty(value = "文档路径")
   private String docPath;

   //文档全路径
   @ApiModelProperty(value = "文档全路径")
   private String fullPath;

   //上传后文档名称
   @ApiModelProperty(value = "上传后文档名称")
   private String uploadName;

   //扩展名
   @ApiModelProperty(value = "扩展名")
   private String extend;

   //文件大小
   @ApiModelProperty(value = "文件大小")
   private Integer size;

   //状态(1启用,2禁用)
   @ApiModelProperty(value = "状态(1启用,2禁用)")
   private Integer status;


}
