package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.CarEntity;
import com.spring.base.entity.baseinfo.CellEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.car.CarAddVo;
import com.spring.base.vo.baseinfo.car.CarUpdateVo;
import com.spring.base.vo.baseinfo.car.CarVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明:  车位信息业务接口类
 */
public interface ICarService extends IBaseService<CarEntity,String> {

	/**
	 * 新增 车位信息
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult addCar(CarAddVo vo) throws Exception;

	/**
	 * 更 车位信息
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult updateCar(CarUpdateVo vo) throws Exception;

	/**
	 * 关联删除
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult deleteCustomer(String id) throws Exception;


	/**
	 * @param id
	 * @Desc: 车位删除
	 * @Author:邓磊
	 * @UpdateDate:2020/4/18 11:34
	 * @return: ApiResponseResult
	 */
    ApiResponseResult deleteCra(String id);


	/**
	 * @Desc:   导入车位信息
	 * @param users
	 * @Author:邓磊
	 * @UpdateDate:2020/4/21 10:45
	 * @return: 返回
	 */
	ApiResponseResult impUser(List<CarVo> users, String[] tenantCompanyArray, String communityId);



	/**
	 * @Desc: 车位信息批量导入下载模板
	 * @param communityId
	 * @Author:邓磊
	 * @UpdateDate:2020/4/24 14:54
	 * @return: 返回
	 */
    void expUserDemo(HttpServletResponse response, String communityId) throws Exception;



	/**
	 * @Desc:车位导出信息数据
	 * @param requestPageVO
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 11:20
	 * @return: 返回
	 */
	 void  exportCarEntityInfo(CarEntity requestPageVO) throws Exception;

	/**
	 * 根据条件分页查询车位
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryCarPage(RequestPageVO<CarEntity> requestPageVO) throws Exception;

}