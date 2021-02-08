package com.spring.base.vo.pay.payonekeypay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.base.entity.pay.BusinessExtendEntity;
import com.spring.base.entity.pay.BusinessJournalsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dell
 */
@ApiModel
@Data
@ToString
public class PayOneVo {

    //公司id
    @ApiModelProperty(value = "公司id")
    private String companyId;
    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    //小区编号
    @ApiModelProperty(value = "小区编号")
    private String plotId;
    //小区名称
    @ApiModelProperty(value = "小区名称")
    private String plotName;
    //房号
    @ApiModelProperty(value = "房号")
    private String hid;
    //楼栋id
    @ApiModelProperty(value = "楼栋id")
    private String buildId;

    //楼栋名称
    @ApiModelProperty(value = "楼栋名称")
    private String buildName;

    //单元id
    @ApiModelProperty(value = "单元id")
    private String cellId;

    //单元名称
    @ApiModelProperty(value = "单元名称")
    private String cellName;

    //房屋编号
    @ApiModelProperty(value = "房屋编号")
    private String houseCode;
    //房屋名称
    @ApiModelProperty(value = "房屋名称")
    private String houseName;
    //业主编号
    @ApiModelProperty(value = "业主编号")
    private String ownerNo;
    //业主姓名
    @ApiModelProperty(value = "业主姓名")
    private String ownerName;
    //手机号
    @ApiModelProperty(value = "手机号")
    private String phone;
    //实际支付金额
    @ApiModelProperty(value = "实际支付金额")
    private String actualAmount;
    //支付类型编码
    @ApiModelProperty(value = "支付类型编码")
    private String paytype;
    //支付类型名称
    @ApiModelProperty(value = "支付类型名称")
    private String paytypeName;
    //是否收取违约金1收取0不收取
    @ApiModelProperty(value = "是否收取违约金（1收取0不收取）")
    private String iscollDelayPay;
    //房屋面积
    @ApiModelProperty(value = "房屋面积")
    private String area;
    //房屋类型
    @ApiModelProperty(value = "房屋类型")
    private String houseType;
    //房屋状态
    @ApiModelProperty(value = "房屋状态")
    private String houseStatus;
    //收费时间
    @ApiModelProperty(value = "收费时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date payDate;
    //收费备注
    @ApiModelProperty(value = "收费备注")
    private String chargeMark;
    //所属终端
    @ApiModelProperty(value = "所属终端")
    private String terminIdentify;
    //所属终端
    @ApiModelProperty(value = "操作者")
    private String operatorName;
    //所属终端
    @ApiModelProperty(value = "操作者编码")
    private String operatorNo;

    //交易类型
    @ApiModelProperty(value = "交易类型")
    private String transType;


    //交易号
    private String transId;

    //交易时间
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;

    //账单详情列表
    @ApiModelProperty(value = "账单详情列表")
    private List<BillDetailVo> billDetailVos;

    //临时收费列表
    @ApiModelProperty(value = "临时收费列表")
    private List<TemporaryPayVo> temporaryPayVos;

    //综合预缴列表
    @ApiModelProperty(value = "综合预缴列表")
    private List<PrePayVo> prePayVos;

    //交易流水记录表
    List<BusinessJournalsEntity> businessJournalsEntities  = new ArrayList<>();
    //业务流水扩展表
    List<BusinessExtendEntity> businessExtendEntities = new ArrayList<>();




}
