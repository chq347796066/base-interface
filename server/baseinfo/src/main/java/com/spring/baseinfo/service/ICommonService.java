package com.spring.baseinfo.service;

import com.spring.common.response.ApiResponseResult;

/**
 * @description:公共接口类
 * @return:
 * @author: 赵进华
 * @time: 2020/4/9 14:05
 */
public interface ICommonService {

    /**
     * @description:发送短信
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 14:07
     */
    ApiResponseResult sendSms(String tel)throws Exception;


    /**
     * @description:判断短信是否过期
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 14:35
     */
    ApiResponseResult checkSms(String smsCode)throws Exception;

    /**
     * @description:员工注册成功发送短信
     * @return:
     * @author: 熊锋
     * @time: 2021/1/18 14:07
     */
    ApiResponseResult sendMessage(String tel,String password,Integer tag)throws Exception;
}
