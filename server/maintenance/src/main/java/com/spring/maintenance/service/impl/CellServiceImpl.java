package com.spring.maintenance.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.CellEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.ICellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dell
 */
@Service("cellService")
public class CellServiceImpl  extends BaseServiceImpl<CellEntity, String> implements ICellService {

    @Autowired
    private BaseInfoFeignClient communityBuildCellHouseFeignCilnet;
    @Override
    public BaseDao getBaseMapper() {
        return null;
    }

    /**
     * 单元列表查询
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryBuildList(CellEntity vo) throws Exception {
        return communityBuildCellHouseFeignCilnet.queryList(vo);
    }
}
