package com.spring.base.entity.pay;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@ApiModel
@Data
@TableName("order")
public class Order {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long  userId;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "金额")
    private BigDecimal money;

    /**
     * 订单状态：0：创建中；1：已完结
     */
    @ApiModelProperty(value = "订单状态：0：创建中；1：已完结")
    private Integer status;

}
