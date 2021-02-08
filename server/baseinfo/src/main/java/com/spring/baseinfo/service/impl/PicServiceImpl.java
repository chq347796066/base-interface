package com.spring.baseinfo.service.impl;

import com.spring.base.entity.baseinfo.PicEntity;
import com.spring.base.vo.baseinfo.pic.PicAddVo;
import com.spring.base.vo.baseinfo.pic.PicUpdateVo;
import com.spring.common.request.RequestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.baseinfo.dao.IPicDao;
import com.spring.baseinfo.service.IPicService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;

import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-14 15:31:38
 * @Desc类说明: 图片中间业务接口实现类
 */

@Service("picService")
public class PicServiceImpl extends BaseServiceImpl<PicEntity, String> implements IPicService {
	
	@Autowired
	private IPicDao picDao;

	@Override
	public BaseDao getBaseMapper() {
		return picDao;
	}
	
	/**
	 * 新增图片中间
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-14 15:31:38
	 */
	@Override
	public ApiResponseResult addPic(PicAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		PicEntity entity = new PicEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = picDao.insert(entity);
		if (no > 0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	/**
	 * 更新图片中间
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-14 15:31:38
	 */
	@Override
	public ApiResponseResult updatePic(PicUpdateVo vo) throws Exception {
		PicEntity entity = new PicEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = picDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	/**
	 * 查询列表
	 * @param picEntity
	 * @return
	 */
	@Override
	public List<PicEntity> queryPicEntityList(PicEntity picEntity) {
		return picDao.queryPicEntityList(picEntity);
	}

	/**
	 * 删除
	 * @param picEntity
	 * @return
	 */
	@Override
	public int updatePicDelFlag(PicEntity picEntity) {
		return picDao.updatePicDelFlag(picEntity);
	}


	/**
	 * 批量新增
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult addPicList(List<PicEntity> vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		int no = 0;
		if(CollectionUtils.isNotEmpty(vo)){
			vo.stream().forEach(picEntity -> {
				picEntity.setDelFlag(0);
			});
			no = picDao.addList(vo);
		}
		if (no > 0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	/**
	 * 删除
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult deletePicDelFlag(PicEntity vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		int no = picDao.updatePicDelFlag(vo);
		if (no > 0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	/**
	 * 查看图片List
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryPicEntityVoList(PicEntity vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		List<PicEntity> picEntities = picDao.queryPicEntityList(vo);
		result.setData(picEntities);
		result.setCode(MessageCode.SUCCESS);
		result.setMsg("成功");
		return result;
	}

}
