package com.spring.pay.dao;

import com.spring.base.entity.pay.PayBillsEntity;
import com.spring.base.vo.pay.paybills.BillParamVo;
import com.spring.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 缴费账单Dao
 */
@Mapper
public interface IPayBillsDao extends BaseDao<PayBillsEntity> {
	String createBill(BillParamVo billParamVo);

	String createBillPreview(BillParamVo billParamVo);
}
