package com.spring.saas.service;


import com.spring.base.vo.saas.PayResult;
import com.spring.base.vo.saas.PayVo;
import com.spring.common.response.ApiResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 作者：熊锋
 * @date : 创建时间：2020-07-01 17:10:11
 * @Desc类说明: 支付接口
 */
public interface IPayService {

	/**
	 * 调用微信生成二维码
	 * @author 熊锋
	 * @param payVo
	 * @date 2020/7/7 19:40
	 * @return java.lang.String
	 * @throws Exception
	 */
	String createCode(PayVo payVo);

	/**
	 * 查询支付状态
	 * @author 熊锋
	 * @param orderNum
	 * @date 2020/7/7 19:40
	 * @return java.lang.String
	 * @throws Exception
	 */
	ApiResponseResult queryPayStatus(String orderNum);

	/**
	 * 微信回调
	 * @author 熊锋
	 * @param request
	 * @param response
	 * @date 2020/7/8 16:27
	 * @return java.util.Map
	 * @throws Exception
	 */
	void getWxPayResult(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
