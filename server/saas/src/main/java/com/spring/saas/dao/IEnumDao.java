package com.spring.saas.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.EnumEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 枚举Dao
 *
 * @author WuJiaQuan
 */
@Mapper
public interface IEnumDao extends BaseDao<EnumEntity> {
}
