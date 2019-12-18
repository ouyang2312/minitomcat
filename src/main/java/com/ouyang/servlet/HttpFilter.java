package com.ouyang.servlet;

import com.ouyang.http.ApplicationFilterChain;
import com.ouyang.http.HttpServletRequest;
import com.ouyang.http.HttpServletResponse;

import java.io.IOException;

public abstract class HttpFilter extends MiniCatHttpPart{

	private String mapping;



	public String getMapping() {
		return mapping;
	}



	public void setMapping(String mapping) {
		this.mapping = mapping;
	}



	public abstract void doFilter(HttpServletRequest request, HttpServletResponse response, ApplicationFilterChain chain) throws IOException;
}
