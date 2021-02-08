package com.spring.maintenance.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.ICommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dell
 */
@Service("communityService")
public class CommunityServiceImpl extends BaseServiceImpl<CommunityEntity, String> implements ICommunityService {
    @Autowired
    private BaseInfoFeignClient communityBuildCellHouseFeignCilnet;

    @Override
    public BaseDao getBaseMapper() {
        return null;
    }

    /**
     * 查看小区列表
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryCommunityList(CommunityEntity vo) throws Exception {
        return communityBuildCellHouseFeignCilnet.queryList(vo);
    }
}
