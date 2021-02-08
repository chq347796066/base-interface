package com.spring.baseinfo.dao;

import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 小区信息Dao
 */
@Mapper
public interface ICommunityDao extends BaseDao<CommunityEntity> {

    /**
     * 删除
     */
    int deleteId(String id);

    String queryCommunityName(Map<String, Object> paraMap);

    /**
     * @param communityEntity
     * @Desc: java类作用描述
     * @Author:邓磊
     * @UpdateDate:2020/4/21 11:46
     * @return: 返回
     */
    CommunityEntity queryCommunity(CommunityEntity communityEntity);


    /**
     * @param communityEntity
     * @Desc:校验公司下小区名唯一性
     * @Author:邓磊
     * @UpdateDate:2020/6/4 10:33
     * @return: 返回
     */
    List<CommunityEntity> queryCommunityName(CommunityEntity communityEntity);

    /**
     * @description:获取租户下的小区数量
     * @return:
     * @author: 赵进华
     * @time: 2020/7/14 11:02
     */
    int getTenantCommunityNum(String tenantId);
}
