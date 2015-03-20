package com.flocompany.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class SongInterfaceServlet extends AbstractServlet {

	private static final Logger log = Logger
			.getLogger(SongInterfaceServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		

		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();

		if (req.getParameter("mp3_key") != null) {
			BlobKey blobKey = new BlobKey(req.getParameter("mp3_key"));
			blobstoreService.serve(blobKey, resp);
		}
		
		if (req.getParameter("ogg_key") != null) {
			BlobKey blobKey = new BlobKey(req.getParameter("ogg_key"));
			blobstoreService.serve(blobKey, resp);
		}

	}

}
