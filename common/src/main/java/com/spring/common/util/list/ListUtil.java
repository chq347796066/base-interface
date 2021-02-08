package com.spring.common.util.list;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author 熊锋
 * @date 2021-01-06 11:21
 * @Describe:
 */
public class ListUtil {

    /**
     * @Author 熊锋
     * @param list
     * @param clazz
     * @Description 从List<A> copy到List<B>
     * @Date 2021/1/6 11:22
     */
    public static <T> List<T> copy(List<?> list, Class<T> clazz){
        String oldOb = JSON.toJSONString(list);
        return JSON.parseArray(oldOb, clazz);
    }

    /**
     * @Author 熊锋
     * @param ob
     * @param clazz
     * @Description 从对象A copy到 对象B
     * @Date 2021/1/6 11:22
     */
    public static <T> T copy(Object ob,Class<T> clazz){
        String oldOb = JSON.toJSONString(ob);
        return JSON.parseObject(oldOb, clazz);
    }
}
