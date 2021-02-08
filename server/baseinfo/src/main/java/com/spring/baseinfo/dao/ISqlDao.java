package com.spring.baseinfo.dao;


import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.entity.baseinfo.ExcelExportEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2019-06-15 11:30:14
 * @Desc类说明: sql Dao
 */
@Mapper
public interface ISqlDao extends BaseDao<ExcelExportEntity> {


    /**
     * 执行sql,返回记录集
     *
     * @param sql
     * @return
     * @author 作者：赵进华
     * @version 创建时间：2019年6月5日 下午4:00:53
     */
    List<LinkedHashMap<String, Object>> execSqlGetRecordSet(String sql) throws Exception;


    /**
     * 执行sql,返回记录数
     *
     * @param sql
     * @return
     * @throws Exception
     * @author 作者：赵进华
     * @version 创建时间：2019年6月19日 下午4:58:58
     */
    Integer execSqlGetRecordCount(String sql) throws Exception;

    /**
     * @description:批量保存项目
     * @return:
     * @author: 赵进华
     * @time: 2020/4/2 18:05
     */
    Integer addListProject(List<CompanyEntity> list) throws Exception;

}
