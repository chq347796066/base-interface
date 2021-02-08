package com.spring.base.vo.buiness.complaintproposal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.base.entity.baseinfo.PicEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-11 15:49:31
* @Desc类说明: 业主投诉建议实体类
*/
@ApiModel
@Data
public class ComplaintProposalEntityVo implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;

   //主键id
   @ApiModelProperty(value = "主键id")
   private String id;

   //投诉描述
   @ApiModelProperty(value = "投诉描述")
   private String complaintDescribe;

   //投诉图片ID
   @ApiModelProperty(value = "投诉图片ID")
   private String pictureId;

   //用户id
   @ApiModelProperty(value = "用户id")
   private String userId;

   //用户姓名
   @ApiModelProperty(value = "用户姓名")
   private String userName;

   //手机号码
   @ApiModelProperty(value = "手机号码")
   private String mobile;

   //小区详细地址
   @ApiModelProperty(value = "小区详细地址")
   private String complaintAddress;

   //提交人
   @ApiModelProperty(value = "提交人")
   private String submitUser;

   //投诉状态（1 待处理 2 已完成 3 已评价　４已取消）
   @ApiModelProperty(value = "投诉状态（1 待处理 2 已完成 3 已评价　４已取消）")
   private Integer proposalStatus;

   //审核完成处理结果
   @ApiModelProperty(value = "审核完成处理结果")
   private String resultDescribe;

   //审核完成处理结果图片ID
   @ApiModelProperty(value = "审核完成处理结果图片ID")
   private String resultId;

   //取消原因（1 原因１ 2 原因２ 3 原因３　４其他）
   @ApiModelProperty(value = "取消原因（1 原因１ 2 原因２ 3 原因３　４其他）")
   private Integer canceState;

   //取消描述
   @ApiModelProperty(value = "取消描述")
   private String canceDescribe;

   //评价状态（1 好评 2 中评 3 差评）
   @ApiModelProperty(value = "评价状态（1 好评 2 中评 3 差评）")
   private Integer evaluationStatus;

   //评价描述
   @ApiModelProperty(value = "评价描述")
   private String evaluationDescribe;

   //投诉建议开始日期
   @ApiModelProperty(value = "投诉建议开始日期")
   private String startDate;

   //投诉建议完成日期
   @ApiModelProperty(value = "投诉建议完成日期")
   private String endDate;

   //创建人
   @ApiModelProperty(value = "创建人")
   private String createUser;

   //创建时间
   @ApiModelProperty(value = "创建时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
   private Date createDate;

   //修改人
   @ApiModelProperty(value = "修改人")
   private String modifyUser;

   //修改时间
   @ApiModelProperty(value = "修改时间")
   private Date modifyDate;

   //预留字段
   @ApiModelProperty(value = "预留字段")
   private String proposalReserve;

   //删除标志（0 未删除 1 已删除）
   @ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
   private Integer delFlag;

   //小区id
   @ApiModelProperty(value = "小区id")
   private String communityId;

   //公司id
   @ApiModelProperty(value = "公司id")
   private String companyId;

   //租户id
   @ApiModelProperty(value = "租户id")
   private String tenantId;


    //投诉建议图片
    @ApiModelProperty(value = "投诉建议图片)")
    private List<PicEntity> picEntityList;


    //审核处理结果图片
    @ApiModelProperty(value = "审核处理结果图片)")
    private List<PicEntity> picPictureList;

}
