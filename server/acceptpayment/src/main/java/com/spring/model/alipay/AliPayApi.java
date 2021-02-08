package com.spring.model.alipay;


import com.spring.model.alipay.response.AliPayOrderCloseResponse;
import com.spring.model.alipay.response.AliPayOrderCreateResponse;
import com.spring.model.alipay.response.AliPayOrderQueryResponse;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.Map;
/**
 * @author dell
 */
public interface AliPayApi {

    @FormUrlEncoded
    @POST("gateway.do")
    Call<AliPayOrderQueryResponse> orderQuery(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("gateway.do")
    Call<AliPayOrderCreateResponse> tradeCreate(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("gateway.do")
    Call<AliPayOrderCloseResponse> close(@FieldMap Map<String, String> map);
}
