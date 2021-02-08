package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.PicEntity;
import com.spring.base.vo.baseinfo.pic.PicAddVo;
import com.spring.base.vo.baseinfo.pic.PicUpdateVo;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-14 15:30:33
 * @Desc类说明: 图片中间业务接口类
 */
public interface IPicService extends IBaseService<PicEntity,String>{
	
	/**
	 * 新增图片中间
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-14 15:30:33
	 */
    ApiResponseResult addPic(PicAddVo vo) throws Exception;
	
	/**
	 * 更图片中间
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-14 15:30:33
	 */
    ApiResponseResult updatePic(PicUpdateVo vo) throws Exception;

	/**
	 * 查看列表详情
	 * @param picEntity
	 * @return
	 */
	List<PicEntity> queryPicEntityList(PicEntity picEntity);


	/**
	 * 修改状态 delfiag为1删除
	 */
    int updatePicDelFlag(PicEntity picEntity);

	/**
	 * 批量新增
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult addPicList(@RequestBody List<PicEntity> vo) throws Exception;

	/**
	 * 删除
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult deletePicDelFlag(@RequestBody PicEntity vo) throws Exception;

	/**
	 * 查看图片List
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryPicEntityVoList(@RequestBody PicEntity vo)throws Exception;



}
