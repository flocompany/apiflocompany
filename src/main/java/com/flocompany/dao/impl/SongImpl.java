package com.flocompany.dao.impl;

import static com.flocompany.util.RestUtil.MAIL_ADMIN;
import static com.flocompany.util.RestUtil.URL_WEB_SERVICE;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.flocompany.dao.model.Parameter;
import com.flocompany.dao.model.Person;
import com.flocompany.dao.model.Song;
import com.flocompany.rest.model.ParameterDTO;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.rest.model.SongDTO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;


/** Persist operation on the Person Entity
 * @author FC07315S
 *
 */
public class SongImpl {
	static {
        ObjectifyService.register(Song.class);
    }
	
	/** Constructeur privé */
	private SongImpl(){}
 
	/** Instance unique pré-initialisée */
	private static SongImpl INSTANCE = new SongImpl();
 
	/** Point d'accès pour l'instance unique du singleton */
	public static SongImpl getInstance()
	{	return INSTANCE;
	}
	
	/**
	 * Initialise data of parameter
	 */
	public void init(){
	}
	

	
	/** Add a new Song Entity
	 * @param name
	 * @param value
	 * @return
	 */
	public long addSong(String keyBlob, String group, String title, String description){
		Song s = new Song(keyBlob, group, title, description);
		Key<Song> key = ofy().save().entity(s).now(); 
		return key.getId();
	}
	
	/** Get all Song
	 * @return
	 */
	public List<SongDTO> findAllSongs(){
		List<Song> songs = ofy().cache(false).load().type(Song.class).ancestor(Key.create(Song.class, "Songs")).list();
		List<SongDTO> results = new ArrayList<SongDTO>();
		for(Song s : songs){
			results.add(s.toDto());
		}
		
		return results;
	}
	
	
}
