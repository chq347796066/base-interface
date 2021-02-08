package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.CustomerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 17:35:27
 * @Desc类说明: 客户信息Dao
 */
@Mapper
public interface ICustomerDao extends BaseDao<CustomerEntity> {
  /**
   * 查询对象
   * @return
   * @param entity
   */
  CustomerEntity queryCustomerInfo(CustomerEntity entity);


  List<CustomerEntity> queryCustormAppUserPage(CustomerEntity entity);

  /**
   * @Desc:  删除客户
   * @param entity
   * @Author:邓磊
   * @UpdateDate:2020/4/18 14:49
   * @return: 返回
   */
    int deleteCustomer(CustomerEntity entity);


  /**
   * @Desc:  批量插入
   * @param list
   * @Author:邓磊
   * @UpdateDate:2020/4/21 15:16
   * @return: 返回
   */
  @Override
  Integer addList(@Param("list") List<CustomerEntity> list);

  /**
   * @Desc:    查看客户信息
   * @param customerEntity
   * @Author:邓磊
   * @UpdateDate:2020/4/21 17:57
   * @return: 返回
   */
  CustomerEntity queryCustomerVo(CustomerEntity customerEntity);
 }
