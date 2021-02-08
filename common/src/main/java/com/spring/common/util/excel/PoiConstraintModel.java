package com.spring.common.util.excel;

import java.io.Serializable;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:poi数据有效性模型类
*/
public class PoiConstraintModel extends PoiMergedRegionModel implements Serializable {
	
	/**(info)*/
	private static final long serialVersionUID = -5089834355518561831L;
	private String[] constraintArr = null;
	
	public PoiConstraintModel() {
		super();
	}

	public PoiConstraintModel(Integer startRowIndex, Integer startCellIndex,
			Integer endRowIndex, Integer endCellIndex,String[] constraintArr) {
		super(startRowIndex, startCellIndex, endRowIndex, endCellIndex);
		this.constraintArr = constraintArr;
	}

	public String[] getConstraintArr() {
		return constraintArr;
	}

	public void setConstraintArr(String[] constraintArr) {
		this.constraintArr = constraintArr;
	}
}