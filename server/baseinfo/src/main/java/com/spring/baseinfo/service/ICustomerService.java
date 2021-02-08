package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.CustomerEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.customer.CustomerAddVo;
import com.spring.base.vo.baseinfo.customer.CustomerUpdateVo;
import com.spring.base.vo.baseinfo.customer.CustomerVo;
import com.spring.base.vo.baseinfo.housingcertification.CertificationHouseAddVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 17:35:27
 * @Desc类说明: 客户信息业务接口类
 */
public interface ICustomerService extends IBaseService<CustomerEntity,String>{
	
	/**
	 * 新增客户信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-03 17:35:27
	 */
    ApiResponseResult addCustomer(CustomerAddVo vo) throws Exception;
	
	/**
	 * 更客户信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-03 17:35:27
	 */
    ApiResponseResult updateCustomer(CustomerUpdateVo vo) throws Exception;


	/**
	 * 关联删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult deleteCarHouse(String id) throws Exception;


	/**
	 * 查看业主信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult viewCustomInfo(String id) throws Exception;



    /**
     * 根据条件分页查询,业主APP用户
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    ApiResponseResult queryCustormAppUserPage(RequestPageVO<CustomerEntity> requestPageVO) throws Exception;


   /**
    * @Desc:    java类作用描述
    * @param users
    * @Author:邓磊
    * @UpdateDate:2020/4/21 17:31
    * @return: 返回
    */
   ApiResponseResult impUser(List<CustomerVo> users, String[] tenantCompanyArray, String communityId);


	/**
	 * @Desc: 客户信息下载模板
	 * @param communityId
	 * @Author:邓磊
	 * @UpdateDate:2020/4/24 16:18
	 */
    void expUserDemo(HttpServletResponse response, String communityId) throws Exception;



	/**
	 * @Desc: 客户信息导出数据
	 * @param customerEntity
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 14:07
	 * @return: 返回
	 */
	 void  exportCustomerEntityInfo(CustomerEntity customerEntity) throws Exception;

	/**
	 * 根据条件分页查询客户
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryCustomerPage(RequestPageVO<CustomerEntity> requestPageVO) throws Exception;

    ApiResponseResult addCustomerHouseInfo(CertificationHouseAddVo vo) throws Exception;
}
