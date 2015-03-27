package com.flocompany.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flocompany.dao.impl.MessageImpl;
import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.model.MessageDTO;
import com.flocompany.util.StringUtil;

public class MessageAdminServlet extends AbstractServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		
		if(action!=null){
			if(action.equals("add")){
				String idFriend = req.getParameter("idFriend");
				String idSender = req.getParameter("idSender");
				String idSong = req.getParameter("idSong");
				String message = req.getParameter("message");
				MessageImpl.getInstance().addMessage(idSender, idSong, idFriend);
			}else if(action.equals("delete")){
				String id = req.getParameter("id");
				String idFriend = req.getParameter("idFriend");
				if(id!=null){
					MessageImpl.getInstance().deleteMessage(Long.valueOf(id), Long.valueOf(idFriend));
				}
			}
		}
		
		try {
			List<MessageDTO> messages = MessageImpl.getInstance().findAllMessages();
			req.setAttribute("messageList", messages);
			req.getRequestDispatcher("/jsp/message_admin.jsp").forward(req, resp);
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
