package com.spring.meter.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.meter.MMeterTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 仪类型Dao
 */
@Mapper
public interface IMMeterTypeDao extends BaseDao<MMeterTypeEntity> {


    int batchDeleteById(@Param("ids")List<Long> ids) throws Exception;

    List<MMeterTypeEntity> queryMeterTypePage(MMeterTypeEntity entity) throws Exception;
}
