package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.NoticeEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.notice.NoticeAddVo;
import com.spring.base.vo.baseinfo.notice.NoticeUpdateVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-07 17:49:59
 * @Desc类说明: 通知公告业务接口类
 */
public interface INoticeService extends IBaseService<NoticeEntity,String> {
	
	/**
	 * 新增通知公告
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-07 17:49:59
	 */
    ApiResponseResult addNotice(NoticeAddVo vo) throws Exception;
	
	/**
	 * 更通知公告
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-07 17:49:59
	 */
    ApiResponseResult updateNotice(NoticeUpdateVo vo) throws Exception;

     /**
      * @description:通知分页
      * @return:
      * @author: 赵进华
      * @time: 2020/6/29 10:02
      */
     ApiResponseResult queryPageNotice(RequestPageVO<NoticeEntity> requestPageVO) throws Exception;
}
