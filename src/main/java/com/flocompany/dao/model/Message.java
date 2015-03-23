package com.flocompany.dao.model;

import java.util.Date;

import com.flocompany.rest.model.MessageDTO;
import com.flocompany.rest.model.PersonDTO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class Message {
 

    
	@Id Long id;
    @Index String idSender;
    @Index String idSong;
    private String message;
    @Index Date dateMessage;
    @Index boolean read;

	@Parent Key<Friend> parent  = Key.create(Friend.class, "Friend");
	
	
	public Message(){
		
	}
	

	public Message(String idSender, String idSong, String message,
			Date dateMessage, boolean read, Key<Friend> parent) {
		super();
		this.idSender = idSender;
		this.idSong = idSong;
		this.message = message;
		this.dateMessage = dateMessage;
		this.read = read;
		this.parent = parent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdSender() {
		return idSender;
	}

	public void setIdSender(String idSender) {
		this.idSender = idSender;
	}

	public String getIdSong() {
		return idSong;
	}

	public void setIdSong(String idSong) {
		this.idSong = idSong;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateMessage() {
		return dateMessage;
	}

	public void setDateMessage(Date dateMessage) {
		this.dateMessage = dateMessage;
	}

	public boolean getRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	
	
	

     public Key<Friend> getParent() {
		return parent;
	}


	public void setParent(Key<Friend> parent) {
		this.parent = parent;
	}


	public MessageDTO toDto(){
    	 MessageDTO m = new MessageDTO(idSender, idSong, message, dateMessage, read, parent.getId());
    	 m.setId(this.id);
    	 return m;
     }
  

     public void initFromDTO(MessageDTO dto){
    	 this.idSender=dto.getIdSender();
    	 this.idSong=dto.getIdSong();
    	 this.message=dto.getMessage();
    	 this.dateMessage=dto.getDateMessage();
    	 this.read=dto.getRead();
     }
}
