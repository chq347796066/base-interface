package com.spring.base.vo;
/**
* @author 作者：赵进华 
* @version 创建时间：2019年4月25日 下午1:37:10
* @Desc类说明:验证码信息类
*/
public class VerifyCodeVo {

	/**
	 * base64字符串
	 */
	private String base64String;
	
	/**
	 * 验证码
	 */
	private String verifyCode;

	public String getBase64String() {
		return base64String;
	}

	public void setBase64String(String base64String) {
		this.base64String = base64String;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Override
	public String toString() {
		return "VerifyCodeVo [base64String=" + base64String + ", verifyCode=" + verifyCode + "]";
	}
	
	
}
