package com.spring.base.vo.buiness.myhouse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-17 09:46:08
 * @Desc类说明: 更新我的房屋信息vo
 */
@ApiModel
@Data
@ToString
public class MyHouseQueryVo {

	//用户Id
	@ApiModelProperty(value = "用户Id")
	private String userId;
}
