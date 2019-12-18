package com.ouyang.exception;

@SuppressWarnings("serial")
public class PageNotFoundException extends MiniCatException{

	public PageNotFoundException(String msg){
		super(msg);
	}
}
