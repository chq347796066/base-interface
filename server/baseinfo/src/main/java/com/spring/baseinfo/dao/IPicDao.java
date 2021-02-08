package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.PicEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-14 15:31:38
 * @Desc类说明: 图片中间Dao
 */
@Mapper
public interface IPicDao extends BaseDao<PicEntity> {

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
	
}
