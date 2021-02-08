package com.spring.common.sequence;


/**
 * @author dell
 */

public enum BusinessCodeConstant {
	/**
	 * SUB_CHARGE_NO
	 */
	SUB_CHARGE_NO("SUB_CHARGE_NO");
	private final String bsCode;

	BusinessCodeConstant(String bsCode) {
		this.bsCode = bsCode;
	}

	public String getBsCode() {
		return bsCode;
	}

}
