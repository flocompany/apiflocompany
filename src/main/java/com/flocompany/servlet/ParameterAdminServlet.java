package com.flocompany.servlet;

import static com.flocompany.util.RestUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.internal.inject.ParameterInjectionBinder;

import com.flocompany.dao.impl.ParameterImpl;
import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.model.ParameterDTO;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.util.RestUtil;

public class ParameterAdminServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(ParameterAdminServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		
		if(action!=null){
			if(action.equals("update")){
				String name = req.getParameter("name");
				String value = req.getParameter("value");
				ParameterImpl.getInstance().updateParametre(name, value);
			}
		}
		
		
		
		List<ParameterDTO> params = ParameterImpl.getInstance().findAllParameters();
		
		req.setAttribute("parameterList", params);
		
		try {
			req.getRequestDispatcher("/jsp/parameter_admin.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		this.doGet(req, resp);
	}
	public void init(){
		ParameterImpl.getInstance().init();
	}
	
	
	
}
