package com.spring.saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.spring.base.entity.saas.OrderEntity;
import com.spring.base.entity.saas.TenantEntity;
import com.spring.base.vo.saas.*;
import com.spring.common.constants.MessageCode;
import com.spring.common.constants.Pay;
import com.spring.common.enums.OrderTypeEnum;
import com.spring.common.enums.TenantStatusEnum;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.date.DateTime;
import com.spring.common.util.wxpay.WxSet;
import com.spring.common.util.httpclient.HttpClient;
import com.spring.saas.config.WxPayConfig;
import com.spring.saas.dao.IOrderDao;
import com.spring.saas.dao.ITenantDao;
import com.spring.saas.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-07-01 17:10:11
 * @Desc类说明: 租户信息业务接口实现类
 */

 @Slf4j
@Service("payService")
public class PayServiceImpl implements IPayService {

 	@Autowired
	private WxPayConfig config;

 	@Autowired
	private IOrderDao orderDao;

 	@Autowired
	private ITenantDao tenantDao;

 	private final static String SUCCESS="SUCCESS";

	/**
	 * @author 熊锋
	 * @param payVo
	 * @date 2020/7/7 19:40
	 * @description:调用微信生成二维码
	 * @return java.lang.String
	 * @throws Exception
	 */
	@Override
	public String createCode(PayVo payVo) {
		Map<String,String> hashMap = new HashMap<>(16);
		//二维码过期时间
		String expireTime= DateTime.getOrderExpireTime(5*60*1000L);
		hashMap.put(Pay.APP_ID,config.getAppId());
		hashMap.put(Pay.MCH_ID,config.getMchId());
		hashMap.put(Pay.NONCE_STR, WXPayUtil.generateNonceStr());
		hashMap.put(Pay.BODY,Pay.BODY_VALUE);
		hashMap.put(Pay.OUT_TRADE_NO,payVo.getOrderNum());
		hashMap.put(Pay.TOTAL_FEE, WxSet.getMoney(payVo.getTotalFee()));
		hashMap.put(Pay.SPBILL_CREATE_IP,Pay.SPBILL_CREATE_IP_VALUE);
		hashMap.put(Pay.NOTIFY_URL,config.getNotifyUrl());
		hashMap.put(Pay.TRADE_TYPE,Pay.TRADE_TYPE_VALUE);
		hashMap.put(Pay.PRODUCT_ID,payVo.getGoodId());
		hashMap.put(Pay.TIME_EXPIRE,expireTime);

		try {
			// 将参数转成xml发送
			String xmlParam = WXPayUtil.generateSignedXml(hashMap, config.getMchKey());
			// 创建https请求，调用统一支付接口
			HttpClient httpClient = new HttpClient(config.getPayUrl());
			httpClient.setHttps(true);
			httpClient.setXmlParam(xmlParam);
			httpClient.post();
			// 获取调用结果
			String content = httpClient.getContent();
			Map<String, String> stringStringMap = WXPayUtil.xmlToMap(content);
			Map<String, String> map = new HashMap<>(16);
			map.put("code_url",stringStringMap.get("code_url"));
			map.put("total_fee",payVo.getTotalFee());
			map.put("out_trade_no",payVo.getOrderNum());
			if(map!=null){
				return map.get("code_url");
			}
			return null;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author 熊锋
	 * @param orderNum
	 * @date 2020/7/7 16:34
	 * @description: 调用微信查询支付状态
	 * @return java.util.Map
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryPayStatus(String orderNum){
		ApiResponseResult result=new ApiResponseResult();
		PayResult payResult=new PayResult();
		Map<String, String> hashMap = new HashMap<>(16);
		hashMap.put(Pay.APP_ID,config.getAppId());
		hashMap.put(Pay.MCH_ID,config.getMchId());
		hashMap.put(Pay.OUT_TRADE_NO,orderNum);
		hashMap.put(Pay.NONCE_STR,WXPayUtil.generateNonceStr());
		try {
			String xmlParam = WXPayUtil.generateSignedXml(hashMap, config.getMchKey());
			HttpClient httpClient = new HttpClient(config.getQueryUrl());
			httpClient.setHttps(true);
			httpClient.setXmlParam(xmlParam);
			httpClient.post();
			String content = httpClient.getContent();
			Map<String, String> map = WXPayUtil.xmlToMap(content);
			if(map!=null){
				//将map转成对象
				Gson gson = new Gson();
				JsonElement jsonElement=gson.toJsonTree(map);
				payResult=gson.fromJson(jsonElement,PayResult.class);
			}
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("订单查询成功");
			result.setData(payResult);
		}catch (Exception e){
			log.error(e.getMessage());
			result.setCode(MessageCode.FAIL);
			result.setMsg("查询异常");
		}
		return result;
	}

	/**
	 * @author 熊锋
	 * @param request
	 * @date 2020/7/7 20:34
	 * @description: 微信回调
	 * @return com.spring.base.vo.PayResult
	 * @throws Exception
	 */
	@Override
	public void getWxPayResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("支付完成执行微信回调开始:{}");
		OrderUpdateVo orderUpdateVo=new OrderUpdateVo();
		PayResult payResult=new PayResult();
		InputStream inStream = request.getInputStream();
		StringBuffer result=new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
		String line;
		while ((line = in.readLine()) != null) {
			result.append(line);
		}
		log.info("微信回调返回xml格式数据:{}"+result.toString());
		in.close();
		inStream.close();
		String resXml = "";
		Map<String, String> map = WXPayUtil.xmlToMap(result.toString());
		if(map!=null){
			//将map转成对象
			Gson gson = new Gson();
			JsonElement jsonElement=gson.toJsonTree(map);
			payResult=gson.fromJson(jsonElement,PayResult.class);
		}
		log.info("微信回调返回xml转成对象:{}"+payResult);
		//返回成功
		if(SUCCESS.equals(payResult.getResult_code())){
			log.info("返回成功标识:{}"+payResult.getResult_code());
			log.info("支付成功:{}");
			//修改用户状态以及修改用户购买了哪些应用
			StringBuilder builder=new StringBuilder();
			String appId=null;
			QueryWrapper<OrderEntity> wrapper=new QueryWrapper<>();
			wrapper.eq("order_num",payResult.getOut_trade_no())
					.eq("del_flag",0);
			OrderEntity entity=orderDao.selectOne(wrapper);
			if (entity!=null){
				appId=entity.getAppId();
			}
			//根据手机号查询租户是否正式用户还是试用
			QueryWrapper<TenantEntity> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("mobile", RequestUtils.getUserId())
					.eq("del_flag",0);
			TenantEntity tenantEntity=tenantDao.selectOne(queryWrapper);
			if (tenantEntity!=null){
				//1:待审核，2: 审核试用，3:审核拒绝，4:正式启用，5:停用
				if (tenantEntity.getTenantStatus().equals(TenantStatusEnum.TRY_OUT.getEnumCode())){
					//如果用户为试用 直接将购买的应用替换调试用应用
					tenantDao.updateTenantApp(RequestUtils.getUserId(),appId);
				}
				if (tenantEntity.getTenantStatus().equals(TenantStatusEnum.ENABLE.getEnumCode())){
					//如果用户为正式用户 添加购买的应用
					builder.append(tenantEntity.getApplicationId()).append(",").append(appId);
					tenantDao.updateTenantApp(RequestUtils.getUserId(),builder.toString());
				}
			}

			//支付成功修改支付状态
			orderUpdateVo.setOrderStatus(2);
			orderUpdateVo.setPayType(1);
			orderUpdateVo.setPayDate(DateTime.strToDateLong(payResult.getTime_end()));
			if (entity.getOrderType().equals(OrderTypeEnum.ORDER.getEnumCode())){
				//如果应用为订购到期时间为支付时间加上购买时间
				orderUpdateVo.setExpireDate(orderUpdateVo.getPayDate().plusMonths(entity.getBuyTime()));
			}
			if (entity.getOrderType().equals(OrderTypeEnum.RENEW.getEnumCode())){
				//如果应用为续费到期时间为上次到期时间加上购买时间
				orderUpdateVo.setExpireDate(entity.getExpireDate().plusMonths(entity.getBuyTime()));
			}
			orderUpdateVo.setPayNum(payResult.getTransaction_id());
			orderUpdateVo.setOrderNum(payResult.getOut_trade_no());
			orderDao.updatePay(orderUpdateVo);
			resXml= WxSet.setXml("SUCCESS", "OK");
		}else{
			log.error("支付失败:{}");
			// 支付失败
			orderUpdateVo.setOrderStatus(4);
			orderUpdateVo.setOrderNum(payResult.getOut_trade_no());
			orderDao.updatePay(orderUpdateVo);
			resXml= WxSet.setXml("FAIL", "报文为空");
		}
		//通知微信已经收到消息，不要再给我发消息了，否则微信会8连击调用本接口
		BufferedOutputStream out=new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
		log.info("支付完成执行微信回调结束:{}");
	}
}
