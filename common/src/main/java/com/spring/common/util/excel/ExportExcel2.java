package com.spring.common.util.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:ExportExcel2
*/
public class ExportExcel2 {

	private final String title;

	private final String[] rowName;

	private List<Object[]> dataList = new ArrayList<Object[]>();

	private final HttpServletResponse response;

	//传入要导出的数据
	public ExportExcel2(String title, String[] rowName, List<Object[]> dataList, HttpServletResponse response) {
		this.title = title;
		this.rowName = rowName;
		this.dataList = dataList;
		this.response = response;
	}

	// 导出数据
	public void exportData() {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(title);
			// 产生表格标题行
			HSSFRow rowm = sheet.createRow(0);
			HSSFCell cellTiltle = rowm.createCell(0);

			// sheet样式定义
			HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);

			sheet.setDefaultColumnWidth((short) 15);
			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);
			// 生成并设置另一个样式
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 生成另一个字体
			HSSFFont font2 = workbook.createFont();
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			// 把字体应用到当前的样式
			style2.setFont(font2);

			/**
			 * 参数说明 从0开始 第一行 第一列 都是从角标0开始 行 列 行列 (0,0,0,5) 合并第一行 第一列 到第一行 第六列
			 * 起始行，起始列，结束行，结束列
			 * 
			 * new Region() 这个方法使过时的
			 */
			// 合并第一行的所有列
			sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) (rowName.length - 1)));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue(title);

			int columnNum = rowName.length;
			HSSFRow rowRowName = sheet.createRow(1);
			HSSFCellStyle cells = workbook.createCellStyle();
			cells.setBottomBorderColor(HSSFColor.BLACK.index);
			rowRowName.setRowStyle(cells);
			sheet.createFreezePane(0, 2, 0, 2);

			// 循环 将列名放进去
			for (int i = 0; i < columnNum; i++) {
				HSSFCell cellRowName = rowRowName.createCell(i);
				cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);
				HSSFRichTextString text = new HSSFRichTextString(rowName[i]);
				cellRowName.setCellValue(text);
				cellRowName.setCellStyle(style);
			}

			// 将查询到的数据设置到对应的单元格中
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < obj.length; j++) {
					HSSFCell cell = null;
					cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
					if (!"".equals(obj[j]) && obj[j] != null) {
						cell.setCellValue(obj[j].toString());
					} else {
						cell.setCellValue("  ");
					}
					cell.setCellStyle(style2);

				}
			}
			if (workbook != null) {
				try {

					// excel 表文件名
					String fileName = title + ".xls";
					String fileName11 = new String((fileName).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
					String headStr = "attachment; filename=\"" + fileName11 + "\"";
					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition", headStr);
					OutputStream out = response.getOutputStream();
					workbook.write(out);
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 导出数据
	public void exportDataToFile(String fileName) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(title);
			// 产生表格标题行
			HSSFRow rowm = sheet.createRow(0);
			HSSFCell cellTiltle = rowm.createCell(0);

			// sheet样式定义
			HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);

			sheet.setDefaultColumnWidth((short) 15);
			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);
			// 生成并设置另一个样式
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 生成另一个字体
			HSSFFont font2 = workbook.createFont();
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			// 把字体应用到当前的样式
			style2.setFont(font2);

			/**
			 * 参数说明 从0开始 第一行 第一列 都是从角标0开始 行 列 行列 (0,0,0,5) 合并第一行 第一列 到第一行 第六列
			 * 起始行，起始列，结束行，结束列
			 *
			 * new Region() 这个方法使过时的
			 */
			// 合并第一行的所有列
			sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) (rowName.length - 1)));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue(title);

			int columnNum = rowName.length;
			HSSFRow rowRowName = sheet.createRow(1);
			HSSFCellStyle cells = workbook.createCellStyle();
			cells.setBottomBorderColor(HSSFColor.BLACK.index);
			rowRowName.setRowStyle(cells);
			sheet.createFreezePane(0, 2, 0, 2);

			// 循环 将列名放进去
			for (int i = 0; i < columnNum; i++) {
				HSSFCell cellRowName = rowRowName.createCell(i);
				cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);
				HSSFRichTextString text = new HSSFRichTextString(rowName[i]);
				cellRowName.setCellValue(text);
				cellRowName.setCellStyle(style);
			}

			// 将查询到的数据设置到对应的单元格中
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HSSFRow row = sheet.createRow(i + 2);
				for (int j = 0; j < obj.length; j++) {
					HSSFCell cell = null;
					cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
					if (!"".equals(obj[j]) && obj[j] != null) {
						cell.setCellValue(obj[j].toString());
					} else {
						cell.setCellValue("  ");
					}
					cell.setCellStyle(style2);

				}
			}
			if (workbook != null) {
				try {
					FileOutputStream out = new FileOutputStream(fileName);
					workbook.write(out);
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;
	}

	/**
	 * @Author Liu Pinghui
	 * @Description 获取组装数据
	 * @Date 2019/8/21 11:40
	 * @Param
	 * @return
	 **/
	public static List<Object[]> getDataList(List<String> titleList, List<LinkedHashMap<String, Object>> map){
		// 组装数据
		List<Object[]> list = new ArrayList<Object[]>();
		for (Map<String, Object> mapItem : map) {
			List<String> row = new ArrayList<String>();
			for (String s : titleList) {
				if(mapItem!=null){
					String value = mapItem.get(s).toString();
					row.add(value);
				}
			}
			// list转array
			Object[] body = new Object[row.size()];

			row.toArray(body);

			list.add(body);
		}
		return list;
	}
}