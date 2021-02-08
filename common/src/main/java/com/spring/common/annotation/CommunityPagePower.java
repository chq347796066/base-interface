package com.spring.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:集团公司小区分页数据权限注解
 * @return:
 * @author: 赵进华
 * @time: 2020/4/24 17:29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommunityPagePower {
}
