package com.spring.base.entity.saas;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.spring.base.entity.SaasBaseEntity;
import com.spring.base.vo.Vo;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-10 10:09:25
 * @Desc类说明: 运营用户列实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("s_user")
public class UserEntity extends SaasBaseEntity implements Vo<String>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private String id;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String userName;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;
	/**
	 * 创建日期
	 */
	@ApiModelProperty(value = "创建日期")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

}
