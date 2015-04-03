package com.flocompany.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.flocompany.dao.impl.FriendImpl;
import com.flocompany.dao.impl.SongImpl;
import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.model.FriendDTO;
import com.flocompany.rest.model.PersonWrappedDTO;
import com.flocompany.util.RestUtil;
import com.flocompany.util.StringUtil;

public class FriendAdminServlet extends AbstractServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		String restResult = "";
		String action = req.getParameter("action");
		
		if(action!=null){
			if(action.equals("update")){
				String id = req.getParameter("id");
				String status = req.getParameter("status");
				FriendDTO f = FriendImpl.getInstance().findById(id);
				f.setStatus(status);
		        if(StringUtil.isNotEmpty(id)){
		        	long resultL = FriendImpl.getInstance().updateFriend(f);
		        	if(resultL==-1){
		        		restResult=  "Le satus n'a pas pu etre mis a jour!! Verifier que l'id existe bien.";
		        	}else{
		        		restResult=  "Le satus a bien ete mis a jour.";
		        	}
		        }else{
		        	restResult=  "Le champ id est obligatoire pour l'update!!";
		        }
			}else if(action.equals("delete")){
				String id = req.getParameter("id");
				if(id!=null){
					FriendImpl.getInstance().delete(Long.valueOf(id));
				}
			}else if(action.equals("add")){
				String applicant = req.getParameter("idPersonApplicant");
				String person = req.getParameter("idPerson");
				 if(StringUtil.isNotEmpty(applicant)&&StringUtil.isNotEmpty(person)){
					 FriendImpl.getInstance().addFriend(applicant, person);
				 }else{
					 restResult=  "Les champs applicant et person sont obligatoires pour créer une amitié poil au nez!!";
				 }
			}else if(action.equals("addWs")){
				String applicant = req.getParameter("idPersonApplicant");
				String person = req.getParameter("idPerson");
				List<String> params = new ArrayList<String>();
				params.add(RestUtil.ID_APPLICANT + "=" + applicant);
				params.add(RestUtil.ID_PERSON + "=" + person);
				restResult = RestUtil.callRestService(RestUtil.PATH_ADD_FRIEND, "POST",  MediaType.APPLICATION_JSON, params);
			}else if(action.equals("mylist")){
				List<String> params = new ArrayList<String>();
				String id = req.getParameter("id");
				if(StringUtil.isNotEmpty(id)){
					params.add(RestUtil.ID + "=" + id);
					restResult = RestUtil.callRestService("friend/mylist", "GET",  MediaType.APPLICATION_JSON  + ";charset=utf-8", params);
				}else{
					restResult=  "Le champ id est obligatoire pour l'update!!";
				}
			}
		}
		try {
			req.setAttribute("restResult", restResult);
			List<FriendDTO> friends = FriendImpl.getInstance().findAllFriends();
			req.setAttribute("friendList", friends);
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
