package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.BuildEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  楼栋Dao
 */
@Mapper
public interface IBuildDao extends BaseDao<BuildEntity> {

 /**
  * 根据条件分页查询
  * @param requestPageVO
  * @return
  */
 List<BuildEntity> queryBuildEntityPage(BuildEntity requestPageVO) throws Exception;

 /**
  * @Desc:    删除楼栋
  * @param buildEntity
  * @Author:邓磊
  * @UpdateDate:2020/4/18 11:14
  * @return: 返回
  */
 int  deleteBuild(BuildEntity buildEntity);

 /**
  * @Desc:  查看楼栋详情
  * @param buildEntity
  * @Author:邓磊
  * @UpdateDate:2020/4/26 20:41
  * @return: 返回
  */
  List<BuildEntity> queryBuildVoList(BuildEntity buildEntity);

  /**
   * @Desc:楼栋批量新增
   * @param list
   * @Author:邓磊
   * @UpdateDate:2020/5/18 17:06
   * @return: 返回
   */
  @Override
  Integer addList(@Param("list") List<BuildEntity> list);

  /**
   * 校验楼栋名称唯一性
   */
  List<BuildEntity> queryBuildName(BuildEntity buildEntity);

  BuildEntity queryBuildInfo(BuildEntity vo);

  List<BuildEntity> queryBuildList(BuildEntity buildEntity);
}
