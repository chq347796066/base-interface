package com.spring.base.vo.saas;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-10 10:09:25
 * @Desc类说明: 更新运营用户列vo
 */
@ApiModel
@Data
@ToString
public class UserUpdateVo  {
	
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
