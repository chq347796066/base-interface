package com.spring.pay.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.entity.pay.BillDetailsEntity;
import com.spring.base.vo.pay.billdetail.*;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.pay.dao.IBillDetailsDao;
import com.spring.pay.service.IBillDetailsService;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-23 09:49:13
 * @Desc类说明: 账单详情业务接口实现类
 */

 @Slf4j
@Service("billDetailsService")
public class BillDetailsServiceImpl extends BaseServiceImpl<BillDetailsEntity, String> implements IBillDetailsService {
	
	@Autowired
	private IBillDetailsDao billDetailsDao;

	@Override
	public BaseDao getBaseMapper() {
		return billDetailsDao;
	}


	/**
	 * 新增账单详情
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-23 09:49:13
	 */
	@Override
	public ApiResponseResult addBillDetails(BillDetailsAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		BillDetailsEntity entity = new BillDetailsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setStatus(1);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = billDetailsDao.insert(entity);
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
	 * 更新账单详情
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-23 09:49:13
	 */
	@Override
	public ApiResponseResult updateBillDetails(BillDetailsUpdateVo vo) throws Exception {
		BillDetailsEntity entity = new BillDetailsEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = billDetailsDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	/**
	 * 查询所有欠费账单
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */

	 @Override
	 public ApiResponseResult queryDebtBillPage(BillDetailParamVo requestPageVO) throws Exception {
		 ApiResponseResult result = new ApiResponseResult();
		 List<BillDetailsEntity> list = billDetailsDao.queryDebtBillPage(requestPageVO);
		 return createSuccessResult(list);
	 }

	@Override
	public ApiResponseResult queryDebtBillPageForOne(BillDetailParamVo requestPageVO) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		List<BillDetailsEntity> list = billDetailsDao.queryDebtBillPageForOne(requestPageVO);
		return createSuccessResult(list);
	}


	/**
	 * 根据条件分页查询
	 *
	 * @param requestPageVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryAllBill(RequestPageVO<BillDetailParamVo> requestPageVO) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		List<BillDetailsEntity> list = billDetailsDao.queryAllBills(requestPageVO.getEntity());
		PageInfo<BillDetailsEntity> pageInfo = new PageInfo<BillDetailsEntity>(list);
		return createSuccessResult(pageInfo);
	}

	@Override
	public ApiResponseResult queryAllBillForOne(RequestPageVO<BillDetailParamVo> requestPageVO) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		List<BillDetailsEntity> list = billDetailsDao.queryAllBillsForOne(requestPageVO.getEntity());
		PageInfo<BillDetailsEntity> pageInfo = new PageInfo<BillDetailsEntity>(list);
		return createSuccessResult(pageInfo);
	}


	/**
	 * @Desc:已出账单导出
	 * @param requestPageVO
	 * @Author:邓磊
	 * @UpdateDate:2020/5/20 16:11
	 * @return: 返回
	 */
	@Override
	public void exportBillDetailParamInfo(BillDetailParamVo requestPageVO) throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		List<BillDetailsEntity> list = billDetailsDao.queryAllBills(requestPageVO);
		excelDownload(response,list);
	}
	public void excelDownload(HttpServletResponse response,List<BillDetailsEntity> list) throws Exception {
		String[] header = {"房屋编号","客户名称","费项科目","费项标准","费用月份","应收总额","账单金额","违约金","未缴金额","账单状态"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("已出账单信息");
		sheet.setDefaultColumnWidth(15);
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		HSSFRow headrow = sheet.createRow(0);
		for (int i = 0; i < header.length; i++) {
			HSSFCell cell = headrow.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(header[i]);
			cell.setCellValue(text);
			cell.setCellStyle(headerStyle);
		}
		for(int i=0;i<list.size();i++){
			HSSFRow row1 = sheet.createRow(i+1);
			row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getHouseCode()));
			if(null !=list.get(i).getPayBillsEntity() && StringUtils.isNotEmpty(list.get(i).getPayBillsEntity().getOwnerName())){
				row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getPayBillsEntity().getOwnerName()));
			}else{
				row1.createCell(1).setCellValue(new HSSFRichTextString(""));
			}
			row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getChargeTypeName()));
			row1.createCell(3).setCellValue(new HSSFRichTextString(list.get(i).getChargeName()));
			if(null !=list.get(i).getPayBillsEntity() && StringUtils.isNotEmpty(list.get(i).getPayBillsEntity().getBillDate())){
				row1.createCell(4).setCellValue(new HSSFRichTextString(list.get(i).getPayBillsEntity().getBillDate()));
			}else{
				row1.createCell(4).setCellValue(new HSSFRichTextString(""));
			}
			row1.createCell(5).setCellValue(new HSSFRichTextString(list.get(i).getPaymentAmount()));
			if(null !=list.get(i).getPayBillsEntity() && StringUtils.isNotEmpty(list.get(i).getPayBillsEntity().getBillAmount())) {
				row1.createCell(6).setCellValue(new HSSFRichTextString(list.get(i).getPayBillsEntity().getBillAmount()));
			}else{
				row1.createCell(6).setCellValue(new HSSFRichTextString(""));
			}
			row1.createCell(7).setCellValue(new HSSFRichTextString(list.get(i).getDPenalty()));
			if(StringUtils.isNotEmpty(list.get(i).getPaymentAmount()) && StringUtils.isNotEmpty(list.get(i).getReceivedAmount())){
				BigDecimal payment = new BigDecimal(list.get(i).getPaymentAmount());
				payment = payment.setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal receive = new BigDecimal(list.get(i).getReceivedAmount());
				receive = receive.setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal subtract = payment.subtract(receive);
				row1.createCell(8).setCellValue(new HSSFRichTextString(subtract.toString()));
			}else{
				row1.createCell(8).setCellValue(new HSSFRichTextString(list.get(i).getPaymentAmount()));
			}
			if("01".equals(list.get(i).getChargeStatus())){
				row1.createCell(9).setCellValue(new HSSFRichTextString("未缴"));
			}else{
				row1.createCell(9).setCellValue(new HSSFRichTextString("已缴"));
			}
		}
		String name = "已出账单信息";
		response.setHeader("content-Type","application/ms-excel");
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}



	/**
	 * @Desc:批量删除
	 * @param list
	 * @Author:邓磊
	 * @UpdateDate:2020/6/4 17:04
	 * @return: 返回
	 */
	@Override
	public ApiResponseResult delete(BillDetailsVo vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		if(null != vo){
			List<BillDetailsEntity> list = vo.getList();
			if(CollectionUtils.isNotEmpty(list)){
				list.stream().forEach(billDetailsEntity -> {
					if("01".equals(billDetailsEntity.getChargeStatus())){
						billDetailsDao.deleteDelFlag(billDetailsEntity);
					}
				});
			}
		}
		result.setCode(MessageCode.SUCCESS);
		result.setMsg(MessageCode.SUCCESS_TEXT);
		result.setData(1);
		return result;
	}


}
