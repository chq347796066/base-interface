package com.spring.common.importExcel.dto;

/**
 * 导入数据的校验结果DTO
 * 
 * @author Michael
 * 
 */
public abstract class ValidateResultDto {
	/**
	 * 数据是否合法
	 * 
	 * @return 合法返回true
	 */
	public abstract boolean isValid();
}
