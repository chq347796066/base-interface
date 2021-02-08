package com.spring.base.vo.baseinfo.user;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.entity.baseinfo.PicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * @Desc:    系统管理用户管理　业主APP用户 实体类
 * @Author:邓磊
 * @UpdateDate:2020/5/7 16:03
 * @return: 返回
 */
@ApiModel
@Data
public class UserAppMyHouseVo {
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "住戶類型")
    private String houseHoldType;

    @ApiModelProperty(value = "用户LOGO头像图片")
    private String userLogoId;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "电话号码")
    private String mobile;

    @ApiModelProperty(value = "证件号码")
    private String idCard;

    @ApiModelProperty(value = "来源渠道")
    private String sourceChannel;

    @ApiModelProperty(value = "注册时间")
    private Date createDate;

    @ApiModelProperty(value = "登录时间")
    private String loginTime;

    @ApiModelProperty(value = "用户密码")
    private  String password;

    //用户LOGO头像图片
    @ApiModelProperty(value = "用户LOGO头像图片)")
    private List<PicEntity> picEntityList;

    //审核通过的房屋
    @ApiModelProperty(value = "审核通过的房屋")
    List<MyHouseEntity> myHouseEntityList;
}
