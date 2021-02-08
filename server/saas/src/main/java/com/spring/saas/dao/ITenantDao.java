package com.spring.saas.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.OrderEntity;
import com.spring.base.entity.saas.TenantEntity;
import com.spring.base.vo.saas.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 17:10:11
 * @Desc类说明: 租户信息Dao
 */
@Mapper
public interface ITenantDao extends BaseDao<TenantEntity> {

	/**
	 * @Author 熊锋
	 * @Description: 查询租户列表
	 * @Date 2020/7/1 18:45
	 * @param vo
	 * @return java.util.List<com.spring.base.entity.saas.TenantEntity>
	 */
	List<TenantEntity> queryTenantList(TenantQueryVo vo) throws Exception;

	/**
	 * @Author 熊锋
	 * @Description: 根据id查询租户详细信息
	 * @Date 2020/7/1 18:45
	 * @Param [vo]
	 * @return java.util.List<com.spring.base.entity.saas.TenantEntity>
	 */
	TenantResponseVo queryTenantDetail(String id) throws Exception;

	/**
	 * @description:租户登录
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/7/3 15:53
	 */
	LoginTenantVo getUserByUserIdPassword(@Param("userId") String userId, @Param("password") String password) throws Exception;

	/**
	 * @author 熊锋
	 * @param mobile
	 * @date 2020/7/6 17:40
	 * @description: 查询个人账户信息
	 * @return com.spring.base.vo.saas.MyAccountVo
	 * @throws Exception
	 */
	MyAccountVo queryMyAccount(String mobile) throws Exception;

	/**
	 * 更新租户购买的应用
	 * @author 熊锋
	 * @param mobile
	 * @param appId
	 * @date 2020/7/10 17:18
	 * @return int
	 * @throws Exception
	 */
	int updateTenantApp(@Param("mobile") String mobile,@Param("appId") String appId) throws Exception;

	/**
	 * 查询本月续费租户数
	 *
	 * @param
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/20 14:21
	 */
	Long queryTenantRenewalNumByThisMonth() throws Exception;

	/**
	 * 查询上月续费租户数
	 *
	 * @param
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/20 14:21
	 */
	Long queryTenantRenewalNumByLastMonth() throws Exception;

	/**
	 * 查询本月新增租户数
	 *
	 * @param
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/20 17:15
	 */
	Long queryNewTenantNumByThisMonth() throws Exception;

	/**
	 * 查询上月新增租户数
	 *
	 * @param
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/20 17:16
	 */
	Long queryNewTenantNumByLastMonth() throws Exception;

	/**
	 * 查询本月到期租户数
	 *
	 * @param
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/20 17:17
	 */
	Long queryTenantsDueNumByThisMonth() throws Exception;

	/**
	 * 查询上月到期租户数
	 *
	 * @param
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/20 17:17
	 */
	Long queryTenantsDueNumByLastMonth() throws Exception;

	/**
	 * 统计有效租户
	 *
	 * @param
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/21 11:00
	 */
	Long queryEffectiveTenantCount() throws Exception;
}
