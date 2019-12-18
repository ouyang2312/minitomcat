package com.ouyang.servlet;

import com.ouyang.http.HttpServletRequest;
import com.ouyang.http.HttpServletResponse;

import java.io.IOException;

public abstract class HttpServlet extends MiniCatHttpPart{

	public abstract void doService(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
