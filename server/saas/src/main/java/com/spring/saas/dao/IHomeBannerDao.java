package com.spring.saas.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.HomeBannerEntity;
import org.apache.ibatis.annotations.Mapper;



/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-06-30 15:55:33
 * @Desc类说明: 首页轮播信息Dao
 */
@Mapper
public interface IHomeBannerDao extends BaseDao<HomeBannerEntity> {

}
