package com.spring.base.vo.baseinfo.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-07 17:49:59
* @Desc类说明: 更新通知公告vo
*/
@ApiModel
@Data
@ToString
public class NoticeUpdateVo  {

   //主键id
   @ApiModelProperty(value = "主键id")
   private String id;

   //公司代码
   @ApiModelProperty(value = "公司代码")
   private String companyId;

   //公司名称
   @ApiModelProperty(value = "公司名称")
   private String companyName;

   //小区Id
   @ApiModelProperty(value = "小区Id")
   private String communityId;

   //小区名称
   @ApiModelProperty(value = "小区名称")
   private String communityName;

   //用户id
   @ApiModelProperty(value = "用户id")
   private String userId;

   //用户姓名
   @ApiModelProperty(value = "用户姓名")
   private String userName;

   //消息类型（1 系统消息 2 小区公告消息 3员工消息）
   @ApiModelProperty(value = "消息类型（1 系统消息 2 小区公告消息 3员工消息）")
   private Integer noticeType;

   //标题
   @ApiModelProperty(value = "标题")
   private String noticeTitle;

    //发布对象
    @ApiModelProperty(value = "发布对象（1 所有员工 2 本部员工 3小区员工）")
    private String publishType;

   //消息类型（1 全选 2 业主APP 3物业APP）
   @ApiModelProperty(value = "消息类型（1 全选 2 业主APP 3物业APP）")
   private Integer productType;

   //有效开始时间
   @ApiModelProperty(value = "有效开始时间")
   private String startDate;

   //有效结束时间
   @ApiModelProperty(value = "有效结束时间")
   private String endDate;

   //内容
   @ApiModelProperty(value = "内容")
   private String noticeContent;

   //发布时间
   @ApiModelProperty(value = "发布时间")
   private String releaseDate;

   //下线日期
   @ApiModelProperty(value = "下线日期")
   private String offlineDate;

   //发布状态（1 未发布 2 已发布 3 已下线）
   @ApiModelProperty(value = "发布状态（1 未发布 2 已发布 3 已下线）")
   private Integer releaseStatus;

   //删除标志（0 未删除 1 已删除）
   @ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
   private Integer delFlag;

   //租户id
   @ApiModelProperty(value = "租户id")
   private String tenantId;

   //租户公司数组
   @ApiModelProperty(value = "租户公司数组")
   private String[] tenantCompanyArray;
}
