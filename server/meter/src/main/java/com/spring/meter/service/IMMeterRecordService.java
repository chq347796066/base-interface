package com.spring.meter.service;

import com.spring.base.entity.meter.MMeterRecordEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.meter.*;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 房屋抄记录业务接口类
 */
public interface IMMeterRecordService extends IBaseService<MMeterRecordEntity,Long>{
	
	/**
	 * 新增房屋抄记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-10-28 14:30:45
	 */
	 ApiResponseResult addMMeterRecord(MMeterRecordAddVo vo) throws Exception;
	
	/**
	 * 更房屋抄记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-10-28 14:30:45
	 */
	 ApiResponseResult updateMMeterRecord(MMeterRecordUpdateVo vo) throws Exception;

	 /**
	  * 删除房屋抄记录
	  * @param id
	  * @return
	  * @throws Exception
	  * @author 作者：ZhaoJinHua
	  * @version 创建时间：2020-10-28 14:30:45
	  */
     ApiResponseResult deleteMeterRecord(Long id) throws Exception;

	 ApiResponseResult batchDeleteMeterRecord(MeterIds ids) throws Exception;

    ApiResponseResult queryStatisPage(RequestPageVO<MMeterRecordStatisParam> requestPageVO) throws Exception;

	ApiResponseResult queryStatisDetailPage(RequestPageVO<MMeterRecordStatisParam> requestPageVO) throws Exception;

    ApiResponseResult queryStatisByCommunity(MMeterRecordStatisParam paramVo) throws Exception;

    void exportMeterStatis(MMeterRecordStatisParam vo) throws Exception;

	void exportMeterRecord(MMeterRecordEntity vo) throws Exception;

	void exportMeterStatisDetail(MMeterRecordStatisParam vo) throws Exception;
}
