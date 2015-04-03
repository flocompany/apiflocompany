package com.flocompany.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.flocompany.dao.model.Friend;
import com.flocompany.dao.model.Person;
import com.flocompany.rest.model.FriendDTO;
import com.flocompany.rest.model.FriendWrappedDTO;
import com.flocompany.rest.model.MessageDTO;
import com.flocompany.rest.model.MessageWrappedDTO;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.util.RestUtil;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;


/** Persist operation on the Friend Entity
 * @author FCOU
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
	}
	
	/** Control if the friend not exit before save
	 * @param name
	 * @param value
	 */
	public void initFriend(String idApplicant, String idPerson){
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
	 * @return
	 */
	public long updateFriend(FriendDTO friendDTO){
		if(friendDTO!=null){
			Friend f = new Friend();
			f.initFromDTO(friendDTO);
			System.out.println("sscqscqsccssssss" + friendDTO.getStatus());
			System.out.println("ssssssssssssss" + f.getStatus());
			System.out.println("ssssssssssssss" + f.getIdBlocker());
			Key<Friend> key = ofy().save().entity(f).now(); 
			Friend newFriend = ofy().load().key(key).now();
			System.out.println("ssssqqqqqqqqqqqqqqqqqqqqqqs" + newFriend.getStatus());
			return newFriend.getId();
		}
		return -1;
	}
	
	/** Get a Friend Entity by his name
	 * @param name
	 * @return
	 */
	public Friend findFriend(String idApplicant, String idPerson){
		Friend friends = ofy().cache(false).load().type(Friend.class).ancestor(Key.create(Friend.class, "Friends")).filter("idPersonApplicant", Long.valueOf(idApplicant)).filter("idPerson", Long.valueOf(idPerson)).first().now();
		if(friends==null){
			friends = ofy().cache(false).load().type(Friend.class).ancestor(Key.create(Friend.class, "Friends")).filter("idPersonApplicant", Long.valueOf(idPerson)).filter("idPerson", Long.valueOf(idApplicant)).first().now();
		}
		return friends;
	}
	
	
	
	
	/** Get all Friend Entity
	 * @return
	 */
	public List<FriendDTO> findAllFriends(){
		List<Friend> friends = ofy().cache(false).load().type(Friend.class).ancestor(Key.create(Friend.class, "Friends")).list();
		List<FriendDTO> results = new ArrayList<FriendDTO>();
		for(Friend f : friends){
			results.add(f.toDto());
		}
		
		return results;
	}
	
	/** Get list of friend id of a Person
	 * @return
	 */
	public List<Long> findFriendIdsByPerson(String idPerson){
		List<Long> results = new ArrayList<Long>();
		List<Friend> friends = ofy().cache(false).load().type(Friend.class).ancestor(Key.create(Friend.class, "Friends")).filter("idPersonApplicant", Long.valueOf(idPerson)).list();
		if (friends.size() <= 0) {
			friends = ofy().load().type(Friend.class).ancestor(Key.create(Friend.class, "Friends")).filter("idPerson", Long.valueOf(idPerson)).list();
			if (friends.size() > 0) {
				for (Friend f : friends) {
					results.add(f.getIdPersonApplicant());
				}
			}
		} else {
			for (Friend f : friends) {
				results.add(f.getIdPerson());
			}
			friends = ofy().load().type(Friend.class).ancestor(Key.create(Friend.class, "Friends")).filter("idPerson", Long.valueOf(idPerson)).list();
			if (friends.size() > 0) {
				for (Friend f : friends) {
					results.add(f.getIdPersonApplicant());
				}
			}
		}
		return results;
	}
	
	/** Get list of friend id of a Person
	 * @return
	 */
	public List<FriendWrappedDTO> findFriendWrappedDTOByIdperson(String idPerson){
		List<FriendWrappedDTO> results = new ArrayList<FriendWrappedDTO>();
		List<Friend> friends = ofy().cache(false).load().type(Friend.class).ancestor(Key.create(Friend.class, "Friends")).filter("idPersonApplicant", Long.valueOf(idPerson)).list();
		if (friends.size() <= 0) {
			friends = ofy().load().type(Friend.class).ancestor(Key.create(Friend.class, "Friends")).filter("idPerson", Long.valueOf(idPerson)).list();
			if (friends.size() > 0) {
				for (Friend f : friends) {
					results.add(buildFriendWrappedDTO(f, idPerson, true));
				}
			}
		} else {
			for (Friend f : friends) {
				results.add(buildFriendWrappedDTO(f, idPerson, false));
			}
			friends = ofy().load().type(Friend.class).ancestor(Key.create(Friend.class, "Friends")).filter("idPerson", Long.valueOf(idPerson)).list();
			if (friends.size() > 0) {
				for (Friend f : friends) {
					results.add(buildFriendWrappedDTO(f, idPerson, true));
				}
			}
		}
		return results;
	}
	
	private FriendWrappedDTO buildFriendWrappedDTO(Friend f, String idUser, boolean applicant){
		FriendWrappedDTO newFriendWrappedDTO = new FriendWrappedDTO(f.getId(), f.getIdPersonApplicant(), f.getIdPersonApplicant(), f.getStatus());
		PersonDTO p = null;
		if(applicant){
			p = UserImpl.getInstance().findById(String.valueOf(f.getIdPersonApplicant()));
		}else{
			p = UserImpl.getInstance().findById(String.valueOf(f.getIdPerson()));
		}
		newFriendWrappedDTO.setPseudo(p.getPseudo());
		newFriendWrappedDTO.setEmail(p.getEmail());
		int nbMessage = MessageImpl.getInstance().countMessagesByFriend(String.valueOf(f.getId()),idUser);
		newFriendWrappedDTO.setNbMessage(String.valueOf(nbMessage));
		return newFriendWrappedDTO;
	}
	
	
	public FriendDTO findById(String id){
		FriendDTO result = null;
		Friend p = ofy().cache(false).load().key(Key.create(Key.create(Friend.class, "Friends"), Friend.class, Long.valueOf(id))).now();
		if(p !=null){
			result = p.toDto();
		}
		return result;
	}
	
	
	/** delete the Friend entity by his Id
	 * @param id
	 * @return
	 */
	public boolean delete(long id){
		List<MessageWrappedDTO> msgList = MessageImpl.getInstance().findAllMessagesByFriend(String.valueOf(id), "", false);
		if(msgList.size()>0){
			for(MessageWrappedDTO msg : msgList){
				MessageImpl.getInstance().deleteMessage(msg.getId(), msg.getIdFriend());
			}
		}
		Key<Friend> key = Key.create(Key.create(Key.create(Friend.class, "Friends"), Friend.class, id).getString());
		ofy().cache(false).delete().key(key).now();
		return true;
	}
	
}
