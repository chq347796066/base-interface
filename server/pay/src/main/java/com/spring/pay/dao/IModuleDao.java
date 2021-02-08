package com.spring.pay.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.pay.ModuleEntity;
import com.spring.base.vo.pay.module.ModuleUpdateVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-18 17:32:18
 * @Desc类说明: Dao
 */
@Mapper
public interface IModuleDao extends BaseDao<ModuleEntity> {

    /**
     * @description:查询组件列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/23 16:05
     */
    List<ModuleUpdateVo> queryModuleList(ModuleEntity entity);
}
