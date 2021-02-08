package com.spring.common.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Hashtable;
import java.util.Map;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:效验异常类
*/
public class ValidationException extends RuntimeException
{
    private static final long serialVersionUID = -2763724242883969582L;

    private final BindingResult result;

    private final Map<String, String> resultMap = new Hashtable<String, String>();

    public BindingResult getResult()
    {
        return result;
    }

    public Map<String, String> getResultMap()
    {
        return resultMap;
    }

    public ValidationException(BindingResult result)
    {
        this.result = result;

        for (ObjectError objectError : this.result.getAllErrors())
        {
            String code = objectError.getCodes()[0];
            resultMap.put(code.substring(code.indexOf(".") + 1), objectError.getDefaultMessage());
        }
    }
}
