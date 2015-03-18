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
	@Index String keyBlob;
	private String group;
	private String title;
	private String description;
	private String status=RestUtil.FREE;
	

	@Parent Key<Song> parent  = Key.create(Song.class, "Songs");
	
	public Song() {
	}

	
	
	public Song(String keyBlob, String group, String title, String description) {
		super();
		this.keyBlob = keyBlob;
		this.group = group;
		this.title = title;
		this.description = description;
	}



	public Song(Long id, String keyBlob, String group, String title,
			String description, String status) {
		super();
		this.id = id;
		this.keyBlob = keyBlob;
		this.group = group;
		this.title = title;
		this.description = description;
		this.status = status;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getKeyBlob() {
		return keyBlob;
	}



	public void setKeyBlob(String keyBlob) {
		this.keyBlob = keyBlob;
	}



	public String getGroup() {
		return group;
	}



	public void setGroup(String group) {
		this.group = group;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
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
    	SongDTO s = new SongDTO(keyBlob, group, title, description);
    	s.setId(this.id);
   	 	return s;
    }
 

    public void initFromDTO(SongDTO dto){
	   	 this.keyBlob=dto.getKeyBlob();
	   	 this.group=dto.getGroup();
	   	 this.title=dto.getTitle();
	   	 this.description=dto.getDescription();
    }
	
}
