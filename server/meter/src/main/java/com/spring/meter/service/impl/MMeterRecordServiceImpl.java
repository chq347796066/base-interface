package com.spring.meter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.BuildEntity;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.entity.meter.MMeterRecordEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.meter.*;
import com.spring.common.constants.MessageCode;
import com.spring.common.constants.Meter;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.SnowflakeIdWorker;
import com.spring.common.util.id.UUIDFactory;
import com.spring.meter.dao.IMMeterRecordDao;
import com.spring.meter.service.IMMeterRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-10-28 14:30:45
 * @Desc类说明: 房屋抄记录业务接口实现类
 */
 @Slf4j
@Service("mMeterRecordService")
public class MMeterRecordServiceImpl extends BaseServiceImpl<MMeterRecordEntity, Long> implements IMMeterRecordService {
	
	@Autowired
	private IMMeterRecordDao mMeterRecordDao;

	@Autowired
	private BaseInfoFeignClient baseInfoFeignClient;

	@Override
	public BaseDao getBaseMapper() {
		return mMeterRecordDao;
	}
	
	/**
	 * 新增房屋抄记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-10-28 14:30:45
	 */
	@Override
	public ApiResponseResult addMMeterRecord(MMeterRecordAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		MMeterRecordEntity entity = new MMeterRecordEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(SnowflakeIdWorker.generateId());
		entity.setDelFlag(0);
		Double dousageValue = entity.getCurrentData().doubleValue() - entity.getLastData().doubleValue();
		entity.setDosage(BigDecimal.valueOf(dousageValue));

		entity.setReadCheck(Meter.READ_CHECK.YES);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		// 新增
		int no = mMeterRecordDao.insert(entity);
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
	 * 更新房屋抄记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-10-28 14:30:45
	 */
	@Override
	public ApiResponseResult updateMMeterRecord(MMeterRecordUpdateVo vo) throws Exception {
		MMeterRecordEntity entity = new MMeterRecordEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setTenantId(RequestUtils.getTenantId());
		entity.setModifyDate(new Date());

		Double dousageValue = entity.getCurrentData().doubleValue() - entity.getLastData().doubleValue();
		entity.setDosage(BigDecimal.valueOf(dousageValue));

		entity.setReadCheck(Meter.READ_CHECK.YES);
		// 更新
		int no = mMeterRecordDao.updateById(entity);
		return createSuccessResult(null);
//		if (no > 0) {
//
//		}
//		return createFailResult();
	}

	 @Override
	 public ApiResponseResult deleteMeterRecord(Long id) throws Exception {
		 MMeterRecordEntity entity = new MMeterRecordEntity();
		 entity.setId(id);
		 entity.setDelFlag(1);

		 // 更新
		 int no = mMeterRecordDao.updateById(entity);
		 return createSuccessResult(null);
//		 if (no > 0) {
//
//		 }
//		 return createFailResult();
	 }

	 @Override
	 public ApiResponseResult batchDeleteMeterRecord(MeterIds ids) throws Exception {
		 int no = mMeterRecordDao.batchDeleteMeterRecord(ids.getIds());
		 return createSuccessResult(null);

	 }

	@Override
	public ApiResponseResult queryStatisPage(RequestPageVO<MMeterRecordStatisParam> requestPageVO) throws Exception {
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		List<MMeterRecordStatisVo> list = mMeterRecordDao.queryStatisList(requestPageVO.getEntity());
		for (MMeterRecordStatisVo statisVo : list) {
			BuildEntity buildEntity = new BuildEntity();
			buildEntity.setId(statisVo.getBuildId());

			BuildEntity buildInfo = baseInfoFeignClient.queryBuildInfo(buildEntity);
			if (null != buildInfo) {
				statisVo.setBuildName(buildInfo.getBuildName());
				statisVo.setCommunityName(buildInfo.getCommunityName());
			}
		}


		PageInfo<MMeterRecordStatisVo> pageInfo = new PageInfo<MMeterRecordStatisVo>(list);
		return createSuccessResult(pageInfo);
	}

	@Override
	public ApiResponseResult queryStatisDetailPage(RequestPageVO<MMeterRecordStatisParam> requestPageVO) throws Exception {
		PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		List<MMeterRecordEntity> list = mMeterRecordDao.queryStatisDetailList(requestPageVO.getEntity());
		for (MMeterRecordEntity meterRecordEntity : list) {
			HouseEntity houseEntity = new HouseEntity();
			houseEntity.setId(meterRecordEntity.getHouseId());
			HouseEntity houseInfo = baseInfoFeignClient.queryHouseInfo(houseEntity);

			if (houseInfo != null) {
				meterRecordEntity.setBuildName(houseInfo.getBuildName());
				meterRecordEntity.setCommunityName(houseInfo.getCommunityName());
				meterRecordEntity.setHouseCode(houseInfo.getHouseCode());
			}

		}

		PageInfo<MMeterRecordEntity> pageInfo = new PageInfo<MMeterRecordEntity>(list);
		return createSuccessResult(pageInfo);
	}

	@Override
	public ApiResponseResult queryStatisByCommunity(MMeterRecordStatisParam paramVo) throws Exception {
		MMeterRecordStatisVo statisVo = mMeterRecordDao.queryStatisByCommunity(paramVo);
		return createSuccessResult(statisVo);
	}

	@Override
	public void exportMeterStatis(MMeterRecordStatisParam vo) throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletRequestAttributes.getResponse();

		List<MMeterRecordStatisVo> list = mMeterRecordDao.queryStatisList(vo);
		for (MMeterRecordStatisVo statisVo : list) {
			BuildEntity buildEntity = new BuildEntity();
			buildEntity.setId(statisVo.getBuildId());

			BuildEntity buildInfo = baseInfoFeignClient.queryBuildInfo(buildEntity);
			if (null != buildInfo) {
				statisVo.setBuildName(buildInfo.getBuildName());
				statisVo.setCommunityName(buildInfo.getCommunityName());
			}
		}
		try{
			excelDownloadMeterStatis(response,list);
		}catch (Exception e){
			log.error("抄表统计列表导出exportRecordInfo异常"+e);
			throw e;

		}
	}

