package com.spring.maintenance.service;

import com.spring.base.entity.baseinfo.CellEntity;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author dell
 */
public interface ICellService extends IBaseService<CellEntity,String> {

    /**
     * 单元列表查询
     * @param vo
     * @return
     * @throws Exception
     */
    ApiResponseResult queryBuildList(@RequestBody CellEntity vo) throws Exception;

}
