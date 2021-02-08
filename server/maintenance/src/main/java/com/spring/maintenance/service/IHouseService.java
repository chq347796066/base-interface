package com.spring.maintenance.service;

import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * @author dell
 */
public interface IHouseService extends IBaseService<HouseEntity,String> {


    /**
     * 查看房屋列表
     */
    ApiResponseResult queryHouseList(@RequestBody HouseEntity vo) throws Exception;


    /**
     * @Desc:    房屋审核确认房屋信息业主
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/5/13 10:33
     * @return: 返回
     */
    ApiResponseResult queryNameMobile(HouseEntity vo) throws Exception;

}
