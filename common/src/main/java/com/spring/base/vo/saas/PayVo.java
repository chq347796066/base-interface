package com.spring.base.vo.saas;

import lombok.Data;

/**
 * @author 熊锋
 * @version 1.0.0
 * CreateDate 2020/7/7 19:27
 * description:支付实体类
 */
@Data
public class PayVo {

    /**
     * 支付金额
     */
    private String totalFee;

    /**
     * 商品id(应用id)
     */
    private String goodId;

    /**
     * 订单号
     */
    private String orderNum;
}
