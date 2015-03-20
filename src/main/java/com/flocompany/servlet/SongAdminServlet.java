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
			        req.setAttribute("result", "L'upload des fichiers a été réalisé avec succés.");
		        }else{
		        	if(StringUtil.isNotEmpty(mp3Key)){
						blobstoreService.delete(blobKeysMp3.get(0));
		        	}
		        	if(StringUtil.isNotEmpty(oggKey)){
						blobstoreService.delete(blobKeysOgg.get(0));
		        	}
		        	req.setAttribute("result", "Les champs titre, extrait et les deux fichiers sont obligatoires!!");
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
				//TODO
				
			}
		}
		
		
		try {
			List<SongDTO> songs =  SongImpl.getInstance().findAllSongs();
			req.setAttribute("songList", songs);
			req.getRequestDispatcher("/jsp/song_admin.jsp").forward(req, resp);
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
