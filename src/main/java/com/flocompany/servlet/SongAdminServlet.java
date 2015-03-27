package com.flocompany.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flocompany.dao.impl.SongImpl;
import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.rest.model.SongDTO;
import com.flocompany.util.EnumCategorySong;
import com.flocompany.util.StringUtil;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class SongAdminServlet extends AbstractServlet {

	private static final Logger log = Logger.getLogger(SongAdminServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("ISO-8859-1");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("ISO-8859-1");
        String result="";
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		String action = req.getParameter("action");
		if(action!=null){
			if(action.equals("upload")){
		
		        // Récupère une Map de tous les champs d'upload de fichiers
		        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		        String oggKey="";
		        String mp3Key="";
		        List<BlobKey> blobKeysMp3 = blobs.get("mp3File");
		        if(blobKeysMp3!=null){
		        	mp3Key = blobKeysMp3.get(0).getKeyString();
		        }
		

		        List<BlobKey> blobKeysOgg = blobs.get("oggFile");
		        if(blobKeysOgg!=null){
		        	oggKey = blobKeysOgg.get(0).getKeyString();
		        }
		        
		        
		        
			        String category = req.getParameter("category");
			        String title = req.getParameter("title");
			        String extract = req.getParameter("extract");
			        String description = req.getParameter("description");
			        String status = req.getParameter("status");
			        
			        
			        
			    if(StringUtil.isNotEmpty(mp3Key)&&StringUtil.isNotEmpty(oggKey)&&StringUtil.isNotEmpty(title)&&StringUtil.isNotEmpty(extract)){
			        SongImpl.getInstance().addSong(mp3Key, oggKey, category, title, extract, description);
			        result=  "L'upload des fichiers s'est bien passes.";
		        }else{
		        	if(StringUtil.isNotEmpty(mp3Key)){
						blobstoreService.delete(blobKeysMp3.get(0));
		        	}
		        	if(StringUtil.isNotEmpty(oggKey)){
						blobstoreService.delete(blobKeysOgg.get(0));
		        	}
		        	result=  "Les champs titre, extrait et les deux fichiers sont obligatoires!!";
		        }
				
			}
			if(action.equals("delete")){
				SongDTO s = SongImpl.getInstance().findById(req.getParameter("id"));
				
				
				BlobKey mp3BlobKey = new BlobKey(s.getMp3Key());
				BlobKey oggBlobKey = new BlobKey(s.getOggKey());
				blobstoreService.delete(mp3BlobKey);
				blobstoreService.delete(oggBlobKey);
				
				SongImpl.getInstance().deleteSong(s.getId());
				
			}
			if(action.equals("update")){
		        String status = req.getParameter("status");
		        String id = req.getParameter("id_song");
		        if(StringUtil.isNotEmpty(id)){
		        	long resultL = SongImpl.getInstance().updateFriend(id, status);
		        	if(resultL==-1){
		        		result=  "Le satus n'a pas pu etre mis a jour!! Verifier que l'id existe bien.";
		        	}else{
		        		result=  "Le satus a bien ete mis a jour.";
		        	}
		        }else{
		        	result=  "Les champs id est obligatoire pour l'update!!";
		        }
			}
			resp.sendRedirect(req.getContextPath() + "/admin/song?result=" + result);
		}
		
		try {
			String r = req.getParameter("result");
			req.setAttribute("result", r);
			List<SongDTO> songs =  SongImpl.getInstance().findAllSongs();
			req.setAttribute("songList", songs);
			req.getRequestDispatcher("/jsp/song_admin.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("ISO-8859-1");
		req.setCharacterEncoding("ISO-8859-1");
		this.doGet(req, resp);
	}
	
	
	
}
