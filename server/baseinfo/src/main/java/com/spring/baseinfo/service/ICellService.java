package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.CellEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.baseinfo.cell.CellAddVo;
import com.spring.base.vo.baseinfo.cell.CellUpdateVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: 单元信息业务接口类
 */
public interface ICellService extends IBaseService<CellEntity,String>{
	
	/**
	 * 新增单元信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult addCell(CellAddVo vo) throws Exception;
	
	/**
	 * 更单元信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-03-31 19:02:26
	 */
    ApiResponseResult updateCell(CellUpdateVo vo) throws Exception;


	/**
	 * 关联删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult deleteHouse(String id) throws Exception;

	/**
	 * 根据条件分页查询小区
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryCellPage(RequestPageVO<CellEntity> requestPageVO) throws Exception;
}
