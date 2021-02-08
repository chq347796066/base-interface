package com.spring.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.spring.common.request.RequestUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充时间字段
 * @author zwb
 */
@Component
public class MateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createDate", new Date(), metaObject);
        setFieldValByName("modifyDate", new Date(), metaObject);
        setFieldValByName("modifyUser", RequestUtils.getUserName(), metaObject);
        setFieldValByName("createUser", RequestUtils.getUserName(), metaObject);
        setFieldValByName("delFlag", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("modifyDate", new Date(), metaObject);
        setFieldValByName("modifyUser", RequestUtils.getUserName(), metaObject);
    }
}
