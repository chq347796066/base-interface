package com.spring.common.util.excel;

import com.spring.common.exception.ServiceException;
import com.spring.common.log.LogUtil;
import com.spring.common.util.id.UUIDFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:操作excel2007(适用 >= 2007 版本) 文件类
*/
public class Excel2007Util
{

	private final static LogUtil LOG = LogUtil.getInstance(Excel2007Util.class);

	/**
	 * 【取Excel指定 sheet 的所有数据，包含header】(这里用一句话描述这个方法的作用)
	 * 
	 * @param sheetIndex
	 * @param isDelFile
	 *            解析excel文件结束后，是否删除文件 true:删除文件；false不删除文件
	 * @param path
	 * @return
	 * @throws ServiceException
	 */
	public static List<String[]> getAllData(int sheetIndex, boolean isDelFile, String path) throws Exception
	{
		List<String[]> arrList = getAllData(sheetIndex, path);
		if (isDelFile)
		{
			deleteFile(path);
		}
		return arrList;
	}

	/**
	 * 【删除文件】(这里用一句话描述这个方法的作用)
	 * 
	 * @param path
	 * @return
	 */
	private static boolean deleteFile(String path)
	{
		boolean isSuccess = false;
		try
		{
			if (null != path)
			{
				File file = new File(path);
				if (file.exists())
				{
					isSuccess = file.delete();
				}
			}
		} catch (Exception ex)
		{
			LOG.error("删除文件失败", ex);
		}
		return isSuccess;
	}

	/**
	 * 【取Excel指定 sheet 的所有数据，包含header】(这里用一句话描述这个方法的作用)
	 * 
	 * @param sheetIndex
	 *            sheet索引
	 * @param path
	 * @return
	 * @throws ServiceException
	 */
	public static List<String[]> getAllData(int sheetIndex, String path) throws Exception
	{
		if (path == null || path.trim().length() == 0)
		{
			throw new Exception("path为空");
		}
		InputStream inp = null;
		ArrayList<String[]> dataList = new ArrayList<String[]>();
		try
		{
			inp = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(inp);
			int columnNum = 0;
			Sheet sheet = wb.getSheetAt(sheetIndex);
			if (sheet.getRow(0) != null)
			{
				columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
			}
			if (columnNum > 0)
			{
				for (Row row : sheet)
				{
					String[] singleRow = new String[columnNum];
					int n = 0;
					for (int i = 0; i < columnNum; i++)
					{
						Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
						switch (cell.getCellType())
						{
						case Cell.CELL_TYPE_BLANK:
							singleRow[n] = "";
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
							break;
						// 数值
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell))
							{
								singleRow[n] = String.valueOf(cell.getDateCellValue());
							} else
							{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								String temp = cell.getStringCellValue();
								// 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
								if (temp.indexOf(".") > -1)
								{
									singleRow[n] = String.valueOf(new Double(temp)).trim();
								} else
								{
									singleRow[n] = temp.trim();
								}
							}
							break;
						case Cell.CELL_TYPE_STRING:
							singleRow[n] = cell.getStringCellValue().trim();
							break;
						case Cell.CELL_TYPE_ERROR:
							singleRow[n] = "";
							break;
						case Cell.CELL_TYPE_FORMULA:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							singleRow[n] = cell.getStringCellValue();
							if (singleRow[n] != null)
							{
								singleRow[n] = singleRow[n].replaceAll("#N/A", "").trim();
							}
							break;
						default:
							singleRow[n] = "";
							break;
						}
						n++;
					}
					if ("".equals(singleRow[0]))
					{
						continue;
					}
					// 如果第一行为空，跳过
					dataList.add(singleRow);
				}
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (InvalidFormatException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (inp != null)
			{
				inp.close();
			}
		}
		return dataList;
	}

	/**
	 * 【返回Excel最大行index值，实际行数要加1】(这里用一句话描述这个方法的作用)
	 * 
	 * @param sheetIndex
	 * @param wb
	 * @return
	 */
	private int getRowNum(int sheetIndex, Workbook wb)
	{
		Sheet sheet = wb.getSheetAt(sheetIndex);
		return sheet.getLastRowNum();
	}

	/**
	 * 【返回数据的列数】(这里用一句话描述这个方法的作用)
	 * 
	 * @param sheetIndex
	 * @param wb
	 * @return
	 */
	private int getColumnNum(int sheetIndex, Workbook wb)
	{
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = sheet.getRow(0);
		if (row != null && row.getLastCellNum() > 0)
		{
			return row.getLastCellNum();
		}
		return 0;
	}

