package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.house.HouseAddVo;
import com.spring.base.vo.baseinfo.house.HouseTemplateVo;
import com.spring.base.vo.baseinfo.house.HouseUpdateVo;
import com.spring.base.vo.baseinfo.housingcertification.HouseDeleteParamVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 房产信息业务接口类
 */
public interface IHouseService extends IBaseService<HouseEntity,String> {

	/**
	 * 新增房产信息
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult addHouse(HouseAddVo vo) throws Exception;

	/**
	 * 更房产信息
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult updateHouse(HouseUpdateVo vo) throws Exception;


	/**
	 * 关联删除
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult deleteCustomer(String id) throws Exception;



	/**
	 * @Desc:    批量导入
	 * @param voList
	 * @Author:邓磊
	 * @UpdateDate:2020/4/27 18:23
	 * @return: 返回
	 */
	ApiResponseResult batchImportHouse(List<HouseTemplateVo> voList, String[] tenantCompanyArray, String communityId);


	/**
	 * @Desc:    详情
	 * @Author:邓磊
	 * @UpdateDate:2020/5/13 10:14
	 * @return: 返回
	 */
    HouseEntity queryHouseInfo(HouseEntity entity) throws Exception;

	/**
	 * @Desc: 房屋审核确认房屋信息
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/5/13 10:14
	 * @return: 返回
	 */
    ApiResponseResult queryOwnerNameMobile(HouseEntity vo) throws Exception;
	
	
	/**
	 * @Desc:    房产导出数据信息
	 * @param houseEntity
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 10:16
	 * @return: 返回
	 */
	 void  exportHouseEntityInfo(HouseEntity houseEntity) throws Exception;

	/**
	 * 根据条件分页查询房产
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryHousePage(RequestPageVO<HouseEntity> requestPageVO) throws Exception;

	List<LinkedHashMap<String, String>> queryExportList(Map<String, Object> params);

	ApiResponseResult addHouseUser(MyHouseEntity myHouseEntity);

	ApiResponseResult deleteHouseUser(HouseDeleteParamVo houseDeleteParamVo);
}
