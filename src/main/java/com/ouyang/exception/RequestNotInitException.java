package com.ouyang.exception;

@SuppressWarnings("serial")
public class RequestNotInitException extends MiniCatException{

	public RequestNotInitException(String msg){
		super(msg);
	}

}
