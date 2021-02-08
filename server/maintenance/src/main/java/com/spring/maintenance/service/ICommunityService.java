package com.spring.maintenance.service;

import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;
/**
 * 查看小区列表
 * @author dell
 */
public interface ICommunityService extends IBaseService<CommunityEntity,String> {

    /**
     * 查看小区列表
     * @param vo
     * @return
     * @throws Exception
     */
     ApiResponseResult queryCommunityList(CommunityEntity vo) throws Exception;



}