	/**
	 * 【获取某一行数据】(这里用一句话描述这个方法的作用)
	 * 
	 * @param sheetIndex
	 * @param rowIndex
	 * @param wb
	 * @param dataList
	 * @return
	 */
	@SuppressWarnings("unused")
	private String[] getRowData(int sheetIndex, int rowIndex, Workbook wb, ArrayList<String[]> dataList)
	{
		String[] dataArray = null;
		if (rowIndex > this.getColumnNum(sheetIndex, wb))
		{
			return dataArray;
		} else
		{
			dataArray = new String[this.getColumnNum(sheetIndex, wb)];
			return dataList.get(rowIndex);
		}

	}

	/**
	 * 【获取某一列数据】(这里用一句话描述这个方法的作用)
	 * 
	 * @param sheetIndex
	 * @param colIndex
	 * @param wb
	 * @param dataList
	 * @return
	 */
	@SuppressWarnings("unused")
	private String[] getColumnData(int sheetIndex, int colIndex, Workbook wb, ArrayList<String[]> dataList)
	{
		String[] dataArray = null;
		if (colIndex > this.getColumnNum(sheetIndex, wb))
		{
			return dataArray;
		} else
		{
			if (dataList != null && dataList.size() > 0)
			{
				dataArray = new String[this.getRowNum(sheetIndex, wb) + 1];
				int index = 0;
				for (String[] rowData : dataList)
				{
					if (rowData != null)
					{
						dataArray[index] = rowData[colIndex];
						index++;
					}
				}
			}
		}
		return dataArray;

	}


	/**
	 * 【写XLS 支持多 SHEET 暂实现书写excel2003】(这里用一句话描述这个方法的作用)
	 * 
	 * @param sheetList
	 * @param fileModel
	 * @return
	 * @throws ServiceException
	 */
	public static FileModel writeExcel(List<ExcelDataModel> sheetList, FileModel fileModel) throws ServiceException
	{
		// 生成Excel文件的Workbook对象 （1）
		XSSFWorkbook wb = new XSSFWorkbook();

		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		if (null == sheetList || sheetList.isEmpty()) {
			return fileModel;
		}
		for (ExcelDataModel edm : sheetList)
		{
			writeSheet(wb, edm, style);
		}
		// 把WorkBook对象打印到Excel文档
		String filepath = fileModel.getFilepath();
		String filename = fileModel.getFilename();
		FileOutputStream fileOut;
		try
		{
			if (!new File(filepath).exists()) {
				new File(filepath).mkdirs();
			}
			fileOut = new FileOutputStream(filepath + filename);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e)
		{
			throw new ServiceException(e.toString());
		}
		fileModel = new FileModel();
		fileModel.setFilepath(filepath);
		return fileModel;
	}

	/**
	 * 【写 excel 的 sheet 数据】(这里用一句话描述这个方法的作用)
	 * 
	 * @param wb
	 * @param edm
	 * @param style
	 */
	private static void writeSheet(XSSFWorkbook wb, ExcelDataModel edm, XSSFCellStyle style)
	{
		if (null == edm) {
			return;
		}
		// 生成「sheet1」Sheet对象
		XSSFSheet sheet = wb.createSheet(edm.getSheetName());
		// 合并单元格
		List<PoiMergedRegionModel> regionList = edm.getRegionList();
		for (int i = 0; regionList != null && i < regionList.size(); i++)
		{
			PoiMergedRegionModel model = regionList.get(i);
			if (null == model.getStartRowIndex() || null == model.getEndRowIndex() || null == model.getStartCellIndex() || null == model.getEndCellIndex())
			{
				continue;
			}
			sheet.addMergedRegion(new CellRangeAddress(model.getStartRowIndex().intValue(), model.getEndRowIndex().intValue(), model.getStartCellIndex().intValue(), model.getEndCellIndex().intValue()));
		}

		// 生成标题
		XSSFRow row = null;
		XSSFCell cell = null;
		if (null != edm.getTitle() && edm.getTitle().length > 0)
		{
			row = sheet.createRow(0);
			for (int i = 0; i < edm.getTitle().length; i++)
			{
				cell = row.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(edm.getTitle()[i]);
			}
		}
		// 生成数据
		List<String[]> data = edm.getData();
		if (null == data || data.isEmpty() || data.size() == 0) {
			return;
		}
		for (int i = 0; i < data.size(); i++)
		{
			row = sheet.createRow(i + 1);
			String[] textarr = data.get(i);
			for (int j = 0; j < textarr.length; j++)
			{
				// 生成列
				cell = row.createCell(j);
				cell.setCellStyle(style);
				cell.setCellValue(textarr[j]);
			}
		}
	}

