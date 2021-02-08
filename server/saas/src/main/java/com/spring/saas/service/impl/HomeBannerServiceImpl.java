package com.spring.saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.saas.HomeBannerEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.saas.HomeBannerAddVo;
import com.spring.common.constants.MessageCode;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import com.spring.saas.dao.IHomeBannerDao;
import com.spring.saas.service.IHomeBannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-06-30 15:55:33
 * @Desc类说明: 首页轮播信息业务接口实现类
 */

 @Slf4j
@Service("homeBannerService")
public class HomeBannerServiceImpl extends BaseServiceImpl<HomeBannerEntity, String> implements IHomeBannerService {
	
	@Autowired
	private IHomeBannerDao homeBannerDao;

	@Override
	public BaseDao getBaseMapper() {
		return homeBannerDao;
	}
	
	/**
	 * 新增首页轮播信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-06-30 15:55:33
	 */
	@Override
	public ApiResponseResult addHomeBanner(HomeBannerAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		HomeBannerEntity entity = new HomeBannerEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = homeBannerDao.insert(entity);
		if (no > 0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("上传成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("上传失败");
		}
		return result;
	}

	/**
	 * 更新首页轮播信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-06-30 15:55:33
	 */
	@Override
	public ApiResponseResult deleteBanner(String id) throws Exception {
		// 删除
		HomeBannerEntity homeBannerEntity=new HomeBannerEntity();
		homeBannerEntity.setDelFlag(1);
		homeBannerEntity.setId(id);
		int no = homeBannerDao.updateById(homeBannerEntity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	/**
	 * @Author 熊锋
	 * @Description: 上下移动Banner图
	 * @Date 2020/7/1 11:29
	 * @Param [sourceId, targetId]
	 * @return com.spring.common.response.ApiResponseResult
	 */
	 @Override
	 public ApiResponseResult moveBanner(String sourceId, String targetId) throws Exception {
		 QueryWrapper<HomeBannerEntity> queryWrapper = new QueryWrapper<>();
		 QueryWrapper<HomeBannerEntity> queryWrapper1 = new QueryWrapper<>();
		 queryWrapper.eq("id",sourceId).eq("del_flag",0);
		 queryWrapper1.eq("id",targetId).eq("del_flag",0);
		 HomeBannerEntity sourceBanner=homeBannerDao.selectOne(queryWrapper);
		 HomeBannerEntity targetBanner=homeBannerDao.selectOne(queryWrapper1);
		 Integer sourceSeqNum=sourceBanner.getSeqNum();
		 sourceBanner.setSeqNum(targetBanner.getSeqNum());
		 targetBanner.setSeqNum(sourceSeqNum);
		 Integer sourceNo=homeBannerDao.updateById(sourceBanner);
		 Integer targetNo=homeBannerDao.updateById(targetBanner);
		 if(sourceNo>0 && targetNo>0){
			 return createSuccessResult(null);
		 }
		 return createFailResult();
	 }
 }
