package com.spring.maintenance.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.BuildEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.IBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dell
 */
@Service("buildService ")
public class BuildServiceImpl extends BaseServiceImpl<BuildEntity, String> implements IBuildService {
    @Autowired
    private BaseInfoFeignClient communityBuildCellHouseFeignCilnet;

    @Override
    public BaseDao getBaseMapper() {
        return null;
    }

    /**
     * 楼栋列表
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryBuildList(BuildEntity vo) throws Exception {
       return communityBuildCellHouseFeignCilnet.queryList(vo);
    }
}
