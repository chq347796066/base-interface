package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.company.AddSaasVo;
import com.spring.base.vo.baseinfo.company.CompanyAddVo;
import com.spring.base.vo.baseinfo.company.CompanyUpdateVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  公司业务接口类
 */
public interface ICompanyService extends IBaseService<CompanyEntity,String>{

	CompanyEntity getCompanyInfo(String id);
	
	/**
	 * 新增 公司
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult addCompany(CompanyAddVo vo) throws Exception;
	
	/**
	 * 更公司
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult updateCompany(CompanyUpdateVo vo) throws Exception;


	/**
	 * 查看对象数据
	 * @param vo
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryCompanyEntity(CompanyUpdateVo vo) throws Exception;

	/**
	 * @Desc:    java类作用描述
	 * @param requestPageVO
	 * @Author:邓磊
	 * @UpdateDate:2020/5/14 11:16
	 * @return: 返回
	 */
	 void  exportCompanyEntityInfo(CompanyEntity requestPageVO) throws Exception;


	public List<String> queryCompanyChidrenList(String companyId);

	/**
	 * @description:查询公司树形结构
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/6/4 17:26
	 */
    //ApiResponseResult queryCompanyTree() throws Exception;

	/**
	 * 根据条件分页查询公司
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
	ApiResponseResult queryPageCompany(RequestPageVO<CompanyEntity> requestPageVO) throws Exception;

	ApiResponseResult queryCompanyTree() throws Exception;


	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryCompanyPage(RequestPageVO<CompanyEntity> requestPageVO) throws Exception;

	/**
	 * @description:新增saas租户
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/7/3 18:28
	 */
    ApiResponseResult addTenant(AddSaasVo vo) throws Exception;
}
