package com.flocompany.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CategoryDTO {
	private String code = "";
	private String libelle = "";
	
	public CategoryDTO() {
	}

	public CategoryDTO(String code, String libelle) {
		this.code = code;
		this.libelle = libelle;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
