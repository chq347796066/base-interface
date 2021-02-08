package com.spring.common.aop;

import com.spring.common.constants.Constants;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:集团公司小区分页数据权限
 * @author: 赵进华
 * @time: 2020/4/8 10:11
 */
@Slf4j
@Aspect
@Component
public class CommunityPagePower {

    //切入点
    @Pointcut("@annotation(com.spring.common.annotation.CommunityPagePower)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //拦截的方法参数，包含值
        Object[] args = joinPoint.getArgs();
        //拦截的放参数类型
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        try {
            for (int i = 0; i < parameterTypes.length; i++) {
                Class clazz = parameterTypes[i];
                String clazzName = clazz.getName();
                log.info("DataPower clazzName:" + clazzName);
                //处理分页实体类
                if ("com.spring.common.page.RequestPageVO".equals(clazzName)) {
                    args[i] = handlePage(args[i]);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //处理完之后放行
        args = joinPoint.getArgs();
        return joinPoint.proceed(args);
    }

    private Object handlePage(Object arg) throws Exception {
        Map<String, Object> ham = objToMap(arg);
        Class clazz = ham.get("entity").getClass();
        Object entity = handleEntity(clazz, ham.get("entity"));
        RequestPageVO<Object> vo = new RequestPageVO();
        vo.setTotal((Integer) ham.get("total"));
        vo.setPageSize((Integer) ham.get("pageSize"));
        vo.setCurrentPage((Integer) ham.get("currentPage"));
        vo.setEntity(entity);
        return vo;
    }

    private Map<String, Object> objToMap(Object obj) throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    private Object handleEntity(Class clazz, Object args) throws Exception {
        String clazzName = clazz.getName();
        Map<String, Object> map = objToMap(args);
        Object newClazz = clazz.newInstance();
        //将数据进行赋值
        copyData(clazz, newClazz, map);
        //超级管理员可以看所有数据
        if(!Constants.SYSADMIN.equals(RequestUtils.getUserId())) {
            if (!StringUtils.isEmpty(RequestUtils.getTenantId())) {
                setField("tenantId", clazz, newClazz, RequestUtils.getTenantId());
                log.info("CommunityDataPower,tenantId:"+RequestUtils.getTenantId());
            }
//            if (!StringUtils.isEmpty(RequestUtils.getCompanyId())) {
//                setField("companyId", clazz, newClazz, RequestUtils.getCompanyId());
//                log.info("CommunityDataPower,companyId:"+RequestUtils.getCompanyId());
//            }
//            if (!StringUtils.isEmpty(RequestUtils.getCommunityId())) {
//                setField("communityId", clazz, newClazz, RequestUtils.getCommunityId());
//                log.info("CommunityDataPower,communityId:"+RequestUtils.getCommunityId());
//            }
        }
        return newClazz;
    }

    /**
     * @return
     * @Author 赵进华
     * @Description 设置属性值
     * @Date 2019/6/26 15:08
     * @Param
     **/
    private void setField(String fieldName, Class clazz, Object newClazz, Object value) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
        Method method = pd.getWriteMethod();
        if (method != null) {
            method.invoke(newClazz, value);
        }
    }

    /**
     * @return
     * @Author 赵进华
     * @Description 将请求的参数的值赋值给新的对象
     * @Date 2019/6/26 15:09
     * @Param
     **/
    private void copyData(Class clazz, Object object, Map<String, Object> value) throws Exception {
        Field[] field = clazz.getDeclaredFields();
        for (Field f : field) {
            f.setAccessible(true);
            String fieldName = f.getName();
            if (!"serialVersionUID".equals(fieldName)) {
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
                Method method = pd.getWriteMethod();
                if (method != null && value.get(fieldName) != null) {
                    method.invoke(object, value.get(fieldName));
                }
            }
        }
    }
}
