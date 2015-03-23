package com.flocompany.rest.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.flocompany.dao.model.Friend;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;


@XmlRootElement
public class MessageWrappedDTO {


    
	private Long id;
    private Long idFriend;
    private String idSender;
    private String idSong;
    private String message;
    private String mp3Key;
    private String oggKey;
    private String dateMessage;
    private boolean read;
	
	
	public MessageWrappedDTO(){
		
	}
	

	public MessageWrappedDTO(Long id, String idSender, String idSong, String message,
			String dateMessage, boolean read, Long idFriend) {
		super();
		this.id = id;
		this.idSender = idSender;
		this.idSong = idSong;
		this.message = message;
		this.dateMessage = dateMessage;
		this.read = read;
		this.idFriend=idFriend;
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

	public String getDateMessage() {
		return dateMessage;
	}

	public void setDateMessage(String dateMessage) {
		this.dateMessage = dateMessage;
	}

	public boolean getRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}


	public Long getIdFriend() {
		return idFriend;
	}


	public void setIdFriend(Long idFriend) {
		this.idFriend = idFriend;
	}


	public String getMp3Key() {
		return mp3Key;
	}


	public void setMp3Key(String mp3Key) {
		this.mp3Key = mp3Key;
	}


	public String getOggKey() {
		return oggKey;
	}


	public void setOggKey(String oggKey) {
		this.oggKey = oggKey;
	}
	
	
}
