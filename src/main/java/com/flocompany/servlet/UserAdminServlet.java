package com.flocompany.servlet;

import static com.flocompany.util.RestUtil.MAIL;
import static com.flocompany.util.RestUtil.PSEUDO;
import static com.flocompany.util.RestUtil.PWD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.util.RestUtil;

public class UserAdminServlet extends AbstractServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		
		if(action!=null){
			if(action.equals("signup")){
				String pseudo = req.getParameter("pseudo");
				String mail = req.getParameter("mail");
				String pwd = req.getParameter("pwd");
				List<String> params = new ArrayList<String>();
				params.add(PSEUDO + "=" + pseudo);
				params.add(MAIL + "=" +mail);
				params.add(PWD + "=" +pwd);
				String restResult = RestUtil.callRestService(RestUtil.PATH_SIGNUP, "POST",  MediaType.APPLICATION_JSON, params);
				req.setAttribute("restResult", restResult);
			}else if(action.equals("delete")){
				String id = req.getParameter("id");
				if(id!=null){
					UserImpl.getInstance().deleteUser(Long.valueOf(id));
				}
			}else if(action.equals("login")){
				String pseudo = req.getParameter("pseudo");
				String pwd = req.getParameter("pwd");
				List<String> params = new ArrayList<String>();
				params.add(PSEUDO + "=" + pseudo);
				params.add(PWD + "=" +pwd);
				String restResult = RestUtil.callRestService(RestUtil.PATH_LOGIN, "POST",  MediaType.APPLICATION_JSON, params);
				req.setAttribute("restResult", restResult);
			}else if(action.equals("demo")){
				String restResult = RestUtil.callRestService("person", "GET",  MediaType.TEXT_PLAIN, null);
				req.setAttribute("restResult", restResult);
			}else if(action.equals("add")){
				String pseudo = req.getParameter("pseudo");
				String mail = req.getParameter("mail");
				String pwd = req.getParameter("pwd");
				UserImpl.getInstance().addUser(pseudo, mail, pwd);
			}else if(action.equals("search")){
				List<String> params = new ArrayList<String>();
				String pseudo = req.getParameter("pseudo");;
				params.add(PSEUDO + "=" + pseudo);
				String restResult = RestUtil.callRestService("person/search", "GET",  MediaType.APPLICATION_JSON  + ";charset=utf-8", params);
				req.setAttribute("restResult", restResult);
			}
		}
		
		
		
		List<PersonDTO> users = UserImpl.getInstance().findAllUsers();
		
		req.setAttribute("userList", users);
		
		try {
			req.getRequestDispatcher("/jsp/user_admin.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("**********POST");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		this.doGet(req, resp);
	}

	
	
	
}
