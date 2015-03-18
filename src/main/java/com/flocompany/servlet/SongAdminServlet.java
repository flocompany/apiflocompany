package com.flocompany.servlet;

import java.io.IOException;
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
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class SongAdminServlet extends AbstractServlet {

	private static final Logger log = Logger.getLogger(SongAdminServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		String action = req.getParameter("action");
		if(action!=null){
			if(action.equals("upload")){
		
		        // Récupère une Map de tous les champs d'upload de fichiers
		        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		
		        // Récupère la liste des fichiers uploadés dans le champ "uploadedFile"
		        // (il peut y en avoir plusieurs si c'est un champ d'upload multiple, d'où la List)
		        List<BlobKey> blobKeys = blobs.get("uploadedFile");
		
		        // Récupère la clé identifiant du fichier uploadé dans le Blobstore (à sauvegarder)
		        String cleFichierUploade = blobKeys.get(0).getKeyString();
		
		        // On peut récupérer les autres champs du formulaire comme d'habitude
		        String group = req.getParameter("group");
		        String title = req.getParameter("title");
		        String description = req.getParameter("description");
		        String status = req.getParameter("status");
				
		        SongImpl.getInstance().addSong(cleFichierUploade, group, title, description);
		        req.setAttribute("result", cleFichierUploade);
				try {
					List<SongDTO> songs =  SongImpl.getInstance().findAllSongs();
					req.setAttribute("songList", songs);
					req.getRequestDispatcher("/jsp/song_admin.jsp").forward(req, resp);
				} catch (ServletException e) {
					e.printStackTrace();
				}
			}
			if(action.equals("download")){
		        if (req.getParameter("blob-key") != null) {
		            BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		            blobstoreService.serve(blobKey, resp);
		        }
				
			}
		}else{
			try {
				List<SongDTO> songs =  SongImpl.getInstance().findAllSongs();
				req.setAttribute("songList", songs);
				req.getRequestDispatcher("/jsp/song_admin.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		this.doGet(req, resp);
	}
	
	
	
}
