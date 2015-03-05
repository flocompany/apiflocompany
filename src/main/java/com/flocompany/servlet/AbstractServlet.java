package com.flocompany.servlet;

import javax.servlet.http.HttpServlet;

import com.flocompany.dao.impl.ParameterImpl;
import com.flocompany.dao.impl.UserImpl;

public abstract class AbstractServlet extends HttpServlet {
	
	
	public void init(){
		UserImpl.getInstance().init();
		ParameterImpl.getInstance().init();
	}
}
