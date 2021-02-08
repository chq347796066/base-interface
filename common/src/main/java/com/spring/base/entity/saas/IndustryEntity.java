package com.spring.base.entity.saas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.SaasBaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 17:10:11
 * @Desc类说明: 行业实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("s_industry")
public class IndustryEntity extends SaasBaseEntity implements Vo<String>, Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键id
	@ApiModelProperty(value = "主键id")
	private String id;
	
	//行业名称
	@ApiModelProperty(value = "行业名称")
	private String industryName;

}
