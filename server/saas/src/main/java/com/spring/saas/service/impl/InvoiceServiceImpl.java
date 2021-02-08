package com.spring.saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.entity.saas.InvoiceEntity;
import com.spring.base.entity.saas.InvoiceOrderEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.saas.*;
import com.spring.common.enums.InvoiceStatusEnum;
import com.spring.common.exception.ServiceException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.util.date.DateHelperExt;
import com.spring.common.util.date.DateStyle;
import com.spring.saas.dao.IInvoiceOrderDao;
import com.spring.saas.dao.IOrderDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.saas.dao.IInvoiceDao;
import com.spring.saas.service.IInvoiceService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-09 10:48:56
 * @Desc类说明: 发票信息业务接口实现类
 */

 @Slf4j
@Service("invoiceService")
public class InvoiceServiceImpl extends BaseServiceImpl<InvoiceEntity, String> implements IInvoiceService {
	
	@Autowired
	private IInvoiceDao invoiceDao;

	@Autowired
    private IInvoiceOrderDao invoiceOrderDao;

	@Autowired
	private IOrderDao orderDao;

	@Override
	public BaseDao getBaseMapper() {
		return invoiceDao;
	}
	
	/**
	 * 新增发票信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-07-09 10:48:56
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult addInvoice(InvoiceAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		InvoiceEntity entity = new InvoiceEntity();
		List<InvoiceOrderEntity> list=new ArrayList<>();
		//将以逗号隔开的订单id转成List集合
		List<String> orderIds= Arrays.asList(vo.getOrderId().split(","));
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setApplyTime(new Date());
		entity.setInvoiceStatus(1);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		entity.setTenantId(RequestUtils.getTenantId());
		for (int i=0;i<orderIds.size();i++){
			//往发票订单关系表插入数据
			InvoiceOrderEntity invoiceOrderEntity=new InvoiceOrderEntity();
			invoiceOrderEntity.setId(UUIDFactory.createId());
			invoiceOrderEntity.setInvoiceId(entity.getId());
			invoiceOrderEntity.setOrderId(orderIds.get(i));
			invoiceOrderEntity.setCreateDate(new Date());
			invoiceOrderEntity.setCreateUser(RequestUtils.getUserId());
			list.add(invoiceOrderEntity);
		}
		//新增发票信息
		int no = invoiceDao.insert(entity);
		int num=0;
		if (CollectionUtils.isNotEmpty(list)){
			num=invoiceOrderDao.addList(list);
        }
		//更新订单表里面的发票状态
		int number=orderDao.batchUpdate(orderIds);
		if (no > 0 && num>0 && number>0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	/**
	 * 查询发票列表
	 * @author 熊锋
	 * @param requestPageVO
	 * @date 2020/7/10 11:31
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryInvoice(RequestPageVO<InvoiceQueryVo> requestPageVO) throws Exception {
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		List<InvoiceEntity> list=invoiceDao.queryInvoice(requestPageVO.getEntity());
		PageInfo<InvoiceEntity> pageInfo = new PageInfo<>(list);
		return createSuccessResult(pageInfo);
	}

	/**
	 * 查询开票详情
	 * @author 熊锋
	 * @param id
	 * @date 2020/7/10 14:06
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryMakeInvoiceDetail(String id) throws Exception{
		List<String> orderIds=new ArrayList<>();
		QueryWrapper<InvoiceOrderEntity> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("del_flag",0).eq("invoice_id",id);
		//根据发票id查询对应订单id
		List<InvoiceOrderEntity> list=invoiceOrderDao.selectList(queryWrapper);
		if (CollectionUtils.isNotEmpty(list)){
			list.stream().forEach(e->{
				String orderId=e.getOrderId();
				orderIds.add(orderId);
			});
		}
		//根据订单id查询订单信息
		if (CollectionUtils.isNotEmpty(orderIds)){
			List<OrderResponseVo> orderList=orderDao.queryOrderByOrderId(orderIds);
			return createSuccessResult(orderList);
		}
		return createSuccessResult(null);
	}

	/**
	 * 分页查询开票查询（运营后台）
	 *
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/10 19:24
	 */
	@Override
	public ApiResponseResult invoiceInquiryQueryPage(RequestPageVO<InvoiceInquiryQueryVo> requestPageVO) throws Exception {
		// 设置分页参数
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);

		InvoiceInquiryQueryVo invoiceInquiryQueryVo = requestPageVO.getEntity();

