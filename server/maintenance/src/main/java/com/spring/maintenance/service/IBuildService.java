package com.spring.maintenance.service;

import com.spring.base.entity.baseinfo.BuildEntity;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;

/**
 * @author dell
 */
public interface IBuildService extends IBaseService<BuildEntity,String> {
    /**
     * 楼栋列表
     * @param vo
     * @return
     * @throws Exception
     */
     ApiResponseResult queryBuildList(BuildEntity vo) throws Exception;

}
