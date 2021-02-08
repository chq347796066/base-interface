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
 * @Desc类说明: 公司规模实体类
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("s_company_scale")
public class CompanyScaleEntity extends SaasBaseEntity implements Vo<String>, Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	@ApiModelProperty(value = "id")
	private String id;
	
	//公司规模
	@ApiModelProperty(value = "公司规模")
	private String scaleName;

}
