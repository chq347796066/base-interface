package com.spring.maintenance.service;

import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.buiness.myhouse.MyHouseAddVo;
import com.spring.base.vo.buiness.myhouse.MyHouseUpdateVo;
import com.spring.base.vo.buiness.myhouse.MyHouseVo;
import com.spring.common.response.ApiResponseResult;

import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-17 09:46:08
 * @Desc类说明: 我的房屋信息业务接口类
 */
public interface IMyHouseService extends IBaseService<MyHouseEntity,String>{
	
	/**
	 * 新增我的房屋信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-17 09:46:08
	 */
    ApiResponseResult addMyHouse(MyHouseAddVo vo) throws Exception;
	
	/**
	 * 更我的房屋信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-17 09:46:08
	 */
    ApiResponseResult updateMyHouse(MyHouseUpdateVo vo) throws Exception;


	/**
	 * @Desc:    删除我的房屋
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/18 10:05
	 * @return: ApiResponseResult
	 */
    ApiResponseResult deleteMyHouse(MyHouseEntity vo) throws Exception;


	/**
	 * @Desc: 查询我的租客列表
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/22 14:35
	 * @return: 返回
	 */
    ApiResponseResult queryRenterList(MyHouseVo vo) throws Exception;

	/**
	 * @Desc:    根据条件系统管理用户管理业主APP用户查询列表
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/5/7 16:29
	 * @return: 返回
	 */
    List<MyHouseEntity> queryUserAppHouseList(MyHouseEntity vo) throws Exception;
}
