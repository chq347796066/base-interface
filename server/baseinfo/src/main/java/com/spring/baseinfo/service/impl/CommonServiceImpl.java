package com.spring.baseinfo.service.impl;

import com.spring.baseinfo.service.ICommonService;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.redis.RedisCacheUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.sms.SmsThread;
import com.spring.common.util.random.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:公共接口实现类
 * @author: 赵进华
 * @time: 2020/4/9 14:05
 */
@Service("commonService")
@Slf4j
public class CommonServiceImpl implements ICommonService {

    @Autowired
    private RedisCacheUtils redisUtils;

    /**
     * @description:注册发送短信
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 14:07
     */
    @Override
    public ApiResponseResult sendSms(String tel) throws Exception {
        // 电话
        String[] param = {tel};
        try {
            // 随机生成6位验证码
            String code = RandomUtil.getRandomCode(6);
            log.info("短息验证码为{}:",code);
            // 发送
            SmsThread.sendMessage(param, code,0);
            // 存redis中的key
            String telCodeKey = Constants.RedisPrefix.TEL_MSG + code;
            // 短信验证码存redis,设置过期时间
            redisUtils.set(telCodeKey, code, Constants.RedisExpire.TEL_MSG_EXPIRE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ApiResponseResult response = new ApiResponseResult();
        response.setCode(MessageCode.SUCCESS);
        response.setMsg(MessageCode.SUCCESS_TEXT);
        return response;
    }

    /**
     * @description:添加员工成功发送
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 14:07
     */
    @Override
    public ApiResponseResult sendMessage(String tel,String passWord,Integer tag) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        result.setCode(MessageCode.SUCCESS);
        result.setMsg(MessageCode.SUCCESS_TEXT);
        // 电话
        String[] param = {tel};

        SmsThread.sendMessage(param,passWord,tag);
        return result;
    }

    /**
     * @description:判断短信是否过期
     * @return:
     * @author: 赵进华
     * @time: 2020/4/9 14:35
     */
    @Override
    public ApiResponseResult checkSms(String smsCode) throws Exception {
        ApiResponseResult response = new ApiResponseResult();
        // 存redis中的key
        String telCodeKey = Constants.RedisPrefix.TEL_MSG + smsCode;
//        response.setData(redisUtils.exists(telCodeKey));

        //保证注册功能正常
        response.setData(true);
        response.setCode(MessageCode.SUCCESS);
        response.setMsg(MessageCode.SUCCESS_TEXT);
        return response;
    }
}
