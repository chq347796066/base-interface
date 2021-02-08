package com.spring.common.response;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object>
{
    //
    private static final long serialVersionUID = -8157613083634272196L;

    public R()
    {
        put("code", 0);
        put("msg", "success");
    }

    public static R error()
    {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg)
    {
        return error(500, msg);
    }

    public static R error(int code, String msg)
    {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error401() {
        return error(401, "你还没有登录");
    }

    public static R error403() {
        return error(403, "你没有访问权限");
    }

    public static R ok(String msg)
    {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map)
    {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok()
    {
        return new R();
    }

    public static R data(Object data){
        return R.ok().put("data",data);
    }

    public static R page(Object page){
        return R.ok().put("page",page);
    }

    @Override
    public R put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}