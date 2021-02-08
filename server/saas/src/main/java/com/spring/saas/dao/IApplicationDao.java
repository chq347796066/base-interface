package com.spring.saas.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.ApplicationEntity;
import com.spring.base.vo.saas.ApplicationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 14:52:37
 * @Desc类说明: 应用Dao
 */
@Mapper
public interface IApplicationDao extends BaseDao<ApplicationEntity> {

   /**
    * @author 熊锋
    * @param ids
    * @date 2020/7/6 18:16
    * @description: 根据应用id查询数据
    * @return java.util.List<com.spring.base.vo.saas.ApplicationVo>
    * @throws Exception
    */
   List<ApplicationVo> queryApplication(@Param("ids") List<String> ids);

   /**
    * 我的应用
    * @author 熊锋
    * @param mobile
    * @date 2020/7/6 18:16
    * @return java.util.List<com.spring.base.vo.saas.ApplicationVo>
    * @throws Exception
    */
   List<ApplicationVo> queryMyApplication(String mobile);

   /**
    * 我的应用(主应用)
    * @author 熊锋
    * @param mobile
    * @date 2020/7/6 18:16
    * @return java.util.List<com.spring.base.vo.saas.ApplicationVo>
    * @throws Exception
    */
   //MyAccountVo queryMainApplication(String mobile);
}
