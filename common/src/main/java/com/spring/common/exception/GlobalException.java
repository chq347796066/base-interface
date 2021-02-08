package com.spring.common.exception;

import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:全局异常类
*/
@ControllerAdvice
public class GlobalException {
	private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

	@ResponseBody
    @ExceptionHandler(value = { Exception.class, RuntimeException.class })
    public ApiResponseResult handleErrors(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        //获取详细日志信息
        String msg=getTrace(ex);
        log.error("系统异常",ex);
        ApiResponseResult ret = new ApiResponseResult();
        ret.setCode(MessageCode.EXCEPTION);
        ret.setMsg("系统异常");
        return ret;
    }


	/**
     * 处理验证异常
     * @param req
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ValidationException.class)
    public ApiResponseResult validationErrorHandler(HttpServletRequest req, Exception e)
    {
        log.error("验证异常",e);
    	ApiResponseResult ret = new ApiResponseResult();
        ret.setCode(MessageCode.EXCEPTION);
        ret.setMsg("验证失败");
        ret.setData(((ValidationException) e).getResultMap());
        return ret;
    }
    
    /**
     * 处理业务异常
     * @param req
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public ApiResponseResult serviceErrorHandler(HttpServletRequest req, Exception e)
    {
        log.error("处理业务异常", e);
    	ApiResponseResult ret = new ApiResponseResult();
        ret.setCode(MessageCode.FAIL);
        ret.setMsg(MessageCode.FAIL_TEXT);
        ret.setData(e.getMessage());
        return ret;
    }
    
    /**
     * 授权异常
     * @param req
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AuthException.class)
    public ApiResponseResult authErrorHandler(HttpServletRequest req, Exception e)
    {
        log.error("授权异常",e);
        ApiResponseResult ret = new ApiResponseResult();
        ret.setCode(MessageCode.AUTH_FAIL);
        ret.setMsg(MessageCode.AUTH_FAIL_TEXT);
        ret.setData(((ValidationException) e).getResultMap());
        return ret;
    }
    
    /**
     * 获取异常信息字符串
     * 
     * @param t
     * @return
     */
    public static String getTrace(Throwable t)
    {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
