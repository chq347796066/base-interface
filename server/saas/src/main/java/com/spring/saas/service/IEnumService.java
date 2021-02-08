package com.spring.saas.service;

import com.spring.base.entity.saas.EnumEntity;
import com.spring.common.response.ApiResponseResult;

import java.util.List;

/**
 * 枚举服务
 *
 * @author WuJiaQuan
 */
public interface IEnumService {

    /**
     * 按枚举类型名称查询枚举列表
     *
     * @param enumType
     * @return
     * @throws Exception
     * @author WuJiaQuan
     * @date 2020/7/14 10:53
     */
    ApiResponseResult queryEnumListByEnumType(String enumType) throws Exception;
}
