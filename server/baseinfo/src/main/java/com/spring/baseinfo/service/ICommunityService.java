package com.spring.baseinfo.service;

import com.spring.base.dto.CommunityDto;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.vo.baseinfo.community.CommunityAddVo;
import com.spring.base.vo.baseinfo.community.CommunityUpdateVo;
import com.spring.base.service.IBaseService;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 小区信息业务接口类
 */
public interface ICommunityService extends IBaseService<CommunityEntity,String>{



	ApiResponseResult queryCommunityEntity(String id);
	
	/**
	 * 新增小区信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult addCommunity(CommunityAddVo vo) throws Exception;
	
	/**
	 * 更小区信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult updateCommunity(CommunityUpdateVo vo) throws Exception;


	/**
	 * @Desc: 小区导出信息
	 * @param requestPageVO
	 * @Author:邓磊
	 * @UpdateDate:2020/5/14 16:57
	 * @return: 返回
	 */
    void  exportCommunityEntityInfo(CommunityEntity requestPageVO) throws Exception;

    /**
     * @description:根据条件分页查询小区
     * @return:
     * @author: 赵进华
     * @time: 2020/6/5 15:28
     */
	ApiResponseResult queryCommunityPage(RequestPageVO<CommunityEntity> requestPageVO) throws Exception;

	/**
	 * 根据条件查询列表
	 * @param
	 * @return
	 */
    ApiResponseResult queryCommunityList(CommunityEntity entity) throws Exception;

	/**
	 * 根据公司id查询项目信息
	 * @param
	 * @return
	 */
	List<CommunityDto> getCommunityInfo(String companyId) throws Exception;

	/**
	 * 根据公司id查询项目信息下拉框
	 * @param
	 * @return
	 */
	ApiResponseResult getCommunityInfoByCompanyId() throws Exception;

	/**
	 * 根据公司id查询项目信息
	 * @param
	 * @return
	 */
	List<CommunityDto> queryCommunityInfo() throws Exception;
}
