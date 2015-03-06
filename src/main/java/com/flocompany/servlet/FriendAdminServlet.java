package com.flocompany.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.flocompany.dao.impl.FriendImpl;
import com.flocompany.rest.model.FriendDTO;
import com.flocompany.util.RestUtil;

public class FriendAdminServlet extends AbstractServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		
		if(action!=null){
			if(action.equals("update")){
				String id = req.getParameter("id");
				String applicant = req.getParameter("idPersonApplicant");
				String person = req.getParameter("idPerson");
				String status = req.getParameter("status");
				FriendImpl.getInstance().updateFriend(id, status);
			}else if(action.equals("delete")){
				String id = req.getParameter("id");
				if(id!=null){
					FriendImpl.getInstance().delete(Long.valueOf(id));
				}
			}else if(action.equals("add")){
				String applicant = req.getParameter("idPersonApplicant");
				String person = req.getParameter("idPerson");
				String status = req.getParameter("status");
				FriendImpl.getInstance().addFriend(applicant, person);
			}else if(action.equals("addWs")){
				String applicant = req.getParameter("idPersonApplicant");
				String person = req.getParameter("idPerson");
				List<String> params = new ArrayList<String>();
				params.add(RestUtil.ID_APPLICANT + "=" + applicant);
				params.add(RestUtil.ID_PERSON + "=" + person);
				String restResult = RestUtil.callRestService(RestUtil.PATH_ADD_FRIEND, "POST",  MediaType.APPLICATION_JSON, params);
				req.setAttribute("restResult", restResult);
			}else if(action.equals("mylist")){
				List<String> params = new ArrayList<String>();
				String id = req.getParameter("id");
				params.add(RestUtil.ID + "=" + id);
				String restResult = RestUtil.callRestService("friend/mylist", "GET",  MediaType.APPLICATION_JSON  + ";charset=utf-8", params);
				req.setAttribute("restResult", restResult);
			}
		}
		
		
		
		List<FriendDTO> friends = FriendImpl.getInstance().findAllFriends();
		
		req.setAttribute("friendList", friends);
		
		try {
			req.getRequestDispatcher("/jsp/friend_admin.jsp").forward(req, resp);
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

	
	
	
}
