package com.spring.exception;

import com.spring.enums.BestPayResultEnum;

/**
 *
 * @author null
 * @date 2017/2/23
 */
public class BestPayException extends RuntimeException {

    private final Integer code;

    public BestPayException(BestPayResultEnum resultEnum) {
        super(resultEnum.getMsg());
        code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
