package com.spring.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:DAO基础父类
*/
public interface BaseDao<T> extends BaseMapper<T> {

    /**
     * 批量新增
     * @param list
     * @return
     * @throws Exception
     */
     Integer addList(List<T> list) throws Exception;

    /**
     * 根据条件查询列表
     * @param entity
     * @return
     */
     List<T> queryList(T entity) throws Exception;


}