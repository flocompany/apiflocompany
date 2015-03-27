package com.flocompany.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.flocompany.dao.model.Friend;
import com.flocompany.dao.model.Person;
import com.flocompany.dao.model.Song;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.rest.model.SongDTO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;


/** Persist operation on the Person Entity
 * @author FCOU
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
	public long addSong(String mp3Key, String oggKey, String category, String title, String extract, String description){
		Song s = new Song(mp3Key, oggKey, category, title, extract, description);
		Key<Song> key = ofy().save().entity(s).now(); 
		return key.getId();
	}
	
	/** Update the value of the status Song Entity
	 * @param name
	 * @param value
	 * @return
	 */
	public long updateFriend(String id, String status){
		Song s = ofy().cache(false).load().key(Key.create(Key.create(Song.class, "Songs"), Song.class, Long.valueOf(id))).now();
		if(s!=null){
			s.setStatus(status);
			Key<Song> keyToSave = ofy().cache(false).save().entity(s).now(); 
			return keyToSave.getId();
		}
		return -1;
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
	
	
	/** Get a Song list  by his category
	 * @param name
	 * @return
	 */
	public List<SongDTO> findSongByCategory(String codeCategory){
		List<Song> songs = ofy().cache(false).load().type(Song.class).ancestor(Key.create(Song.class, "Songs")).filter("category", codeCategory).list();
		List<SongDTO> results = new ArrayList<SongDTO>();
		for(Song s : songs){
			results.add(s.toDto());
		}
		return results;
	}
	
	/** Get the song by id
	 * @param id
	 * @return
	 */
	public SongDTO findById(String id){
		SongDTO result = null;
		Song s = ofy().cache(false).load().key(Key.create(Key.create(Song.class, "Songs"), Song.class, Long.valueOf(id))).now();
		if(s !=null){
			result = s.toDto();
		}
		return result;
	}
	
	/** delete the Song entity by his Id
	 * @param id
	 * @return
	 */
	public boolean deleteSong(long id){
		Key<Song> key = Key.create(Key.create(Key.create(Song.class, "Songs"), Song.class, id).getString());
		ofy().cache(false).delete().key(key).now();
		return true;
	}
	
	
}
