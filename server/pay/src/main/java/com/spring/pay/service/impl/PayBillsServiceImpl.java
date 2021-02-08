package com.spring.pay.service.impl;

import com.spring.base.entity.pay.BillDetailsEntity;
import com.spring.base.entity.pay.PayBillsEntity;
import com.spring.base.vo.pay.paybills.BillParamVo;
import com.spring.base.vo.pay.paybills.PayBillsAddVo;
import com.spring.base.vo.pay.paybills.PayBillsUpdateVo;
import com.spring.common.request.RequestUtils;
import com.spring.pay.dao.IBillDetailsDao;
import com.spring.pay.dao.IPayBillsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.pay.service.IPayBillsService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:14
 * @Desc类说明: 缴费账单业务接口实现类
 */

 @Slf4j
@Service("payBillsService")
public class PayBillsServiceImpl<mian> extends BaseServiceImpl<PayBillsEntity, String> implements IPayBillsService {
	
	@Autowired
	private IPayBillsDao payBillsDao;

	@Autowired
	private IBillDetailsDao billDetailsDao;

	@Override
	public BaseDao getBaseMapper() {
		return payBillsDao;
	}
	
	/**
	 * 新增缴费账单
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult addPayBills(PayBillsAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		PayBillsEntity entity = new PayBillsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setStatus(1);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = payBillsDao.insert(entity);
		if (no > 0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	/**
	 * 更新缴费账单
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:14
	 */
	@Override
	public ApiResponseResult updatePayBills(PayBillsUpdateVo vo) throws Exception {
		PayBillsEntity entity = new PayBillsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = payBillsDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	@Override
	public ApiResponseResult queryPreview(BillParamVo billParamVo) throws Exception {
		payBillsDao.createBillPreview(billParamVo);
		String orderNo = billParamVo.getResult();
		if (orderNo != null) {
			List<BillDetailsEntity> list = billDetailsDao.queryAllBillPreview(orderNo);
			return createSuccessResult(list);
		}
		return createFailResult(orderNo);
	}

	 /**
	  * 账单生成
	  * @param billParamVo
	  * @return
	  * @throws Exception
	  */
	 @Override
	 public ApiResponseResult createBill(BillParamVo billParamVo) throws Exception {
	 	payBillsDao.createBill(billParamVo);
		 String no = billParamVo.getResult();
		 if ("1".equals(no)) {
			 return createSuccessResult(no);
		 }
		 return createFailResult(no);
	 }


 }