	private void excelDownloadMeterStatis(HttpServletResponse response, List<MMeterRecordStatisVo> list) throws Exception {
		String[] header = {"小区名称", "楼栋", "仪表总户数", "仪表数", "已抄表", "未抄表", "抄表占比"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("仪表抄表信息");
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
			row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getCommunityName()));
			row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getBuildName()));

			row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getHouseNum()));
			row1.createCell(3).setCellValue(new HSSFRichTextString(list.get(i).getMeterNum()));
			row1.createCell(4).setCellValue(new HSSFRichTextString(list.get(i).getReadNum()));
			row1.createCell(5).setCellValue(new HSSFRichTextString(list.get(i).getNotReadNum()));

			Double readNum = Double.valueOf(list.get(i).getReadNum());
			Double notReadNum = Double.valueOf(list.get(i).getNotReadNum());
			Double readPercent = BigDecimal.valueOf(readNum/(readNum+notReadNum)*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();;

			row1.createCell(6).setCellValue(new HSSFRichTextString(String.valueOf(readPercent) + "%"));


		}
		String name = "仪表抄表统计信息";
		response.setHeader("content-Type","application/ms-excel");
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

	@Override
	public void exportMeterRecord(MMeterRecordEntity vo) throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletRequestAttributes.getResponse();

		List<MMeterRecordEntity> list = mMeterRecordDao.queryList(vo);
		for (MMeterRecordEntity meterRecordEntity : list) {
			HouseEntity houseEntity = new HouseEntity();
			houseEntity.setId(meterRecordEntity.getHouseId());
			HouseEntity houseInfo = baseInfoFeignClient.queryHouseInfo(houseEntity);

			if (houseInfo != null) {
				meterRecordEntity.setBuildName(houseInfo.getBuildName());
				meterRecordEntity.setCommunityName(houseInfo.getCommunityName());
				meterRecordEntity.setHouseCode(houseInfo.getHouseCode());
			}
		}

		try{
			excelDownloadMeterRecord(response,list);
		}catch (Exception e){
			log.error("抄表记录列表导出exportRecordInfo异常"+e);
			throw e;

		}
	}

	@Override
	public void exportMeterStatisDetail(MMeterRecordStatisParam vo) throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletRequestAttributes.getResponse();

		List<MMeterRecordEntity> list = mMeterRecordDao.queryStatisDetailList(vo);
		for (MMeterRecordEntity meterRecordEntity : list) {
			HouseEntity houseEntity = new HouseEntity();
			houseEntity.setId(meterRecordEntity.getHouseId());
			HouseEntity houseInfo = baseInfoFeignClient.queryHouseInfo(houseEntity);

			if (houseInfo != null) {
				meterRecordEntity.setBuildName(houseInfo.getBuildName());
				meterRecordEntity.setCommunityName(houseInfo.getCommunityName());
				meterRecordEntity.setHouseCode(houseInfo.getHouseCode());
			}

		}

		try{
			excelDownloadMeterStatisDeail(response,list);
		}catch (Exception e){
			log.error("抄表统计明细列表导出exportRecordInfo异常"+e);
			throw e;

		}
	}

	private void excelDownloadMeterStatisDeail(HttpServletResponse response, List<MMeterRecordEntity> list) throws Exception {
		String[] header = {"小区名称", "楼栋","房屋编码","仪表名称","抄表状态","上期抄表时间","上期抄表读数","本期抄表时间","本期抄表读数","用量"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("仪表抄表统计明细信息");
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
			row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getCommunityName()));
			row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getBuildName()));
			row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getHouseCode()));
			row1.createCell(3).setCellValue(new HSSFRichTextString(list.get(i).getMeterTypeName()));
			String readCheck = list.get(i).getReadCheck() == 0 ? "未抄表" : "已抄表";
			row1.createCell(4).setCellValue(new HSSFRichTextString(readCheck));

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			row1.createCell(5).setCellValue(new HSSFRichTextString(formatter.format(list.get(i).getLastReadDate())));
			row1.createCell(6).setCellValue(new HSSFRichTextString(String.valueOf(list.get(i).getLastData())));
			row1.createCell(7).setCellValue(new HSSFRichTextString(formatter.format(list.get(i).getCurrentReadDate())));
			row1.createCell(8).setCellValue(new HSSFRichTextString(String.valueOf(list.get(i).getCurrentData())));
			row1.createCell(9).setCellValue(new HSSFRichTextString(String.valueOf(list.get(i).getDosage())));


		}
		String name = "仪表抄表统计明细信息";
		response.setHeader("content-Type","application/ms-excel");
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

	private void excelDownloadMeterRecord(HttpServletResponse response, List<MMeterRecordEntity> list) throws Exception {
		String[] header = {"小区名称", "仪表编码", "房屋编码","楼栋","单元","房号","仪表类型","上期读数","本期读数","用量","初始抄表日期","本期抄表日期"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("仪表抄表信息");
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
			row1.createCell(0).setCellValue(new HSSFRichTextString(list.get(i).getCommunityName()));
			row1.createCell(1).setCellValue(new HSSFRichTextString(list.get(i).getMeterNum()));
			row1.createCell(2).setCellValue(new HSSFRichTextString(list.get(i).getHouseCode()));
			row1.createCell(3).setCellValue(new HSSFRichTextString(list.get(i).getBuildName()));
			row1.createCell(4).setCellValue(new HSSFRichTextString(list.get(i).getCellName()));
			row1.createCell(5).setCellValue(new HSSFRichTextString(list.get(i).getHouseNo()));
			row1.createCell(6).setCellValue(new HSSFRichTextString(list.get(i).getMeterTypeName()));
			row1.createCell(7).setCellValue(new HSSFRichTextString(String.valueOf(list.get(i).getLastData())));
			row1.createCell(8).setCellValue(new HSSFRichTextString(String.valueOf(list.get(i).getCurrentData())));
			row1.createCell(9).setCellValue(new HSSFRichTextString(String.valueOf(list.get(i).getDosage())));

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			row1.createCell(10).setCellValue(new HSSFRichTextString(formatter.format(list.get(i).getLastReadDate())));
			row1.createCell(11).setCellValue(new HSSFRichTextString(formatter.format(list.get(i).getCurrentReadDate())));


		}
		String name = "仪表抄表信息";
		response.setHeader("content-Type","application/ms-excel");
		String fileName = URLEncoder.encode(name,"utf-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename="+fileName + ".xls");
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}



}
