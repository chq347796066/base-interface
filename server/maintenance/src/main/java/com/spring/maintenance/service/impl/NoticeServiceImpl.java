package com.spring.maintenance.service.impl;
import com.spring.base.dao.BaseDao;

import com.spring.base.entity.baseinfo.NoticeEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.INoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author dell
 */
@Slf4j
@Service("noticeServiceI")
public class NoticeServiceImpl extends BaseServiceImpl<NoticeEntity, String> implements INoticeService {

    @Autowired
    private BaseInfoFeignClient noticeFeignCilnet;

    @Override
    public BaseDao getBaseMapper() { return null;}

    /**
     * 查看公告信息列表
     * @param vo
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryNoticeList(NoticeEntity vo) throws Exception {
        return noticeFeignCilnet.queryList(vo);
    }

    /**
     * 查询对象详情
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ApiResponseResult queryNotice(String id) throws Exception {
        return noticeFeignCilnet.queryObject(id);
    }
}
