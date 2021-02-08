package com.spring.base.entity.data;

/**
 * 描述:对象未找到异常
 * 
 */
@SuppressWarnings("serial")
public class ObjectNotFoundException extends RuntimeException {

  /**
   * 构造函数
   * @param message 异常信息
   */
  public ObjectNotFoundException(String message) {
    super(message);
  }
  
}
