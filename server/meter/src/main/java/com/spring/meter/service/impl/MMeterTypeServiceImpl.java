package com.spring.meter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.meter.MMeterTypeEntity;
import com.spring.base.entity.pay.TransJournalsEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.meter.MMeterTypeAddVo;
import com.spring.base.vo.meter.MMeterTypeUpdateVo;
import com.spring.base.vo.meter.MeterIds;
import com.spring.common.constants.MessageCode;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.SnowflakeIdWorker;
import com.spring.common.util.id.UUIDFactory;
import com.spring.meter.dao.IMMeterTypeDao;
import com.spring.meter.service.IMMeterTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 仪类型业务接口实现类
 */

 @Slf4j
@Service("mMeterTypeService")
public class MMeterTypeServiceImpl extends BaseServiceImpl<MMeterTypeEntity, Long> implements IMMeterTypeService {
	
	@Autowired
	private IMMeterTypeDao mMeterTypeDao;

	@Override
	public BaseDao getBaseMapper() {
		return mMeterTypeDao;
	}
	
	/**
	 * 新增仪类型
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-10-28 14:30:45
	 */
	@Override
	public ApiResponseResult addMMeterType(MMeterTypeAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		MMeterTypeEntity entity = new MMeterTypeEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(SnowflakeIdWorker.generateId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setDelFlag(0);
		entity.setCreateDate(new Date());
		entity.setTenantId(RequestUtils.getTenantId());

		// 新增
		int no = mMeterTypeDao.insert(entity);
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
	 * 更新仪类型
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-10-28 14:30:45
	 */
	@Override
	public ApiResponseResult updateMMeterType(MMeterTypeUpdateVo vo) throws Exception {
		MMeterTypeEntity entity = new MMeterTypeEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		// 更新
		int no = mMeterTypeDao.updateById(entity);
		return createSuccessResult(null);
//		if (no > 0) {
//
//		}
//		return createFailResult();
	}

	 @Override
	 public ApiResponseResult singelDelete(Long id) throws Exception {
		 MMeterTypeEntity entity = new MMeterTypeEntity();
		 entity.setId(id);
		 entity.setModifyUser(RequestUtils.getUserId());
		 entity.setModifyDate(new Date());
		 entity.setDelFlag(1);
		 // 更新
		 int no = mMeterTypeDao.updateById(entity);
		 return createSuccessResult(null);
//		 if (no > 0) {
//
//		 }
//		 return createFailResult();
	 }

	 @Override
	 public ApiResponseResult batchDeleteById(MeterIds ids) throws Exception {
//		String[] idArr = ids.split(",");
//		Map<String,List<String>> meterTypeMap = new HashMap<String,List<String>>();
//		List<String> idList = Arrays.asList(idArr);
//		 meterTypeMap.put("idList",idList);
		 int no = mMeterTypeDao.batchDeleteById(ids.getIds());
		 return createSuccessResult(null);
//		 if (no > 0) {
//
//		 }

//		 return createFailResult();
	 }

	@Override
	public ApiResponseResult queryMeterTypePage(RequestPageVO<MMeterTypeEntity> requestPageVO) throws Exception {
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		List<MMeterTypeEntity> list = mMeterTypeDao.queryMeterTypePage(requestPageVO.getEntity());
		PageInfo<MMeterTypeEntity> pageInfo = new PageInfo<MMeterTypeEntity>(list);
		return createSuccessResult(pageInfo);
	}

	@Override
	public void exportMeterTypeInfo(MMeterTypeEntity vo) throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		List<MMeterTypeEntity> list = mMeterTypeDao.queryMeterTypePage(vo);
		try{
			excelDownload(response,list);
			log.info("收费历史记录导出数据信息exportRecordInfo"+list);
		}catch (Exception e){
			log.error("收费历史记录导出exportRecordInfo异常"+e);
		}
	}

	public void excelDownload(HttpServletResponse response, List<MMeterTypeEntity> list) throws Exception {
		String[] header = {"仪表类型", "计量单位", "备注信息"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("仪表类型信息");
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
		for(int i=0;i<list.size();i++) {
			HSSFRow row1 = sheet.createRow(i + 1);
			row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getTypeName()));
			row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getUnit()));
			row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getMark()));
		}
		String name = "仪表类型信息";
		response.setHeader("content-Type","application/ms-excel");
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}
}
