package com.spring.common.util.httpclient;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:解决HttpDelete不能把参数放RequestBody里面问题
*/
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase
{

    public static final String METHOD_NAME = "DELETE";

    @Override
    public String getMethod()
    {
        return METHOD_NAME;
    }

    public HttpDeleteWithBody(final String uri)
    {

        super();

        setURI(URI.create(uri));

    }

    public HttpDeleteWithBody(final URI uri)
    {

        super();

        setURI(uri);

    }

    public HttpDeleteWithBody()
    {
        super();
    }

}