package com.spring.pay.service.impl;


import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.arithmetic.BigDecimalUtil;
import com.spring.common.util.date.DateUtil;
import com.spring.pay.dao.IHomePageDataDao;
import com.spring.pay.service.IHomePageDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dell
 * @Model: HomePageDataServiceImpl
 * @Description: 首页收费统计
 * @Auter: Mollen
 * @Date: 2020/5/20  9:59
 *
 **/
@Slf4j
@Service("HomePageDataServiceImpl")
public class HomePageDataServiceImpl implements IHomePageDataService {

    @Autowired
    private IHomePageDataDao homePageDataDao;

    /**
     * @Method: GetTotalAmount
     * @Description: 统计当日总收入
     * @Auter: Mollen
     * @Date: 2020/5/20  16:21
     * @return 
     **/
    @Override
    public ApiResponseResult getTotalAmount() throws Exception {

        ApiResponseResult result = new ApiResponseResult();

        // 今日数据
        Map<String,Object> param = new HashMap<>(16);
        param.put("date", DateUtil.getDateStr(new Date(),"yyyyMMdd"));
        Map<String, Object> nowMap = homePageDataDao.getTotalAmount(param);

        // 昨日数据
        param.clear();
        param.put("date",DateUtil.getDateStrBefore("yyyyMMdd"));
        Map<String, Object> beforeMap = homePageDataDao.getTotalAmount(param);

        // 计算收入增比
        Double nowAmount = Double.parseDouble(nowMap.get("amount")+"");
        Double beforeAmout = Double.parseDouble(beforeMap.get("amount")+"");
        String addRate = "";
        double num=0.00;
        if(beforeAmout == 0.0 || beforeAmout == num){
            addRate = "100";
        }else {
            addRate = BigDecimalUtil.divs((nowAmount - beforeAmout) * 100 + "", beforeAmout + "", 2);
        }
        nowMap.put("addRate",addRate+"%");
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("成功");
        result.setData(nowMap);
        return result;

    }

    
    /**
     * @Method: getMaxAmoutByCid
     * @Description: 统计当日收费最高小区
     * @Auter: Mollen
     * @Date: 2020/5/20  16:21
     * @return 
     **/
    @Override
    public ApiResponseResult getMaxAmoutByCid() throws Exception {

        ApiResponseResult result = new ApiResponseResult();

        Map<String,Object> param = new HashMap<>(16);
        param.put("date", DateUtil.getDateStr(new Date(),"yyyyMMdd"));
        Map<String, Object> map = homePageDataDao.getMaxAmoutByCid(param);

        result.setCode(MessageCode.SUCCESS);
        result.setMsg("成功");
        result.setData(map);
        return result;

    }

    
    /**
     * @Method: getOrderSum
     * @Description: 统计当日总订单
     * @Auter: Mollen
     * @Date: 2020/5/20  16:22
     * @return 
     **/
    @Override
    public ApiResponseResult getOrderSum() throws Exception {

        ApiResponseResult result = new ApiResponseResult();

        // 今日数据
        Map<String,Object> param = new HashMap<>(16);
        param.put("date", DateUtil.getDateStr(new Date(),"yyyyMMdd"));
        Map<String, Object> nowMap = homePageDataDao.getOrderSum(param);

        // 昨日数据
        param.clear();
        param.put("date",DateUtil.getDateStrBefore("yyyyMMdd"));
        Map<String, Object> beforeMap = homePageDataDao.getOrderSum(param);

        // 计算收入增比
        Double nowAmount = Double.parseDouble(nowMap.get("count")+"");
        Double beforeAmout = Double.parseDouble(beforeMap.get("count")+"");
        String addRate = "";
        double num=0.00;
        if(beforeAmout == 0.0 || beforeAmout == num){
            addRate = "100";
        }else {
            addRate = BigDecimalUtil.divs((nowAmount - beforeAmout) * 100 + "", beforeAmout + "", 2);
        }
        nowMap.put("addRate",addRate+"%");
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("成功");
        result.setData(nowMap);
        return result;

    }


    /**
     * @Method: getMaxByPayType
     * @Description: 统计当日收费最多渠道
     * @Auter: Mollen
     * @Date: 2020/5/20  16:28
     * @return 
     **/
    @Override
    public ApiResponseResult getMaxByPayType() throws Exception {

        ApiResponseResult result = new ApiResponseResult();

        Map<String,Object> param = new HashMap<>(16);
        param.put("date", DateUtil.getDateStr(new Date(),"yyyyMMdd"));
        Map<String, Object> map = homePageDataDao.getMaxByPayType(param);

        result.setCode(MessageCode.SUCCESS);
        result.setMsg("成功");
        result.setData(map);
        return result;

    }


}
