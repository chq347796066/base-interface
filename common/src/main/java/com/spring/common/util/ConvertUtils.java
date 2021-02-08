package com.spring.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Object Convert Utils
 *
 * @author WuJiaQuan
 */
public class ConvertUtils {

    /**
     * Convert Object
     *
     * @param source
     * @param clazz
     * @return
     * @throws Exception
     * @author WuJiaQuan
     */
    public static <T, S> T copyProperties(S source, Class<T> clazz) {
        if (Objects.isNull(source)) {
            return null;
        }
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(source, t);
        return t;
    }

    /**
     * Convert ArrayList
     *
     * @param sourceList
     * @param clazz
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/9 15:36
     */
    public static <T, V> List<V> copyPropertiesList(List<T> sourceList, Class<V> clazz) {
        List<V> resultList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sourceList)) {
            return resultList;
        }
        for (T t : sourceList) {
            resultList.add(copyProperties(t, clazz));
        }
        return resultList;
    }
}
