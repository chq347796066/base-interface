package com.spring.saas.service;


import com.spring.base.entity.saas.TenantEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.user.LoginVo;
import com.spring.base.vo.saas.TenantAddVo;
import com.spring.base.vo.saas.TenantAuditVo;
import com.spring.base.vo.saas.TenantQueryVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 17:10:11
 * @Desc类说明: 租户信息业务接口类
 */
public interface ITenantService extends IBaseService<TenantEntity,String> {
	
	/**
	 * 新增租户信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-01 17:10:11
	 */
	 ApiResponseResult registerTenant(TenantAddVo vo) throws Exception;

	 /**
	  * @Author 熊锋
	  * @Description: 查询租户列表(不分页)
	  * @Date 2020/7/1 18:02
	  * @Param [vo]
	  * @return com.spring.common.response.ApiResponseResult
	  */
	 ApiResponseResult queryTenantList(TenantQueryVo vo) throws Exception;

	 /**
	  * @Author 熊锋
	  * @Description: 查询租户列表(分页)
	  * @Date 2020/7/1 18:02
	  * @Param [vo]
	  * @return com.spring.common.response.ApiResponseResult
	  */
	 ApiResponseResult queryTenantList(RequestPageVO<TenantQueryVo> requestPageVO) throws Exception;

	 /**
	  * @Author 熊锋
	  * @Description: 根据id查询租户详细信息
	  * @Date 2020/7/1 18:42
	  * @Param [id]
	  * @return com.spring.common.response.ApiResponseResult
	  */
	 ApiResponseResult queryTenantDetail(String id) throws Exception;

    /**
     * @description:检测手机号码是否则注册
     * @return:
     * @author: 赵进华
     * @time: 2020/7/3 11:22
     */
	ApiResponseResult checkMobileExist(String mobile);

    /**
     * @description:租户登录
     * @return:
     * @author: 赵进华
     * @time: 2020/7/3 15:44
     */
    ApiResponseResult tenantLogin(LoginVo loginVo) throws Exception;

	/**
	 * @description:租户单独登录
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/7/3 15:44
	 */
	ApiResponseResult tenantLoginAlone(LoginVo loginVo) throws Exception;

    /**
     * @description:审核租户
     * @return:
     * @author: 赵进华
     * @time: 2020/7/3 18:00
     */
    ApiResponseResult auditTenant(TenantAuditVo vo) throws Exception;

    /**
	 * @author 熊锋
	 * @date 2020/7/6 17:31
	 * @param mobile
	 * @description: 查询个人账户信息
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
    ApiResponseResult queryMyApplication(String mobile) throws Exception;

	/**
	 * @author 熊锋
	 * @date 2020/7/6 17:31
	 * @param mobile
	 * @description: 查询基本资料
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	ApiResponseResult queryBasicData(String mobile) throws Exception;

	/**
	 * 更新租户状态
	 *
	 * @param tenantId
	 * @param tenantStatus
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/13 19:27
	 */
	ApiResponseResult updateTenantStatus(String tenantId, Integer tenantStatus) throws Exception;

	/**
	 * 查询租户主应用信息
	 * @author 熊锋
	 * @param mobile
	 * @date 2020/7/17 14:19
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	ApiResponseResult queryMainApplication(String mobile) throws Exception;
}
