package com.spring.saas.service;

import com.spring.base.entity.saas.HomeBannerEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.saas.HomeBannerAddVo;
import com.spring.common.response.ApiResponseResult;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-06-30 15:55:33
 * @Desc类说明: 首页轮播信息业务接口类
 */
public interface IHomeBannerService extends IBaseService<HomeBannerEntity,String> {
	
	/**
	 * 新增首页轮播信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-06-30 15:55:33
	 */
	 ApiResponseResult addHomeBanner(HomeBannerAddVo vo) throws Exception;

	/**
	 * 删除首页Banner图
	 * @author 熊锋
	 * @param id
	 * @date 2020/7/8 16:29
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	 ApiResponseResult deleteBanner(String id) throws Exception;

	 /**
	  * 上下移动Banner图
	  * @author 熊锋
	  * @param sourceId
	  * @param targetId
	  * @date 2020/7/8 16:29
	  * @return com.spring.common.response.ApiResponseResult
	  * @throws Exception
	  */
	 ApiResponseResult moveBanner(String sourceId, String targetId) throws Exception;
}
