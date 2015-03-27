package com.flocompany.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.flocompany.dao.model.Friend;
import com.flocompany.dao.model.Message;
import com.flocompany.dao.model.Person;
import com.flocompany.dao.model.Song;
import com.flocompany.rest.model.MessageAEnvoyerDTO;
import com.flocompany.rest.model.MessageDTO;
import com.flocompany.rest.model.MessageWrappedDTO;
import com.flocompany.rest.model.SongDTO;
import com.flocompany.util.RestUtil;
import com.flocompany.util.StringUtil;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;


/** Persist operation on the Message Entity
 * @author FCOUCOU
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
		long result = -1;
		Key<Friend> friend = Key.create(Friend.class, Long.valueOf(idFriend));
		Message m = new Message(idSender, idSong, "", new Date(), false, friend);
		Key<Message> key = ofy().save().entity(m).now(); 
		if(key!=null){
			result=key.getId();
		}
		return result;
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
	
	/** Count all message not read
	 * @param friend
	 * @return
	 */
	public int countMessagesByFriend(String idFriend, String idUser){
		Key<Friend> friend = Key.create(Friend.class, Long.valueOf(idFriend));
		List<Message> messages = ofy().cache(false).load().type(Message.class).ancestor(friend).filter("idSender !=", idUser).filter("read =", false).list();
		int results = messages.size();
		return results;
	}
	
	
	/** Get all Messages by friend. The message is update to read if the user id is not the sender.
	 * @param friend
	 * @return
	 */
	public List<MessageWrappedDTO> findAllMessagesByFriend(String idFriend, String idUser, boolean updateReadValue){
		Key<Friend> friend = Key.create(Friend.class, Long.valueOf(idFriend));
		List<Message> messages = ofy().cache(false).load().type(Message.class).ancestor(friend).order("dateMessage").list();
		List<MessageWrappedDTO> results = new ArrayList<MessageWrappedDTO>();
		for(Message m : messages){
			String dateMessage=StringUtil.formatDateMessage(m.getDateMessage());
			if(updateReadValue){
				if(!m.getIdSender().equals(idUser)){
					if(!m.getRead()){
						m.setRead(true);
						ofy().cache(false).save().entity(m).now();
					}
				}
			}
			MessageWrappedDTO newMessageWrappedDTO = new MessageWrappedDTO(m.getId(), m.getIdSender(), m.getIdSong(), m.getMessage(), dateMessage, m.getRead(), Long.valueOf(idFriend));
			SongDTO s = SongImpl.getInstance().findById(m.getIdSong());		
			newMessageWrappedDTO.setMp3Key(s.getMp3Key());
			newMessageWrappedDTO.setOggKey(s.getOggKey());
			results.add(newMessageWrappedDTO);
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
		ofy().cache(false).delete().key(key).now();
		return true;
	}
	
}
