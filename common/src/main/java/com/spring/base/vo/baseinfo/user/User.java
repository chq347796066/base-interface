package com.spring.base.vo.baseinfo.user;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Desc: 用户消息导入实体类
 * @Author:邓磊
 * @UpdateDate:2020/4/26 15:30
 */
@ApiModel
@Data
public class User {

    @Excel(name = "员工姓名")
    private String userName;

    @Excel(name = "员工状态")
    private String status;

    @Excel(name = "联系电话")
    private String mobile;

    @Excel(name = "员工性别")
    private String sex;

    @Excel(name = "出生日期")
    private String birthday;

    @Excel(name = "身份证号码")
    private String idCard;

    @Excel(name = "住址")
    private String address;

    @Excel(name = "籍贯")
    private String nativePlace;

    @Excel(name = "所属公司")
    private String companyName;

    @Excel(name = "所属小区")
    private String communityName;
}
