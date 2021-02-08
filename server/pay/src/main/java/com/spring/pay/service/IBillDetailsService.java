package com.spring.pay.service;

import com.spring.base.entity.pay.BillDetailsEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.pay.billdetail.BillDetailParamVo;
import com.spring.base.vo.pay.billdetail.BillDetailsAddVo;
import com.spring.base.vo.pay.billdetail.BillDetailsUpdateVo;
import com.spring.base.vo.pay.billdetail.BillDetailsVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:13
 * @Desc类说明: 账单详情业务接口类
 */
public interface IBillDetailsService extends IBaseService<BillDetailsEntity,String>{


	 /**
	  * @Desc:批量删除
	  * @param list
	  * @Author:邓磊
	  * @UpdateDate:2020/6/4 17:03
	  * @return: 返回
	  */
	 ApiResponseResult delete(BillDetailsVo vo) throws Exception;

	
	/**
	 * 新增账单详情
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:13
	 */
    ApiResponseResult addBillDetails(BillDetailsAddVo vo) throws Exception;
	
	/**
	 * 更账单详情
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:13
	 */
    ApiResponseResult updateBillDetails(BillDetailsUpdateVo vo) throws Exception;

	/**
	 * 查询欠费账单
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryDebtBillPage(BillDetailParamVo requestPageVO) throws Exception;

	/**
	 * 查询欠费账单
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryDebtBillPageForOne(BillDetailParamVo requestPageVO) throws Exception;

	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryAllBill(RequestPageVO<BillDetailParamVo> requestPageVO) throws Exception;


	/**
	 * 根据条件分页查询
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult queryAllBillForOne(RequestPageVO<BillDetailParamVo> requestPageVO) throws Exception;


	/**
	 * @Desc:已出账单导出
	 * @param billDetailParamVo
	 * @Author:邓磊
	 * @UpdateDate:2020/5/20 16:11
	 * @return: 返回
	 */
	 void  exportBillDetailParamInfo(BillDetailParamVo billDetailParamVo)throws Exception ;
}
