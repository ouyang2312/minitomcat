package com.ouyang.exception;

@SuppressWarnings("serial")
public class BadRequestException extends MiniCatException{

	public BadRequestException(String msg){
		super(msg);
	}

}
