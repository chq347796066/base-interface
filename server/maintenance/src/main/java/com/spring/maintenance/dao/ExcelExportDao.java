package com.spring.maintenance.dao;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.ExcelExportEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author dell
 */
@Mapper
public interface ExcelExportDao extends BaseDao<ExcelExportEntity> {

    /**
     * 执行sql,返回记录集
     * @param sql
     * @return
     */
    List<LinkedHashMap<String, Object>> execSqlGetRecordSet(String sql) throws Exception;


    /**
     * 执行sql,返回记录数
     * @param sql
     * @return
     */
    Integer execSqlGetRecordCount(String sql) throws Exception;



}