	/**
	 * 【写xls文件，暂实现书写excel2003】(这里用一句话描述这个方法的作用)
	 * 
	 * @param textList
	 * @param fileModel
	 * @return
	 * @throws ServiceException
	 */
	public static FileModel writeXls(List<String[]> textList, FileModel fileModel) throws Exception
	{
		return writeXls(textList, fileModel, null);
	}

	/**
	 * 【写xls文件，暂实现书写excel2003】(这里用一句话描述这个方法的作用)
	 * 
	 * @param textList
	 * @param fileModel
	 * @param regionList
	 * @return
	 * @throws ServiceException
	 */
	public static FileModel writeXls(List<String[]> textList, FileModel fileModel, List<PoiMergedRegionModel> regionList) throws Exception
	{
		return writeXls(textList, fileModel, regionList, null);
	}

	/**
	 * 【写xls文件，暂实现书写excel2003】(这里用一句话描述这个方法的作用)
	 * 
	 * @param textList
	 * @param fileModel
	 * @param regionList
	 * @param constraintlList
	 * @return
	 * @throws ServiceException
	 */
	public static FileModel writeXls(List<String[]> textList, FileModel fileModel, List<PoiMergedRegionModel> regionList, List<PoiConstraintModel> constraintlList) throws Exception
	{
		return writeXls(textList, fileModel, regionList, constraintlList, null);
	}

	/**
	 * 写xls文件，暂实现书写excel2003
	 * 
	 * @param textList
	 *            文本内容集合
	 * @param fileModel
	 *            输出文件模型
	 * @param regionList
	 *            合并范围集合
	 * @param constraintlList
	 *            数据有效性集合
	 * @param columnHiddenModelList
	 *            数据列隐藏集合
	 * @return
	 * @throws ServiceException
	 *             FileModel
	 * @author xsolr
	 * @date 2013-9-12 下午02:03:53
	 * @version V1.0
	 */
	public static FileModel writeXls(List<String[]> textList, FileModel fileModel, List<PoiMergedRegionModel> regionList, List<PoiConstraintModel> constraintlList, List<Integer> columnHiddenModelList) throws Exception
	{
		if (textList == null || textList.size() == 0)
		{
			throw new Exception("文本集合参数无效");
		}
		// 生成Excel文件的Workbook对象 （1）
		XSSFWorkbook wb = new XSSFWorkbook();

		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		// 生成「sheet1」Sheet对象 （2）
		XSSFSheet sheet = wb.createSheet("sheet");

		// 合并单元格
		for (int i = 0; regionList != null && i < regionList.size(); i++)
		{
			PoiMergedRegionModel model = regionList.get(i);
			if (null == model.getStartRowIndex() || null == model.getEndRowIndex() || null == model.getStartCellIndex() || null == model.getEndCellIndex())
			{
				continue;
			}
			sheet.addMergedRegion(new CellRangeAddress(model.getStartRowIndex().intValue(), model.getEndRowIndex().intValue(), model.getStartCellIndex().intValue(), model.getEndCellIndex().intValue()));
		}

		// 数据有效性
		for (int i = 0; constraintlList != null && i < constraintlList.size(); i++)
		{
			PoiConstraintModel cm = constraintlList.get(i);
			if (null == cm.getStartRowIndex() || null == cm.getEndRowIndex() || null == cm.getStartCellIndex() || null == cm.getEndCellIndex() || null == cm.getConstraintArr() || cm.getConstraintArr().length == 0)
			{
				continue;
			}
		}

		// 数据列隐藏
		for (int i = 0; columnHiddenModelList != null && i < columnHiddenModelList.size(); i++)
		{
			if (columnHiddenModelList.get(i) != null) {
				sheet.setColumnHidden(columnHiddenModelList.get(i).intValue(), true);
			}
		}

		for (int i = 0; i < textList.size(); i++)
		{
			// 生成行（3）
			XSSFRow row = sheet.createRow(i);
			String[] textarr = textList.get(i);
			for (int j = 0; j < textarr.length; j++)
			{
				// 生成列
				XSSFCell cell = row.createCell(j);
				cell.setCellStyle(style);
				cell.setCellValue(textarr[j]);
			}
		}
		// 把WorkBook对象打印到Excel文档
		String filename = null;
		if (fileModel != null && fileModel.getFilename() != null)
		{
			filename = fileModel.getFilename();
		} else
		{
			filename = UUIDFactory.createId();
		}
		filename = filename + ".xls";
		String filepath = SystemUtil.getBaseValueByBaseKey("flieUploadPath") + filename;
		FileOutputStream fileOut = new FileOutputStream(filepath);
		wb.write(fileOut);
		fileOut.close();
		fileModel = new FileModel();
		fileModel.setFilename(filename);
		fileModel.setFilepath(filepath);
		return fileModel;
	}

