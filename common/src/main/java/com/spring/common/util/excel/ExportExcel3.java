package com.spring.common.util.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:ExportExcel2
*/
public class ExportExcel3 {

	private final String title;

	private final String[] rowName;

	private List<Object[]> dataList = new ArrayList<Object[]>();


	// 传入要导出的数据
	public ExportExcel3(String title, String[] rowName, List<Object[]> dataList) {
		this.title = title;
		this.rowName = rowName;
		this.dataList = dataList;
	}

	/**

	 *   用poi导出Excel文件的静态方法

	 * @param list                    数据：只能是List<Map<String, Object>>类型

	 * @param sheetname        Excel的sheet名字

	 * @param filepath             保存文件的地址

	 * @throws IOException

	 */

	public static void exportExcelMy(List<LinkedHashMap<String, Object>> list, String sheetname, String fileName) throws IOException {

		//新建工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建Excel的sheet
		HSSFSheet sheet=workbook.createSheet(sheetname);
		//从list任意一个Map对象里获取标题（字段名或属性名）放到sheet的第一行上，若第一条记录某字段值没有，则会没有该字段
		Map<String, Object> map=list.get(0);
		int num=0;
		HSSFRow first=sheet.createRow(0);
		for(String key:map.keySet()) {

			first.createCell(num).setCellValue(key);

			num++;

		}
		//从list取第一行到最后一行的内容并放到对应的Excel里，若记录里某字段值没有会有问题
		int rownum=1;

		for(Map<String, Object> data:list) {

			HSSFRow row=sheet.createRow(rownum);

			int n=0;

			for(String key:data.keySet()) {

				row.createCell(n).setCellValue(data.get(key).toString());

				n++;

			}

			rownum++;

		}



		//1.通过IO流把数据写到文件上：只能写到服务器端

              FileOutputStream out=new FileOutputStream(fileName);

              workbook.write(out);

              out.close();



		//2.响应到客户端：可以下载到客户端（两个选一个）

		/*try {

			this.setResponseHeader(response, fileName);

			OutputStream os = response.getOutputStream();

			workbook.write(os);

			os.flush();

			os.close();

		} catch (Exception e) {

			e.printStackTrace();
		}*/
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
					// }
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
				String value = mapItem.get(s).toString();
				row.add(value);
			}
			// list转array
			Object[] body = new Object[row.size()];

			row.toArray(body);

			list.add(body);
		}
		return list;
	}


}