package com.spring.saas.service.impl;

import com.spring.base.entity.saas.InvoiceOrderEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.saas.InvoiceOrderAddVo;
import com.spring.base.vo.saas.InvoiceOrderUpdateVo;
import com.spring.common.request.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.saas.dao.IInvoiceOrderDao;
import com.spring.saas.service.IInvoiceOrderService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

 /**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-10 09:43:02
 * @Desc类说明: 发票订单关系业务接口实现类
 */

 @Slf4j
@Service("invoiceOrderService")
public class InvoiceOrderServiceImpl extends BaseServiceImpl<InvoiceOrderEntity, String> implements IInvoiceOrderService {
	
	@Autowired
	private IInvoiceOrderDao invoiceOrderDao;

	@Override
	public BaseDao getBaseMapper() {
		return invoiceOrderDao;
	}
	
	/**
	 * 新增发票订单关系
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-07-10 09:43:02
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult addInvoiceOrder(InvoiceOrderAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		InvoiceOrderEntity entity = new InvoiceOrderEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = invoiceOrderDao.insert(entity);
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
	 * 更新发票订单关系
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-10 09:43:02
	 */
	@Override
	public ApiResponseResult updateInvoiceOrder(InvoiceOrderUpdateVo vo) throws Exception {
		InvoiceOrderEntity entity = new InvoiceOrderEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = invoiceOrderDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}
}
