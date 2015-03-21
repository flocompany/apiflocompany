package com.flocompany.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.flocompany.dao.model.Friend;
import com.flocompany.dao.model.Person;
import com.flocompany.rest.model.FriendDTO;
import com.flocompany.rest.model.PersonDTO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;


/** Persist operation on the Friend Entity
 * @author FC07315S
 *
 */
public class FriendImpl {
	static {
        ObjectifyService.register(Friend.class);
    }
	
	/** Constructeur privé */
	private FriendImpl(){}
 
	/** Instance unique pré-initialisée */
	private static FriendImpl INSTANCE = new FriendImpl();
 
	/** Point d'accès pour l'instance unique du singleton */
	public static FriendImpl getInstance()
	{	return INSTANCE;
	}
	
	/**
	 * Initialise data of parameter
	 */
	public void init(){
//		initFriend(URL_WEB_SERVICE, "http://localhost/rest/");
//		initFriend(MAIL_ADMIN, "florent.courtiade@gmail.com");
	}
	
	/** Control if the friend not exit before save
	 * @param name
	 * @param value
	 */
	public void initFriend(String idApplicant, String idPerson){
//		Parameter param = findFriend(name);
//		if(param==null){
//			addParametre(name, value);
//		}
	}
	
	/** Add a new Friend Entity
	 * @param name
	 * @param value
	 * @return
	 */
	public long addFriend(String idApplicant, String idPerson){
		long result = -1;
		if(findFriend(idApplicant, idPerson)==null){
			Friend f = new Friend(Long.valueOf(idApplicant), Long.valueOf(idPerson));
			Key<Friend> key = ofy().save().entity(f).now(); 
			result = key.getId();
		}
		return result;
	}
	
	

	
	/** Update the value of a Friend Entity
	 * @param name
	 * @param value
	 * @return
	 */
	public long updateFriend(String id, String status){
		Key<Friend> key = Key.create(Key.create(Key.create(Friend.class, "friends"), Friend.class, Long.valueOf(id)).getString());
		Friend f = ofy().cache(false).load().type(Friend.class).id(Long.valueOf(id)).now();
		if(f!=null){
			f.setStatus(status);
			Key<Friend> keyToSave = ofy().cache(false).save().entity(f).now(); 
			return keyToSave.getId();
		}
		return -1;
	}
	
	/** Get a Friend Entity by his name
	 * @param name
	 * @return
	 */
	public Friend findFriend(String idApplicant, String idPerson){
		Friend friends = ofy().cache(false).load().type(Friend.class).ancestor(Key.create(Friend.class, "friends")).filter("idPersonApplicant", Long.valueOf(idApplicant)).filter("idPerson", Long.valueOf(idPerson)).first().now();
		return friends;
	}
	
	
	
	
	/** Get all Friend Entity
	 * @return
	 */
	public List<FriendDTO> findAllFriends(){
		List<Friend> friends = ofy().cache(false).load().type(Friend.class).ancestor(Key.create(Friend.class, "friends")).list();
		List<FriendDTO> results = new ArrayList<FriendDTO>();
		for(Friend f : friends){
			results.add(f.toDto());
		}
		
		return results;
	}
	
	/** Get list of friend id of a Person
	 * @return
	 */
	public List<Long> findFriendByPerson(String idPerson){
		List<Long> results = new ArrayList<Long>();
		List<Friend> friends = ofy().cache(false).load().type(Friend.class).ancestor(Key.create(Friend.class, "friends")).filter("idPersonApplicant", Long.valueOf(idPerson)).list();
		if (friends.size() <= 0) {
			friends = ofy().load().type(Friend.class).ancestor(Key.create(Friend.class, "friends")).filter("idPerson", Long.valueOf(idPerson)).list();
			if (friends.size() > 0) {
				for (Friend f : friends) {
					results.add(f.getIdPersonApplicant());
				}
			}
		} else {
			for (Friend f : friends) {
				results.add(f.getIdPerson());
			}
			friends = ofy().load().type(Friend.class).ancestor(Key.create(Friend.class, "friends")).filter("idPerson", Long.valueOf(idPerson)).list();
			if (friends.size() > 0) {
				for (Friend f : friends) {
					results.add(f.getIdPersonApplicant());
				}
			}
		}
		return results;
	}
	
	
	public FriendDTO findById(String id){
		FriendDTO result = null;
		Friend p = ofy().cache(false).load().key(Key.create(Key.create(Friend.class, "Friends"), Friend.class, Long.valueOf(id))).now();
		if(p !=null){
			result = p.toDto();
		}
		return result;
	}
	
	
	/** delete the User entity by his Id
	 * @param id
	 * @return
	 */
	public boolean delete(long id){
		Key<Friend> key = Key.create(Key.create(Key.create(Friend.class, "friends"), Friend.class, id).getString());
		ofy().cache(false).delete().key(key).now();
		return true;
	}
	
}
