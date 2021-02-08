package com.spring.common.aop;

import com.alibaba.fastjson.JSONObject;
import com.spring.common.constants.Constants;
import com.spring.common.exception.ServiceException;
import com.spring.common.request.RequestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:service层拦截器
*/
//@Component
//@Aspect
public class ServiceAspect {
	private final static Logger logger = LoggerFactory.getLogger(ServiceAspect.class);
	/**
	 * 切面
	 */
	private final String POINT_CUT = "execution(* com.spring.*.service.*.*.*(..))";

	@Pointcut(POINT_CUT)
	private void pointcut() {

	}

	@Before(value = POINT_CUT)
	public void before(JoinPoint joinPoint) {
		try {
			// 获取HttpServletRequest
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			ServletRequestAttributes sra = (ServletRequestAttributes) ra;
			HttpServletRequest request = sra.getRequest();
			// 用户id
			String userId = "";
			if (request != null) {
				userId=RequestUtils.getUserId();
			}
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			StringBuilder log = new StringBuilder();
			log.append("=====前置通知开始======请求的类名: ").append(className).append("；请求的方法名称：").append(methodName)
					.append("；传递的参数: ");
			Object[] args = joinPoint.getArgs();
			// 新增设置创建人创建时间
			if ("add".equals(methodName)) {
				Object argument = args[0];
				BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
				// 设置创建人
				if (beanWrapper.isWritableProperty(Constants.CREATE_BY)) {
					beanWrapper.setPropertyValue(Constants.CREATE_BY, userId);
				}
				// 设置创建时间
				if (beanWrapper.isWritableProperty(Constants.CREATE_DATE)) {
					beanWrapper.setPropertyValue(Constants.CREATE_DATE, new Date());
				}
				if (beanWrapper.isWritableProperty(Constants.UPDATE_BY)) {
					beanWrapper.setPropertyValue(Constants.UPDATE_BY, userId);
				}
				// 设置修改时间
				if (beanWrapper.isWritableProperty(Constants.UPDATE_DATE)) {
					beanWrapper.setPropertyValue(Constants.UPDATE_DATE, new Date());
				}
			}
			
			// 更新设置修改人，修改时间
			if ("update".equals(methodName)) {
				Object argument = args[0];
				BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
				// 设置修改人
				if (beanWrapper.isWritableProperty(Constants.UPDATE_BY)) {
					beanWrapper.setPropertyValue(Constants.UPDATE_BY, userId);
				}
				// 设置修改时间
				if (beanWrapper.isWritableProperty(Constants.UPDATE_DATE)) {
					beanWrapper.setPropertyValue(Constants.UPDATE_DATE, new Date());
				}
			}
			for (Object arg : args) {
				log.append(JSONObject.toJSONString(arg) + ", ");
			}
			logger.info(log.toString());
		} catch (Exception e) {
			logger.error("==前置通知异常==");
			logger.error("异常信息:{}", e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterReturning(value = "pointcut()", returning = "returnObj")
	public void afterReturn(Object returnObj) {
		String result = JSONObject.toJSONString(returnObj);
		logger.info("接口调用结束返回结果: " + result);
	}

	@AfterThrowing(value = POINT_CUT, throwing = "e")
	public void afterThrowing(Throwable e) {
		logger.error("afterThrowing: " + e.getMessage(), e);
		if (e instanceof ServiceException) {
			ServiceException se = (ServiceException) e;
			try {
				throw se;
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Around(value = "pointcut()")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Long begin = System.currentTimeMillis();
		StringBuilder log = new StringBuilder("around: ");
		Object result = null;
		try {
			result = proceedingJoinPoint.proceed();
		} catch (Exception e) {
			result = e;
			logger.error(log + e.getMessage(), e);
			if (e instanceof ServiceException) {
				ServiceException se = (ServiceException) e;
				throw se;
			}
		}
		Long end = System.currentTimeMillis();
		log.append(" 执行时间: ").append(end - begin).append("ms");
		return result;
	}
}
