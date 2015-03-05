package com.flocompany.dao.impl;

import static com.flocompany.util.RestUtil.MAIL_ADMIN;
import static com.flocompany.util.RestUtil.URL_WEB_SERVICE;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.flocompany.dao.model.Friend;
import com.flocompany.dao.model.Parameter;
import com.flocompany.dao.model.Person;
import com.flocompany.rest.model.FriendDTO;
import com.flocompany.rest.model.ParameterDTO;
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
		Friend f = ofy().load().type(Friend.class).id(Long.valueOf(id)).now();
		if(f!=null){
			f.setStatus(status);
			Key<Friend> key = ofy().save().entity(f).now(); 
			return key.getId();
		}
		return -1;
	}
	
	/** Get a Friend Entity by his name
	 * @param name
	 * @return
	 */
	public Friend findFriend(String idApplicant, String idPerson){
		Friend friends = ofy().load().type(Friend.class).filter("idPersonApplicant", Long.valueOf(idApplicant)).filter("idPerson", Long.valueOf(idPerson)).first().now();
		return friends;
	}
	
	
	
	
	/** Get all Friend Entity
	 * @return
	 */
	public List<FriendDTO> findAllFriends(){
		List<Friend> friends = ofy().load().type(Friend.class).list();
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
		List<Friend> friends = ofy().load().type(Friend.class).filter("idPersonApplicant", Long.valueOf(idPerson)).list();
		if(friends.size()<=0){
			System.out.println("nullllllllllllllllllllll");
			 friends = ofy().load().type(Friend.class).filter("idPerson", Long.valueOf(idPerson)).list();

				System.out.println("friend.size" + friends.size());
			 if(friends.size()>0){
				for(Friend f : friends){
					results.add(f.getIdPerson());
				}
			 }
		}else{
			for(Friend f : friends){
				results.add(f.getIdPerson());
			}
		}
		return results;
	}
	
	
	
	/** delete the User entity by his Id
	 * @param id
	 * @return
	 */
	public boolean delete(long id){
		Friend p = ofy().load().type(Friend.class).id(id).now();
		
		ofy().delete().entity(p).now();
		return true;
	}
	
}
