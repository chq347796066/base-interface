package com.spring.base.entity.buiness;

import com.spring.base.entity.BaseEntity;
import com.spring.base.entity.baseinfo.PicEntity;
import com.spring.base.vo.Vo;
import com.spring.base.vo.buiness.repairpersonnel.RepairPersonnelAddVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-11 16:03:31
* @Desc类说明: 业主报事报修实体类
*/
@ApiModel
@Data
public class ReportingRepairEntity extends BaseEntity implements Vo<String>, Serializable {
   private static final long serialVersionUID = 1L;
   //主键id
   @ApiModelProperty(value = "主键id")
   private String id;

    //小区id
    @ApiModelProperty(value = "小区id")
    private String communityId;

    //小区名称
    @ApiModelProperty(value = "小区名称")
    private String communityName;

    //楼栋Id
    @ApiModelProperty(value = "楼栋Id")
    private String buildId;

    //楼栋名称
    @ApiModelProperty(value = "楼栋名称")
    private String buildName;

    //单元Id
    @ApiModelProperty(value = "单元Id")
    private String cellId;

    //单元名称
    @ApiModelProperty(value = "单元名称")
    private String cellName;

    //房产Id
    @ApiModelProperty(value = "房产Id")
    private String houseId;

    //房屋编码
    @ApiModelProperty(value = "房屋编码")
    private String houseCode;

   //报修描述
   @ApiModelProperty(value = "报修描述")
   private String reportingDescribe;

   //报修图片ID
   @ApiModelProperty(value = "报修图片ID")
   private String repairId;

   //报修类型（1 个人报修 2 公共报修）
   @ApiModelProperty(value = "报修类型（1 个人报修 2 公共报修）")
   private Integer repairType;

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

   //报修状态（1 待派单 2 待接单 3 处理中　４待确认 5 已完成 6 已评价 7 已取消）
   @ApiModelProperty(value = "报修状态（1 待派单 2 待接单 3 处理中　４待确认 5 已完成 6 已评价 7 已取消）")
   private Integer reportingStatus;

   //取消原因（1 原因１ 2 原因２ 3 原因３　４其他）
   @ApiModelProperty(value = "取消原因（1 原因１ 2 原因２ 3 原因３　４其他）")
   private Integer canceState;

   //取消描述
   @ApiModelProperty(value = "取消描述")
   private String canceDescribe;

   //维修费用
   @ApiModelProperty(value = "维修费用")
   private BigDecimal outlay;

   //维修明细
   @ApiModelProperty(value = "维修明细")
   private String repairDetail;

   //维修费用图片ID
   @ApiModelProperty(value = "维修费用图片ID")
   private String outlayId;

   //评价状态（1 好评 2 中评 3 差评）
   @ApiModelProperty(value = "评价状态（1 好评 2 中评 3 差评）")
   private Integer evaluationStatus;

   //评价描述
   @ApiModelProperty(value = "评价描述")
   private String evaluationDescribe;

    //派修时间
    @ApiModelProperty(value = "派修时间")
    private String dispatchDate;

    //维修人员完成时间
    @ApiModelProperty(value = "维修人员完成时间")
    private String finishDate;

    //维修人员接受派工时间
    @ApiModelProperty(value = "维修人员接受派工时间")
    private String repairerReacceptDate;

   //报事报修开始日期
   @ApiModelProperty(value = "报事报修开始日期")
   private String startDate;

   //报事报修完成日期
   @ApiModelProperty(value = "报事报修完成日期")
   private String endDate;

   //预留字段
   @ApiModelProperty(value = "预留字段")
   private String repairReserve;

    //业主报修图片
    @ApiModelProperty(value = "业主报修图片)")
    private List<PicEntity> picEntityList;

    ////维修人员上传图片
    @ApiModelProperty(value = "业主报修图片)")
    private List<PicEntity> repairPersonnelList;

    //报修派工人员
    @ApiModelProperty(value = "报修派工人员")
    List<RepairPersonnelAddVo> personnelAddVoList;


    @ApiModelProperty(value = "接收报修地址，报修人，联系电话)")
    private String repairReceiveType;

    //租户id
    @ApiModelProperty(value = "租户id")
    private String tenantId;

    //公司id
    @ApiModelProperty(value = "公司id")
    private String companyId;

}
