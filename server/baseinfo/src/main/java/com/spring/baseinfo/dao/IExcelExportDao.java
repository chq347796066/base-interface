package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.ExcelExportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2019-07-01 17:14:43
 * @Desc类说明: Dao
 */
@Mapper
public interface IExcelExportDao extends BaseDao<ExcelExportEntity> {

    /**
     * @description:查询excel文件导出列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/29 10:15
     */
    List<ExcelExportEntity> queryExcelFile(@Param("userId") String userId, @Param("excelType") String excelType);
}