		if (invoiceInquiryQueryVo != null) {
			if (StringUtils.isNotEmpty(invoiceInquiryQueryVo.getCompanyName())) {
				invoiceInquiryQueryVo.setCompanyName("%" + invoiceInquiryQueryVo.getCompanyName() + "%");
			}

			if (StringUtils.isNotEmpty(invoiceInquiryQueryVo.getStartDate())
					&& StringUtils.isEmpty(invoiceInquiryQueryVo.getEndDate())) {
				throw new ServiceException("请选择结束时间");
			}

			if (StringUtils.isEmpty(invoiceInquiryQueryVo.getStartDate())
					&& StringUtils.isNotEmpty(invoiceInquiryQueryVo.getEndDate())) {
				throw new ServiceException("请选择起始时间");
			}

			if (StringUtils.isNotEmpty(invoiceInquiryQueryVo.getStartDate())
					&& StringUtils.isNotEmpty(invoiceInquiryQueryVo.getEndDate())) {

				Date startDate = DateHelperExt.stringToDate(invoiceInquiryQueryVo.getStartDate(), DateStyle.YYYY_MM_DD);
				Date endDate = DateHelperExt.stringToDate(invoiceInquiryQueryVo.getEndDate(), DateStyle.YYYY_MM_DD);

				// 开始时间不能大于结束时间
				if (startDate.after(endDate)) {
					throw new ServiceException("起始时间时间不能大于结束时间");
				}

				invoiceInquiryQueryVo.setStartDate(invoiceInquiryQueryVo.getStartDate() + " 00:00:00");
				invoiceInquiryQueryVo.setEndDate(invoiceInquiryQueryVo.getEndDate() + " 23:59:59");
			}
		}

		// 分页查询开票查询
		List<InvoiceInquiryPageVo> invoiceInquiryPageVoList = invoiceDao.invoiceInquiryQueryPage(invoiceInquiryQueryVo);

		// 设置分页
		PageInfo<InvoiceInquiryPageVo> pageInfo = new PageInfo<>(invoiceInquiryPageVoList);
		return createSuccessResult(pageInfo);
	}

	/**
	 * 查询开票摘要
	 *
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/13 14:56
	 */
	@Override
	public ApiResponseResult queryInvoiceSummary(String invoiceId) throws Exception {
		if (StringUtils.isBlank(invoiceId)) {
			throw new ServiceException("开票Id不能为空");
		}
		return createSuccessResult(invoiceDao.queryInvoiceSummary(invoiceId));
	}

	/**
	 * 查询开票详情
	 *
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/13 15:22
	 */
	@Override
	public ApiResponseResult queryInvoiceDetailList(String invoiceId) throws Exception {
		if (StringUtils.isBlank(invoiceId)) {
			throw new ServiceException("开票Id不能为空");
		}
		return createSuccessResult(invoiceDao.queryInvoiceDetailList(invoiceId));
	}

	/**
	 * 审核发票
	 *
	 * @param invoiceReviewVo
	 * @return
	 * @throws Exception
	 * @author WuJiaQuan
	 * @date 2020/7/13 15:36
	 */
	@Override
	public ApiResponseResult reviewInvoice(InvoiceReviewVo invoiceReviewVo) throws Exception {
	    if (invoiceReviewVo == null) {
            throw new ServiceException("填写信息异常");
        }

		if (StringUtils.isBlank(invoiceReviewVo.getInvoiceId())) {
			throw new ServiceException("开票Id不能为空");
		}

		if (invoiceReviewVo.getInvoiceStatus() == null) {
			throw new ServiceException("发票状态不能为空");
		}

		// 查询发票信息
		InvoiceEntity invoice = invoiceDao.selectById(invoiceReviewVo.getInvoiceId());
		if (invoice == null) {
			throw new ServiceException("没有找到相关的发票信息");
		}

		Integer retStatus = 0;

		if (invoice.getInvoiceStatus() == null
				|| invoice.getInvoiceStatus().equals(InvoiceStatusEnum.PENDING_REVIEW.getEnumCode())) {
			if(invoice.getInvoiceStatus().equals(InvoiceStatusEnum.INVOICING_FAILED.getEnumCode())) {
				throw new ServiceException("发票已驳回，请不要重复操作");
			}
			if(invoice.getInvoiceStatus().equals(InvoiceStatusEnum.INVOICED.getEnumCode())) {
				throw new ServiceException("发票已审核通过，请不要重复操作");
			}
		}

		// 审核驳回
		if (invoiceReviewVo.getInvoiceStatus().equals(InvoiceStatusEnum.INVOICING_FAILED.getEnumCode())) {
			if (StringUtils.isBlank(invoiceReviewVo.getReason())) {
				throw new ServiceException("审核驳回必须填写驳回原因");
			}
		}

		// 发票状态
		invoice.setInvoiceStatus(invoiceReviewVo.getInvoiceStatus());
		// 更新用户
		invoice.setModifyUser(RequestUtils.getUserId());
		// 更新时间
		invoice.setModifyDate(new Date());
		// 驳回原因
		invoice.setReason(invoiceReviewVo.getReason());

		retStatus = invoiceDao.updateById(invoice);

		return retStatus > 0 ? createSuccessResult(null) : createFailResult();
	}

	/**
	 * 查询开票摘要
	 * @author 熊锋
	 * @param id
	 * @date 2020/7/10 14:06
	 * @return com.spring.common.response.ApiResponseResult
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryMakeInvoiceSummary(String id) throws Exception {
		MakeSummaryVo makeSummaryVo=invoiceDao.queryMakeInvoiceSummary(id);
		if (makeSummaryVo==null){
			return createSuccessResult(null);
		}

		return createSuccessResult(makeSummaryVo);
	}

}
