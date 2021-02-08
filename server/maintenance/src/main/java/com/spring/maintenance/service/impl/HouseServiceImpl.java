package com.spring.maintenance.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dell
 */
@Service("houseService")
public class HouseServiceImpl extends BaseServiceImpl<HouseEntity, String> implements IHouseService {
    @Autowired
    private BaseInfoFeignClient communityBuildCellHouseFeignCilnet;

    @Override
    public BaseDao getBaseMapper() {
        return null;
    }

    /**
     * 查看房屋列表
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryHouseList(HouseEntity vo) throws Exception {
        return communityBuildCellHouseFeignCilnet.queryList(vo);
    }

    /**
     * @Desc:    房屋审核确认房屋信息业主
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/5/13 10:34
     * @return: 返回
     */
    @Override
    public ApiResponseResult queryNameMobile(HouseEntity vo) throws Exception {
        return communityBuildCellHouseFeignCilnet.queryOwnerNameMobile(vo);
    }
}
