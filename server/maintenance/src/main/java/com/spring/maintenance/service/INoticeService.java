package com.spring.maintenance.service;

import com.spring.base.entity.baseinfo.NoticeEntity;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;

/**
 * 广告通知
 * @author dell
 */
public interface INoticeService extends IBaseService<NoticeEntity,String> {

    /**
     * 查询广告消息列表
     * @param vo
     * @return
     * @throws Exception
     */
    ApiResponseResult queryNoticeList(NoticeEntity vo) throws Exception;


    /**
     * 根据主键id查询对象
     * @param id
     * @return
     */
    ApiResponseResult queryNotice(String id) throws Exception;
}
