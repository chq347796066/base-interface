package com.spring.common.cache;

public class CachesException extends RuntimeException{

	private static final long serialVersionUID = 9108610493368719536L;

	public CachesException(){}
	
	public CachesException(String message){
		super(message);
	}
	
	public CachesException(String message,Throwable throwable){
		super(message,throwable);
	}
	
	public CachesException(Throwable throwable){
		super(throwable);
	}
	
}
