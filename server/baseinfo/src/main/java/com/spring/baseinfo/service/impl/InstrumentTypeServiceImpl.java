package com.spring.baseinfo.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.InstrumentTypeEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.instrumenttype.InstrumentTypeAddVo;
import com.spring.base.vo.baseinfo.instrumenttype.InstrumentTypeUpdateVo;
import com.spring.baseinfo.dao.IHouseInstrumentDao;
import com.spring.baseinfo.dao.IInstrumentTypeDao;
import com.spring.baseinfo.service.IInstrumentTypeService;
import com.spring.common.constants.MessageCode;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.SnowflakeIdWorker;
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
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-02 17:17:10
 * @Desc类说明: 仪类型业务接口实现类
 */

@Service("instrumentTypeService")
public class InstrumentTypeServiceImpl extends BaseServiceImpl<InstrumentTypeEntity, Long> implements IInstrumentTypeService {

	@Autowired
	private IInstrumentTypeDao instrumentTypeDao;

	@Autowired
	private IHouseInstrumentDao houseInstrumentDao;

	@Override
	public BaseDao getBaseMapper() {
		return instrumentTypeDao;
	}
	
	/**
	 * 新增仪类型
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-02 17:17:10
	 */
	@Override
	public ApiResponseResult addInstrumentType(InstrumentTypeAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
		InstrumentTypeEntity entity = new InstrumentTypeEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(idWorker.nextId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setTenantId(RequestUtils.getTenantId());
		entity.setDelFlag(0);
		entity.setCreateDate(new Date());
		//校验仪表类型唯一性
		InstrumentTypeEntity voInstrumentType = new InstrumentTypeEntity();
		voInstrumentType.setInstrumentType(entity.getInstrumentType());
		List<InstrumentTypeEntity> instrumentTypeEntities = instrumentTypeDao.queryInstrumentType(voInstrumentType);
		if(instrumentTypeEntities.size()>0){
			result.setCode(MessageCode.FAIL);
			result.setMsg("你填写的仪表类型已存在，请核实后请重新输入！");
			return result;
		}
		// 新增
		int no = instrumentTypeDao.insert(entity);
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
	 * @version 创建时间：2020-04-02 17:17:10
	 */
	@Override
	public ApiResponseResult updateInstrumentType(InstrumentTypeUpdateVo vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		InstrumentTypeEntity entity = new InstrumentTypeEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		//校验仪表类型唯一性
		InstrumentTypeEntity voInstrumentType = new InstrumentTypeEntity();
		voInstrumentType.setInstrumentType(entity.getInstrumentType());
		voInstrumentType.setId(entity.getId());
		List<InstrumentTypeEntity> instrumentTypeEntities = instrumentTypeDao.queryInstrumentType(voInstrumentType);
		if(instrumentTypeEntities.size()>0){
			result.setCode(MessageCode.FAIL);
			result.setMsg("仪表类型已存在，请核实后请重新输入！");
			return result;
		}
		// 更新
		int no = instrumentTypeDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	 /**
	  * 关联删除
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @Override
	 public ApiResponseResult deleteHouseinstrument(Long id) throws Exception {
		 ApiResponseResult result = new ApiResponseResult();
		 instrumentTypeDao.modifyDelFlag(id);
		 result.setCode(MessageCode.SUCCESS);
		 result.setMsg(MessageCode.SUCCESS_TEXT);
		 result.setData(1);
		 return result;
	 }


	/**
	  * @Desc:仪表类型管理导出
	  * @param voEntity
	  * @Author:邓磊
	  * @UpdateDate:2020/6/2 13:56
	  * @return: 返回
	  */
	@Override
	public void exportInstrumentTypeEntityInfo(InstrumentTypeEntity voEntity) throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		List<InstrumentTypeEntity> instrumentTypeEntities = instrumentTypeDao.queryList(voEntity);
		excelDownload(response,instrumentTypeEntities);
	}
	public void excelDownload(HttpServletResponse response,List<InstrumentTypeEntity>  list) throws Exception {
		//表头数据
		String[] header = {"仪表名称", "计量单位", "备注信息"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("仪表类型管理信息");
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
			row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getInstrumentType()));
			row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getMeasuringUnit()));
			row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getRemark()));
		}
		response.setHeader("content-Type","application/ms-excel");
		String name = "仪表类型管理信息";
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

}
