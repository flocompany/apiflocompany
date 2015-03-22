package com.flocompany.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.flocompany.dao.model.Friend;
import com.flocompany.dao.model.Message;
import com.flocompany.dao.model.Person;
import com.flocompany.dao.model.Song;
import com.flocompany.rest.model.MessageDTO;
import com.flocompany.rest.model.SongDTO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;


/** Persist operation on the Person Entity
 * @author FC07315S
 *
 */
public class MessageImpl {
	static {
        ObjectifyService.register(Message.class);
    }
	
	/** Constructeur privé */
	private MessageImpl(){}
 
	/** Instance unique pré-initialisée */
	private static MessageImpl INSTANCE = new MessageImpl();
 
	/** Point d'accès pour l'instance unique du singleton */
	public static MessageImpl getInstance()
	{	return INSTANCE;
	}
	
	/**
	 * Initialise data of message
	 */
	public void init(){
	}
	
	

	/**Add a new Message Entity
	 * @param idSender
	 * @param idSong
	 * @param friend
	 * @return
	 */
	public long addMessage(String idSender, String idSong, String idFriend){
		Key<Friend> friend = Key.create(Friend.class, Long.valueOf(idFriend));
		Message m = new Message(idSender, idSong, "", new Date(), false, friend);
		Key<Message> key = ofy().save().entity(m).now(); 
		return key.getId();
	}
	
	/** Get all Messages
	 * @param friend
	 * @return
	 */
	public List<MessageDTO> findAllMessages(){
		List<Message> messages = ofy().cache(false).load().type(Message.class).list();
		List<MessageDTO> results = new ArrayList<MessageDTO>();
		for(Message m : messages){
			results.add(m.toDto());
		}
		
		return results;
	}
	
	
	/** Get all Messages
	 * @param friend
	 * @return
	 */
	public List<MessageDTO> findAllMessagesByFriend(Key<Friend> friend){
		List<Message> messages = ofy().cache(false).load().type(Message.class).ancestor(friend).list();
		List<MessageDTO> results = new ArrayList<MessageDTO>();
		for(Message m : messages){
			results.add(m.toDto());
		}
		
		return results;
	}
	
	
	
	/** Get all Messages
	 * @param friend
	 * @return
	 */
	public List<MessageDTO> findAllMessagesByFriend(String idFriend){
		Key<Friend> friend = Key.create(Friend.class, Long.valueOf(idFriend));
		System.out.println(friend.getString());
		List<Message> messages = ofy().cache(false).load().type(Message.class).ancestor(friend).list();
		System.out.println("sise" + messages.size());
		List<MessageDTO> results = new ArrayList<MessageDTO>();
		for(Message m : messages){
			results.add(m.toDto());
		}
		
		return results;
	}
	
	/** delete the Message entity by his Id
	 * @param id
	 * @return
	 */
	public boolean deleteMessage(long id, long idFriend){
		
		Key<Friend> keyFriend = Key.create(Friend.class, idFriend);
		Key<Message> key = Key.create(keyFriend, Message.class, id);

		System.out.println("********** id" + id);
		System.out.println("********** key" + key.getString());
		System.out.println("********** keyFriend" + keyFriend.getString());
		ofy().cache(false).delete().key(key).now();
		return true;
	}
	
}
