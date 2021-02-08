package com.spring.base.entity.data;

/**
 * 描述:参数错误异常
 * 
 */
@SuppressWarnings("serial")
public class InvalidArgumentException extends RuntimeException {

  /**
   * 构造函数
   * @param message 错误信息
   */
  public InvalidArgumentException(String message) {
    super(message);
  }
  
}
