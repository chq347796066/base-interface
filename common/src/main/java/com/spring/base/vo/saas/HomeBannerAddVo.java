package com.spring.base.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-06-30 15:55:33
* @Desc类说明: 新增首页轮播信息vo
*/

@ApiModel
@Data
@ToString
public class HomeBannerAddVo {

   //bannerId
   @ApiModelProperty(value = "id")
   private String id;

   //banner图片地址
   @ApiModelProperty(value = "banner图片地址")
   @NotBlank(message = "banner图片地址不能为空")
   private String imagePath;

   //序号
   @ApiModelProperty(value = "序号")
   private Integer seqNum;
}
