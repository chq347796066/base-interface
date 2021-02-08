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
* @date : 创建时间：2020-04-14 15:31:38
* @Desc类说明: 图片中间实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_pic")
public class PicEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键ID
   @ApiModelProperty(value = "主键ID")
   private String id;

   //外键ID
   @ApiModelProperty(value = "外键ID")
   private String tableId;

    //图片ID
    @ApiModelProperty(value = "图片ID")
    private String dataId;

    //图片名称
    @ApiModelProperty(value = "图片名称")
    private String name;

    //图片URL地址链接
    @ApiModelProperty(value = "图片URL地址链接")
    private String  url;

   //状态，1-启用，2-关闭
   @ApiModelProperty(value = "图片类型（1公司LOG 2营业执照）")
   private Integer status;

}
