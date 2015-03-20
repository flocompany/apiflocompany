package com.flocompany.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SongWrappedDTO {
	
	private List<CategoryDTO> categories;
	private List<SongDTO> songs;
	

	
	public SongWrappedDTO() {
	}



	public SongWrappedDTO(List<CategoryDTO> categories, List<SongDTO> songs) {
		super();
		this.categories = categories;
		this.songs = songs;
	}



	public List<CategoryDTO> getCategories() {
		return categories;
	}



	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}



	public List<SongDTO> getSongs() {
		return songs;
	}



	public void setSongs(List<SongDTO> songs) {
		this.songs = songs;
	}

	


	
	
	
}
