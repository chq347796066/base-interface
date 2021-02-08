package com.spring.base.vo.baseinfo.home;

import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * @Desc: 首页工单统计实体类
 * @Author:邓磊
 * @UpdateDate:2020/4/29 9:39
 */
@ApiModel
@Data
public class HomeVo implements Vo<String> {
	//公司id
	@ApiModelProperty(value = "公司id")
	@NotNull(message = "公司id不能为空")
	private String companyId;

	@ApiModelProperty(value = "工单总数")
	private Integer sum;

	@ApiModelProperty(value = "完成数")
	private Integer fulfilnNumber;

	@ApiModelProperty(value = "待派单数")
	private Integer waitingNumber;

	@ApiModelProperty(value = "待接单数")
	private Integer stayNumber;

	@ApiModelProperty(value = "处理中数")
	private Integer processingNumber;

	@ApiModelProperty(value = "完成率")
	private BigDecimal completionRate;

	@ApiModelProperty(value = "处理中率")
	private BigDecimal processing;

	@Override
	public String getId() {
		return null;
	}
	@Override
	public void setId(String id) {

	}
}
