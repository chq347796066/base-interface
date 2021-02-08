package com.spring.base.entity.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
public class BdHousePrepayInfoDto {

    //公司名称
    private String companyName;

    //公司编号
    private String companyId;

    //小区名称
    private String communityName;

    //小区编号
    private String cid;

    //楼栋名称
    private String buildName;

    //楼栋id
    private String buildId;

    //单元名称
    private String cellName;

    //单元id
    private String cellId;

    //房屋编码
    private String houseCode;

    //房屋编号
    private String hid;

    //业主编码
    private String ownerNo;

    //业主名称
    private String ownerName;

    //手机号
    private String phone;

    //预缴金额
    private String unpaidAmount;

    //预缴时间
    //@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private String updateTime;

    // 序号
    private String index;

    private String transId;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;

}
