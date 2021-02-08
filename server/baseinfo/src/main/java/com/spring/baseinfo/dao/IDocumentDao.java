package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.DocumentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-03 16:33:19
* @Desc类说明: 文档Dao
*/
@Mapper
public interface IDocumentDao extends BaseDao<DocumentEntity> {

}
