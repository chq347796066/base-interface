package com.spring.common.importExcel.util;

import com.spring.common.importExcel.exception.InvalidExcelTemplateException;
import com.spring.common.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;
import com.spring.common.exception.ServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取Excel文件工具类
 * 
 * @author Michael
 * 
 */
public class ExcelUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 创建Apache POI WorkBook对象
	 * 
	 * @param input
	 *            输入流
	 * @return 由excel文件对应创建的WorkBook对象
	 */
	private static Workbook getExcelWorkBook(InputStream input) throws ServiceException {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(input);
			return workbook;
		} catch (IOException e) {
			LOGGER.error("create Workbook failed", e);
			throw new ServiceException("创建对象失败", e);
		} catch (InvalidFormatException | IllegalArgumentException iae) {
			LOGGER.error("excel文件内容错误", iae);
			throw new ServiceException("excel文件内容错误");
		} finally {
			// 2018-07-26 Cai 注掉此段
			// 关闭流请到打开流的代码段中。
			// IOUtils.closeQuietly(input);
		}
	}

	/**
	 * 读取excel文件数据至Map集合中，键值对形式：<sheet名称, List<Object[]>>
	 * 
	 * @param input
	 *            输入流
	 * @return 返回诸如如下形式的Map集合：<br>
	 *         {<br>
	 *         <"第1个sheet页名称", {["row1col1", "row1col2", "row1col3"],
	 *         ["row2col1", "row2col2", "row2col3"]}>,<br>
	 *         <"第2个sheet页名称", {["row1col1", "row1col2", "row1col3"]}><br>
	 *         }
	 */
	public static Map<String, List<Object[]>> readToMap(InputStream input) throws ServiceException {
		Map<String, List<Object[]>> map = new HashMap<String, List<Object[]>>();
		Workbook workbook = getExcelWorkBook(input);
		int sheetNumber = workbook.getNumberOfSheets();
		List<Object[]> list = null;
		for (int i = 0; i < sheetNumber; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (null == sheet) {
				continue;
			}
			list = readCurrSheetToList(sheet);
			if (CollectionUtils.isNotEmpty(list))
				map.put(sheet.getSheetName().trim(), list);
		}
		return map;
	}


	/**
	 * 读取excel文件数据至Map集合中，键值对形式：<sheet名称, List<Object[]>>
	 *
	 * @param input
	 *            输入流
	 * @return 返回诸如如下形式的Map集合：<br>
	 *         {<br>
	 *         <"第1个sheet页名称", {["row1col1", "row1col2", "row1col3"],
	 *         ["row2col1", "row2col2", "row2col3"]}>,<br>
	 *         <"第2个sheet页名称", {["row1col1", "row1col2", "row1col3"]}><br>
	 *         }
	 */
	public static Map<String, List<Object[]>> readToMapForBank(InputStream input) throws ServiceException {
		Map<String, List<Object[]>> map = new HashMap<String, List<Object[]>>();
		Workbook workbook = getExcelWorkBook(input);
		int sheetNumber = workbook.getNumberOfSheets();
		List<Object[]> list = null;
		for (int i = 0; i < sheetNumber; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (null == sheet) {
				continue;
			}
			list = readCurrSheetToListForBank(sheet);
			if (CollectionUtils.isNotEmpty(list))
				map.put(sheet.getSheetName().trim(), list);
		}
		return map;
	}

	/**
	 * 读取指定sheet excel文件数据至List集合中，键值对形式：<sheet名称, List<Object[]>>
	 * 
	 * @param input
	 *            输入流
	 * @return 返回诸如如下形式的list集合：<br>
	 *         {<br>
	 *         <"{["row1col1", "row1col2", "row1col3"],
	 */
	public static List<Object[]> readCurrSheetToList(Sheet sheet) {

		List<Object[]> list = null;

		list = new ArrayList<Object[]>();

		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		for (int j = firstRowNum; j <= lastRowNum; j++) {
			Row row = sheet.getRow(j);
			if (row == null)
				continue;

			int firstCellNum = row.getFirstCellNum();
			int lastCellNum = row.getLastCellNum();

			int length = lastCellNum - firstCellNum;
			Object[] cellArr = new String[length];

			for (int k = 0; k < length; k++) {
				Cell cell = row.getCell(k);
				cellArr[k] = getCellValue(cell);
			}

			// 如果读取的行的所有单元格数据为空
			if (ArrayUtil.isAllElemEmpty(cellArr))
				continue;
			list.add(cellArr);
		}
		return list;
	}




	/**
	 * 读取指定sheet excel文件数据至List集合中，键值对形式：<sheet名称, List<Object[]>>
	 *
	 * @param input
	 *            输入流
	 * @return 返回诸如如下形式的list集合：<br>
	 *         {<br>
	 *         <"{["row1col1", "row1col2", "row1col3"],
	 */
	public static List<Object[]> readCurrSheetToListForBank(Sheet sheet) {

		List<Object[]> list =  new ArrayList<Object[]>();

		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();

		int length = dealFirstRow(sheet,firstRowNum,list);

		for (int j = firstRowNum+1; j <= lastRowNum; j++) {
			Row row = sheet.getRow(j);
			if (row == null)
				continue;

			Object[] cellArr = new String[length];

			for (int k = 0; k < length; k++) {
				Cell cell = row.getCell(k);
				cellArr[k] = getCellValue(cell);
			}

			// 如果读取的行的所有单元格数据为空
			if (ArrayUtil.isAllElemEmpty(cellArr))
				continue;
			list.add(cellArr);
		}
		return list;
	}

	private static int dealFirstRow(Sheet sheet, int firstRowNum, List<Object[]> list){
		for(int j= firstRowNum;j<firstRowNum+1;j++){
			Row row = sheet.getRow(j);
			if (row == null){
				continue;
			}

			int firstCellNum = row.getFirstCellNum();
			int lastCellNum = row.getLastCellNum();

			int length = lastCellNum - firstCellNum;
			Object[] cellArr = new String[length];

			for (int k = 0; k < length; k++) {
				Cell cell = row.getCell(k);
				cellArr[k] = getCellValue(cell);
			}

			// 如果读取的行的所有单元格数据为空
			if (ArrayUtil.isAllElemEmpty(cellArr)){
				continue;
			}
			list.add(cellArr);
			return length;
		}
		return 0;

	}



	/**
	 * 读取excel文件数据至Map集合中，键值对形式：<sheet名称, List<Object[]>>
	 * 
	 * @param input
	 *            输入流
	 * @param sheetIndex指定当前sheet的下标
	 * @return 返回诸如如下形式的Map集合：<br>
	 *         {<br>
	 *         <"当前sheet页名称", {["row1col1", "row1col2", "row1col3"],
	 *         ["row2col1", "row2col2", "row2col3"]}>,<br>
	 *         }
	 */
	public static Map<String, List<Object[]>> readSheetIndexToList(InputStream input, int sheetIndex) throws ServiceException {
		Map<String, List<Object[]>> map = new HashMap<String, List<Object[]>>();
		Workbook workbook = getExcelWorkBook(input);
		int sheetNumber = workbook.getNumberOfSheets();
		if (sheetNumber < sheetIndex + 1) {
			throw new InvalidExcelTemplateException();
		}
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		if (null == sheet) {
			throw new InvalidExcelTemplateException();
		}
		List<Object[]> list = readCurrSheetToList(sheet);

		if (!CollectionUtils.isEmpty(list)) {
			map.put(sheet.getSheetName().trim(), list);
		}
		return map;
	}

	/**
	 * 读取excel单元格的值
	 * 
	 * @param cell
	 *            单元格对象
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static Object getCellValue(Cell cell) {
		if (cell == null)
			return "";
		Object cellValue = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			cellValue = "";
			break;
		case Cell.CELL_TYPE_ERROR:
			cellValue = Byte.toString(cell.getErrorCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString();
			// 如果是日期格式的文本，则统一转换为"yyyyMMdd"格式
			cellValue = com.spring.common.importExcel.util.DateUtil.converFormat(ObjectUtils.toString(cellValue));
			break;
		/** 在excel中日期也是数字,在此要进行判断 */
		case Cell.CELL_TYPE_NUMERIC:
			Double d = cell.getNumericCellValue();
			if (DateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
				cellValue = fmt.format(HSSFDateUtil.getJavaDate(d));
			} else {
				cellValue = d.toString();
				if (StringUtils.isNotEmpty(d.toString())) {
					// 取消科学技术法,手机号身份证号会出现问题
					if (d.toString().endsWith(".0")) {
						DecimalFormat df = new DecimalFormat("#");
						cellValue = df.format(cell.getNumericCellValue());
					} else {
						DecimalFormat df = new DecimalFormat("###0.00");// 保留两位小数且不用科学计数法
						cellValue = df.format(cell.getNumericCellValue());
						if (String.valueOf(cellValue).endsWith(".00")) {
							cellValue = String.valueOf(cellValue).substring(0, String.valueOf(cellValue).length() - 3);
						}
					}

				}
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = Boolean.toString(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			cellValue = cell.getCellFormula();
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}

}
