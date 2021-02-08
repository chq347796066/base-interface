package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.HouseInstrumentEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.houseinstrument.HouseInstrumentAddVo;
import com.spring.base.vo.baseinfo.houseinstrument.HouseInstrumentUpdateVo;
import com.spring.common.response.ApiResponseResult;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-03 09:35:57
 * @Desc类说明: 房屋仪管理业务接口类
 */
public interface IHouseInstrumentService extends IBaseService<HouseInstrumentEntity,Long> {

	/**
	 * 新增房屋仪管理
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-03 09:35:57
	 */
	 ApiResponseResult addHouseInstrument(HouseInstrumentAddVo vo) throws Exception;

	/**
	 * 更房屋仪管理
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-03 09:35:57
	 */
	 ApiResponseResult updateHouseInstrument(HouseInstrumentUpdateVo vo) throws Exception;

	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	 ApiResponseResult deleteDetail(Long id) throws Exception;


	 /**
	  * @Desc:查看房屋仪表详情
	  * @param vo
	  * @Author:邓磊
	  * @UpdateDate:2020/6/2 15:36
	  * @return: 返回
	  */
	  ApiResponseResult queryHouseInstrumentInfo(HouseInstrumentEntity vo) throws Exception;

	  /**
	   * @Desc:房屋仪表管理导出
	   * @param voEntity
	   * @Author:邓磊
	   * @UpdateDate:2020/6/2 15:44
	   * @return: 返回
	   */
	   void exportHouseInstrumentEntityInfo(HouseInstrumentEntity voEntity) throws Exception;

	/**
	 * @Desc:房屋仪表批量导入
	 * @param voList
	 * @Author:邓磊
	 * @UpdateDate:2020/4/21 10:45
	 * @return: 返回
	 */
	ApiResponseResult batchImportHouseInstrument(List<HouseInstrumentAddVo> voList, String companyId, String communityId);

}