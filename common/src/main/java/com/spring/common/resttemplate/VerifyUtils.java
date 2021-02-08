package com.spring.common.resttemplate;

import com.spring.common.util.aes.AesEncryptUtil;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:RestTemplate微服务调用鉴权工具类
 * @author: 赵进华
 * @time: 2020/3/5 16:09
 */

public class VerifyUtils {

    /**
     * @description:创建http请求head
     * @Par: 参数：字符串，post请求为对象的json,get请求为形参顺序的kv值，中间用|隔开，例:userCode=001|userName=张三
     * @return:
     * @author: 赵进华
     * @time: 2020/3/5 16:18
     */
    public static HttpHeaders createHttpHeads(String parString) throws Exception {
        // 当前时间戳
        Long ts = System.currentTimeMillis();
        //参数拼接时间戳
        String endString = parString.trim() + "$" + ts.toString().trim();
        //AES对称加密
        String encryptString = AesEncryptUtil.encrypt(endString.trim());
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("oath", encryptString);
        return requestHeaders;
    }

    /**
     * @description:判断http请求是否合法
     * @Par: 参数：字符串，post请求为对象的json,get请求为形参顺序的kv值，中间用|隔开，例:userCode=001|userName=张三
     * @return:
     * @author: 赵进华
     * @time: 2020/3/5 16:49
     */
    public static boolean checkRequest(HttpServletRequest request,String dataString) throws Exception {
        boolean returnBool = true;
        String parString=request.getHeader("oath");
        //AES对称解密
        String desEncryptString = AesEncryptUtil.desEncrypt(parString);
        String[] arry=desEncryptString.split("\\$");
        if(arry!=null && arry.length==2)
        {
            String timeString= arry[1].trim();
            //发送的时间戳
            Long sendTs=Long.parseLong(timeString);
            //判断发送的时间戳和现在时间戳，是否在5分钟内
            Long s = (System.currentTimeMillis() - sendTs) / (1000 * 60);
            if(s>5)
            {
                returnBool = false;
                return returnBool;
            }
            //判断参数是否和接收的相同
            String sendPar=arry[0];
            if(!dataString.equals(sendPar))
            {
                returnBool = false;
            }
        }
        return returnBool;
    }
}
