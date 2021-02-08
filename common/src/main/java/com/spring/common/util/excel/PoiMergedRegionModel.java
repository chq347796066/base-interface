package com.spring.common.util.excel;

import java.io.Serializable;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:poi合并单元格模型类
*/
public class PoiMergedRegionModel implements Serializable 
{
	/**(info)*/
	private static final long serialVersionUID = -880346924318192310L;
	private Integer startRowIndex = null;
	private Integer startCellIndex = null;
	private Integer endRowIndex = null;
	private Integer endCellIndex = null;
	
	public PoiMergedRegionModel() {
		super();
	}
	public PoiMergedRegionModel(Integer startRowIndex, Integer startCellIndex,
			Integer endRowIndex, Integer endCellIndex) {
		super();
		this.startRowIndex = startRowIndex;
		this.startCellIndex = startCellIndex;
		this.endRowIndex = endRowIndex;
		this.endCellIndex = endCellIndex;
	}
	public Integer getStartRowIndex() {
		return startRowIndex;
	}
	public void setStartRowIndex(Integer startRowIndex) {
		this.startRowIndex = startRowIndex;
	}
	public Integer getStartCellIndex() {
		return startCellIndex;
	}
	public void setStartCellIndex(Integer startCellIndex) {
		this.startCellIndex = startCellIndex;
	}
	public Integer getEndRowIndex() {
		return endRowIndex;
	}
	public void setEndRowIndex(Integer endRowIndex) {
		this.endRowIndex = endRowIndex;
	}
	public Integer getEndCellIndex() {
		return endCellIndex;
	}
	public void setEndCellIndex(Integer endCellIndex) {
		this.endCellIndex = endCellIndex;
	}
	
	
}
