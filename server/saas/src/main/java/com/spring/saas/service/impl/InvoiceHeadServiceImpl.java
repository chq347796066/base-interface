package com.spring.saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.entity.saas.InvoiceHeadEntity;
import com.spring.base.entity.saas.OrderEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.saas.InvoiceHeadAddVo;
import com.spring.base.vo.saas.InvoiceHeadUpdateVo;
import com.spring.base.vo.saas.OrderResponseVo;
import com.spring.base.vo.saas.TenantPageVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.saas.dao.IOrderDao;
import com.spring.saas.service.IOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.saas.dao.IInvoiceHeadDao;
import com.spring.saas.service.IInvoiceHeadService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 发票抬头业务接口实现类
 */

 @Slf4j
@Service("invoiceHeadService")
public class InvoiceHeadServiceImpl extends BaseServiceImpl<InvoiceHeadEntity, String> implements IInvoiceHeadService {
	
	@Autowired
	private IInvoiceHeadDao invoiceHeadDao;

	@Autowired
	private IOrderDao orderDao;

	@Override
	public BaseDao getBaseMapper() {
		return invoiceHeadDao;
	}
	
	/**
	 * 新增发票抬头
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-07-09 10:48:56
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult addInvoiceHead(InvoiceHeadAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		InvoiceHeadEntity entity = new InvoiceHeadEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		entity.setTenantId(RequestUtils.getTenantId());
		// 新增
		int no = invoiceHeadDao.insert(entity);
		//如果新增发票为默认发票 设置其他发票为非默认
		if (vo.getIsDefault()==0){
			int num=invoiceHeadDao.deEnableInvoiceHead(entity);
		}
		if (no > 0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("新增发票抬头成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增发票抬头失败");
		}
		return result;
	}
	/**
	 * 更新发票抬头
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-07-09 10:48:56
	 */
	@Override
	public ApiResponseResult updateInvoiceHead(InvoiceHeadUpdateVo vo) throws Exception {
		InvoiceHeadEntity entity = new InvoiceHeadEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = invoiceHeadDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	 /**
	  * 删除发票抬头
	  * @author 熊锋
	  * @param id
	  * @date 2020/7/9 11:28
	  * @return com.spring.common.response.ApiResponseResult
	  * @throws Exception
	  */
	 @Override
	 public ApiResponseResult deleteInvoiceHead(String id) throws Exception {
		 InvoiceHeadEntity entity=new InvoiceHeadEntity();
		 entity.setId(id);
		 entity.setDelFlag(1);
		 int no=invoiceHeadDao.updateById(entity);
		 if (no > 0) {
			 return createSuccessResult(null);
		 }
		 return createFailResult();
	 }
	 /**
	  * 设置默认
	  * @author 熊锋
	  * @date 2020/7/9 11:28
	  * @return com.spring.common.response.ApiResponseResult
	  * @throws Exception
	  */
	 @Override
	 public ApiResponseResult setDefault(String id) throws Exception{
		 InvoiceHeadEntity entity=new InvoiceHeadEntity();
		 entity.setId(id);
		 entity.setIsDefault(0);
		 entity.setMobile(RequestUtils.getUserId());
		 int no=invoiceHeadDao.updateById(entity);
		 int num=invoiceHeadDao.deEnableInvoiceHead(entity);
		 if (no > 0 && num>0) {
			 return createSuccessResult(null);
		 }
		 return createFailResult();
	 }

	 /**
	  * 查询默认发票抬头
	  * @author 熊锋
	  * @date 2020/7/9 14:11
	  * @return
	  * @throws Exception
	  */
	 @Override
	 public ApiResponseResult queryDefaultInvoice(String mobile) throws Exception {
	 	 ApiResponseResult result=new ApiResponseResult();
		 QueryWrapper<InvoiceHeadEntity> queryWrapper=new QueryWrapper<>();
		 queryWrapper.eq("is_default",0)
				 .eq("del_flag",0)
		 		 .eq("mobile",mobile);
		 InvoiceHeadEntity entity=invoiceHeadDao.selectOne(queryWrapper);
		 if (entity!=null){
			 result.setCode(MessageCode.SUCCESS);
			 result.setMsg("查询成功");
			 result.setData(entity);
		 }
		 return result;
	 }

	 /**
	  * 查询发票可索取金额
	  * @param mobile
	  * @author 熊锋
	  * @date 2020/7/9 14:11
	  * @return
	  * @throws Exception
	  */
	 @Override
	 public ApiResponseResult queryInvoiceMoney(String mobile) throws Exception {
		 ApiResponseResult result=new ApiResponseResult();
		 String totalMoney="";
		 List<OrderResponseVo> list=orderDao.queryAlreadyPayOrder(mobile);
		 if (CollectionUtils.isNotEmpty(list)){
			 double totalAmount=list.stream().mapToDouble(OrderResponseVo::getOrderAmount).sum();
			 totalMoney=String.valueOf(totalAmount);
		 }
		 result.setCode(MessageCode.SUCCESS);
		 result.setMsg("成功");
		 result.setData(totalMoney);
		 return result;
	 }

	/**
	 * 查询可开票订单
	 * @param requestPageVO
	 * @author 熊锋
	 * @date 2020/7/9 14:11
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryInvoiceOrder(RequestPageVO<OrderEntity> requestPageVO) throws Exception{
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		List<OrderResponseVo> list=orderDao.queryAlreadyPayOrder(requestPageVO.getEntity().getMobile());
		if (CollectionUtils.isNotEmpty(list)){
			PageInfo<OrderResponseVo> pageInfo = new PageInfo<>(list);
			return createSuccessResult(pageInfo);
		}
		return createSuccessResult(null);
	}

}
