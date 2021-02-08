package com.spring.base.entity.buiness;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-11 16:09:13
* @Desc类说明: 报修派工人员实体类
*/
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_repair_personnel")
public class RepairPersonnelEntity extends BaseEntity implements Vo<String>, Serializable {
    private static final long serialVersionUID = 1L;

    //主键id
    @ApiModelProperty(value = "主键id")
    private String id;

    //业主报事报修主键id
    @ApiModelProperty(value = "业主报事报修主键id")
    private String reportingRepairId;

    //用户id
    @ApiModelProperty(value = "用户id")
    private String userId;

    //用户姓名
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    //手机号码
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    //预留字段
    @ApiModelProperty(value = "预留字段")
    private String repairReserve;

}
