package com.spring.pay.service;


import com.spring.base.entity.pay.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    void create(Order order);
}
