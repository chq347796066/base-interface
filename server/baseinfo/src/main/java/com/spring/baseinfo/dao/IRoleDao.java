package com.spring.baseinfo.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.RoleEntity;
import com.spring.base.entity.baseinfo.UserRoleEntity;
import com.spring.base.vo.baseinfo.dictionary.DictionaryDataVo;
import com.spring.base.vo.baseinfo.role.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-18 15:37:54
 * @Desc类说明: 角色Dao
 */
@Mapper
public interface IRoleDao extends BaseDao<RoleEntity> {

    /**
     * @description:获取角色列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/19 11:16
     */
    List<DictionaryDataVo> getRoleList() throws Exception;


    /**
     * @param roleEntity
     * @Desc: 获取角色下所有用户
     * @Author:邓磊
     * @UpdateDate:2020/4/22 20:40
     * @return: 返回
     */
    List<UserRoleEntity> queryRoleUser(RoleVo roleEntity);

    /**
     * @param roleId
     * @Desc: java类作用描述
     * @Author:邓磊
     * @UpdateDate:2020/5/7 10:36
     * @return: 返回
     */
    List<RoleEntity> queryBatchRole(@Param("array") String[] roleId);

    /**
     * @description:获取saas试用版角色列表
     * @return:
     * @author: 赵进华
     * @time: 2020/7/14 11:42
     */
    List<DictionaryDataVo> getTryRoleList() throws Exception;

    /**
     * @description:获取saas正式版角色列表
     * @return:
     * @author: 赵进华
     * @time: 2020/7/14 11:42
     */
    List<DictionaryDataVo> getSaasRoleList(String tenantId) throws Exception;

    /**
     * saas根据条件查询列表
     * @param entity
     * @return
     */
    List<RoleEntity> querySaasList(RoleEntity entity) throws Exception;
}
