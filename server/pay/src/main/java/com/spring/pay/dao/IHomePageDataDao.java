package com.spring.pay.dao;


import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


/**
 * @author dell
 * @Model: IHomePageDataDao
 * @Description: 首页收费统计
 * @Auter: Mollen
 * @Date: 2020/5/20  10:11 
 *      
 **/
@Mapper
public interface IHomePageDataDao {

    /**
     * @Method: GetTotalAmount
     * @Description: 统计当日总收入
     * @Auter: Mollen
     * @Date: 2020/5/20  10:12
     * @return 
     **/
    Map<String,Object> getTotalAmount(Map<String, Object> param) throws Exception;

    /**
     * @Method: getMaxAmoutByCid
     * @Description: 当日收入最高的小区
     * @Auter: Mollen
     * @Date: 2020/5/20  16:15
     * @return
     **/
    Map<String,Object> getMaxAmoutByCid(Map<String, Object> param) throws Exception;

    /**
     * @Method: getOrderSum
     * @Description: 当日总收费订单
     * @Auter: Mollen
     * @Date: 2020/5/20  10:16
     * @return
     **/
    Map<String,Object> getOrderSum(Map<String, Object> param) throws Exception;

    /**
     * @Method: getMaxByPayType
     * @Description: 当日收费
     * @Auter: Mollen
     * @Date: 2020/5/20  10:16
     * @return
     **/
    Map<String,Object> getMaxByPayType(Map<String, Object> param) throws Exception;



}
