package com.flocompany.rest.model;

public class MessageAEnvoyerDTO {
	private String id;
	private String title;
	private String body;
	private String dateCreation;
	
	
	public MessageAEnvoyerDTO() {
		super();
	}
	
	public MessageAEnvoyerDTO(String id, String title, String body,
			String dateCreation) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.dateCreation = dateCreation;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	
}