	/**
	 * 导入excel
	 * 
	 * @param filePath
	 * @param num
	 * @return
	 */
	public static List<List<String[]>> impexc(String filePath, int num)
	{
		List<List<String[]>> excList = new ArrayList<List<String[]>>();
		try
		{
			// 创建对Excel工作簿文件的引用
			XSSFWorkbook wookbook = new XSSFWorkbook(new FileInputStream(filePath));
			// 在Excel文档中，第一张工作表的缺省索引是0
			int sheetNum = wookbook.getNumberOfSheets();
			for (int i = 0; i < sheetNum; i++)
			{
				List<String[]> list = importSheet(wookbook.getSheetAt(i), num);
				excList.add(list);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return excList;
	}

	/**
	 * 单张表格数据 知道有多少列
	 * 
	 * @param sheet
	 * @param num
	 * @return
	 */
	private static List<String[]> importSheet(XSSFSheet sheet, int num)
	{
		List<String[]> list = new ArrayList<String[]>();
		int rows = sheet.getPhysicalNumberOfRows();
		XSSFRow row;// 行
		XSSFCell cell;// 列
		String[] val;
		for (int i = 0; i < rows; i++)
		{
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			// 获取到Excel文件中的所有的列
			val = new String[num];
			for (int j = 0; j < num; j++)
			{
				val[j] = "";
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				switch (cell.getCellType())
				{

				case XSSFCell.CELL_TYPE_FORMULA:
					break;
				case XSSFCell.CELL_TYPE_STRING:
					val[j] = cell.getStringCellValue();
					break;
				default:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					val[j] = cell.getStringCellValue();
					break;
				}
			}
			list.add(val);
		}
		return list;
	}

	/**
	 * 导入Excel 工具
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<List<String[]>> impexc(String filePath)
	{
		List<List<String[]>> excList = new ArrayList<List<String[]>>();
		try
		{
			// 创建对Excel工作簿文件的引用
			XSSFWorkbook wookbook = new XSSFWorkbook(new FileInputStream(filePath));
			// 在Excel文档中，第一张工作表的缺省索引是0
			// 其语句为：XSSFSheet sheet = wookbook.getSheetAt(0);­
			// 根据名字 XSSFSheet sheet = wookbook.getSheet("Sheet1");­
			int sheetNum = wookbook.getNumberOfSheets();
			for (int i = 0; i < sheetNum; i++)
			{
				List<String[]> list = importSheet(wookbook.getSheetAt(i));
				excList.add(list);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return excList;
	}

	/**
	 * 单张表格数据
	 * 
	 * @param sheet
	 * @return
	 */
	private static List<String[]> importSheet(XSSFSheet sheet)
	{
		List<String[]> list = new ArrayList<String[]>();
		// 获取到Excel文件中的所有行数­
		int rows = sheet.getPhysicalNumberOfRows();
		XSSFRow row;// 行
		XSSFCell cell;// 列
		// 遍历行­
		for (int i = 0; i < rows; i++)
		{
			row = sheet.getRow(i);
			// 行不为空
			if (row != null)
			{
				// 获取到Excel文件中的所有的列
				int cells = row.getLastCellNum();
				String[] val = new String[cells];
				// 遍历列
				for (int j = 0; j < cells; j++)
				{
					val[j] = "";
					cell = row.getCell(j);
					if (cell != null)
					{
						switch (cell.getCellType())
						{
						case XSSFCell.CELL_TYPE_FORMULA:
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							val[j] = String.valueOf(cell.getNumericCellValue());
							break;
						case XSSFCell.CELL_TYPE_STRING:
							val[j] = cell.getStringCellValue();
							break;
						default:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							val[j] = cell.getStringCellValue();
							break;
						}
					}
				}
				list.add(val);
			}
		}
		return list;
	}
	
	public static void main(String[] args)
	{
		List<List<String[]>> list = Excel2007Util.impexc("d:/aa.xlsx");
		for (List<String[]> list2 : list)
		{
			for (String[] strings : list2)
			{
				System.out.println();
				System.out.println("--------------------------");
				System.out.println();
				for (String string : strings)
				{
					System.out.print(string + "|");
				}
			}
		}
	}
}