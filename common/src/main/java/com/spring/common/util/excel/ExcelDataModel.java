package com.spring.common.util.excel;

import java.util.List;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:Excel 模型
*/
public class ExcelDataModel {
	private String sheetName;
	private List<String[]> data;
	private String[] title;
	private List<PoiMergedRegionModel> regionList;
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public List<String[]> getData() {
		return data;
	}
	public void setData(List<String[]> data) {
		this.data = data;
	}
	public String[] getTitle() {
		return title;
	}
	public void setTitle(String[] title) {
		this.title = title;
	}
	public List<PoiMergedRegionModel> getRegionList() {
		return regionList;
	}
	public void setRegionList(List<PoiMergedRegionModel> regionList) {
		this.regionList = regionList;
	}
	
	
}
