package com.spring.base.vo.saas;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-07-01 17:10:11
* @Desc类说明: 新增租户信息vo
*/

@ApiModel
@Data
@ToString
public class MyAccountVo {

   /**
    * 公司名称
   */
   @ApiModelProperty(value = "租户名称")
   private String companyName;

   /**
    * 服务编号
    */
   @ApiModelProperty(value = "服务编号")
   private String serviceNum;

   /**
    * 应用id
    */
   @ApiModelProperty(value = "应用id")
   private String appId;

   /**
    * 开始时间
    */
   @ApiModelProperty(value = "开始时间")
   @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
   private LocalDate startTime;

   /**
    * 到期时间
    */
   @ApiModelProperty(value = "到期时间")
   @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
   private LocalDate endTime;

   /**
    * 已使用天数
    */
   @ApiModelProperty(value = "已使用天数")
   private Integer userDay;

   /**
    * 剩余天数
    */
   @ApiModelProperty(value = "剩余天数")
   private Integer residueDay;
}
