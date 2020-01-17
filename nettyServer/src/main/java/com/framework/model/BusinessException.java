package com.framework.model;

/**
 * 业务异常类
 * 便于异常捕获与拦截处理
 * @author 马博士
 *
 */
public class BusinessException extends RuntimeException{
    
	private static final long serialVersionUID = 1L;
	
	private Integer code;
	
	private String message;
    
    public BusinessException(Integer code,String message) {
    	super(message);
    	this.code=code;
    	this.message=message;
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
   
}
