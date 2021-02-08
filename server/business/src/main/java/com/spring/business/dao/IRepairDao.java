package com.spring.business.dao;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.RepairEntity;
import com.spring.business.dto.WebRepairDto;
import com.spring.business.vo.WebRepairQueryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报事报修Dao
 */
@Mapper
public interface IRepairDao extends BaseDao<RepairEntity> {

    /**
     * web查询报事报修列表
     * @return
     * @throws Exception
     * @author 作者：熊锋
     * @version 创建时间：2021-01-05 14:57:47
     */
    List<WebRepairDto> queryWebRepairList(WebRepairQueryVo webRepairQueryVo);
	
}
