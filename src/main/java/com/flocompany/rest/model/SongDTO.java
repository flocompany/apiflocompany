package com.flocompany.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SongDTO {

	private Long id;
	private String mp3Key;
	private String oggKey;
	private String category;
	private String title;
	private String extract;
	private String description;
	private String status;
	

	
	public SongDTO() {
	}

	
	
	public SongDTO(String mp3Key,String oggKey, String category, String title, String extract, String description) {
		super();
		this.mp3Key = mp3Key;
		this.oggKey = oggKey;
		this.category = category;
		this.title = title;
		this.extract = extract;
		this.description = description;
	}



	public SongDTO(Long id,String mp3Key,String oggKey, String category, String title, String extract, String description, String status) {
		super();
		this.id = id;
		this.mp3Key = mp3Key;
		this.oggKey = oggKey;
		this.category = category;
		this.title = title;
		this.extract = extract;
		this.description = description;
		this.status = status;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getExtract() {
		return extract;
	}



	public void setExtract(String extract) {
		this.extract = extract;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
}
