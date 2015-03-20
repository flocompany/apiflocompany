package com.flocompany.dao.model;

import com.flocompany.rest.model.ParameterDTO;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.rest.model.SongDTO;
import com.flocompany.util.RestUtil;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Song {

	@Id Long id;
	@Index String mp3Key;
	@Index String oggKey;
	@Index String category;
	private String title;
	private String extract;
	private String description;
	private String status=RestUtil.FREE;
	

	@Parent Key<Song> parent  = Key.create(Song.class, "Songs");
	
	public Song() {
	}

	
	
	public Song(String mp3Key,String oggKey, String category, String title, String extract, String description) {
		super();
		this.mp3Key = mp3Key;
		this.oggKey = oggKey;
		this.category = category;
		this.title = title;
		this.extract = extract;
		this.description = description;
	}



	public Song(Long id,String mp3Key,String oggKey, String category, String title, String extract, String description, String status) {
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



	public SongDTO toDto(){
    	SongDTO s = new SongDTO(id, mp3Key, oggKey, category, title, extract, description, status);
   	 	return s;
    }
 

    public void initFromDTO(SongDTO dto){
	   	 this.mp3Key=dto.getMp3Key();
	   	 this.oggKey=dto.getOggKey();
	   	 this.category=dto.getCategory();
	   	 this.title=dto.getTitle();
	   	 this.extract=dto.getExtract();
	   	 this.description=dto.getDescription();
	   	 this.status=dto.getStatus();
    }
	
}
