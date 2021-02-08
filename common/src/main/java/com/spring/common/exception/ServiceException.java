package com.spring.common.exception;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:业务异常类
*/
public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	Integer exCode;
    String exMsg;

    public Integer getExCode()
    {
        return exCode;
    }

    public void setExCode(Integer exCode)
    {
        this.exCode = exCode;
    }

    public String getExMsg()
    {
        return exMsg;
    }

    public void setExMsg(String exMsg)
    {
        this.exMsg = exMsg;
    }

    public ServiceException()
    {
        super();
    }
    
    public ServiceException(String msg)
    {
        super(msg);
    }
    
    public ServiceException(Integer code, String msg)
    {
        super(msg);
        this.exCode = code;
        this.exMsg = msg;
    }

	
	/**
	 * ServiceException构造函数根据传递的异常信息
	 * @param argMessage
	 * @param argThr
	 */
	public ServiceException(String argMessage, Throwable argThr) {
		super(argMessage,argThr);
	}

}
