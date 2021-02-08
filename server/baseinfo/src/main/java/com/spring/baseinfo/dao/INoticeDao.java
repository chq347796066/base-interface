package com.spring.baseinfo.dao;
import com.spring.base.entity.baseinfo.NoticeEntity;
import com.spring.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-07 17:49:59
 * @Desc类说明: 通知公告Dao
 */
@Mapper
public interface INoticeDao extends BaseDao<NoticeEntity> {
  /**
   * @Desc:通知公告列表
   * @param noticeEntity
   * @Author:邓磊
   * @UpdateDate:2020/5/11 14:35
   * @return: 返回
   */
  @Override
  List<NoticeEntity> queryList(NoticeEntity noticeEntity);

  /**
   * @Desc:修改上下线状态
   * @param noticeEntity
   * @Author:邓磊
   * @UpdateDate:2020/5/11 14:34
   * @return: 返回
   */
  Integer updateNoticeStatus(NoticeEntity noticeEntity);
}
