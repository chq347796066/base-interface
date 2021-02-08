package com.spring.pay.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 * DataSource 表数据库控制层接口
 *
 * @author dell
 */
@Mapper
@Component
public interface DataSourceMapper {

	/**
     * 查询数据库用构造出来的querySql
     * @param querySql
     * @return
     */
	@Select("${querySql}")
    Map<String,Object> selectOne(@Param("querySql") String querySql);
    
    /**
     * 查询数据库用构造出来的querySql
     * @param querySql
     * @return
     */
	@Select("${querySql}")
    List<Map<String,Object>> querySql(@Param("querySql") String querySql);

    /**
     * 执行更新的sql
     * @param updateSql
     */
	@Update("${updateSql}")
    void updateSql(@Param("updateSql") String updateSql);
    
    /**
     * 查询过滤后数据的总数
     * @param countSql
     * @return
     */
	@Select("${countSql}")
    int countSql(@Param("countSql") String countSql);
    
    /**
     * 执行存储过程
     * @param updateProc
     */
	@Update("${updateProc}")
    void updateProc(@Param("updateProc") String updateProc);
    
    /**
     * 执行存储过程
     * @param selectProc
     */
	@Select("${selectProc}")
    List<Map<String,Object>> selectProc(@Param("selectProc") String selectProc);
}