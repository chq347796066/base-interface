package com.spring.base.entity.saas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.SaasBaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-06-30 15:55:33
* @Desc类说明: 首页轮播信息实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("s_home_banner")
public class HomeBannerEntity extends SaasBaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //bannerId
   @ApiModelProperty(value = "id")
   private String id;

   //banner图片地址
   @ApiModelProperty(value = "banner图片地址")
   private String imagePath;

   //序号
   @ApiModelProperty(value = "序号")
   private Integer seqNum;

}
