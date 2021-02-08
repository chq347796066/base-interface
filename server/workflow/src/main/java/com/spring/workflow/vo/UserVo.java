package com.spring.workflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author 作者：赵进华
 * @version 创建时间：2019年9月26日 上午9:43:31
 * @Desc类说明:审核用户vo
 */

@ApiModel
@Data
@ToString(callSuper = true)
public class UserVo {

	@ApiModelProperty(value = "审核用户id")
	private String userId;

	@ApiModelProperty(value = "审核用户名称")
	private String userName;

}
