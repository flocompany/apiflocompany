package com.flocompany.rest.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class FriendDTO {
 
	private Long id;
	private Long idPersonApplicant;
	private Long idPerson;
	private String status;
	
	
	
	public FriendDTO() {
	}

	public FriendDTO(Long id, Long idPersonApplicant, Long idPerson,
			String status) {
		super();
		this.id = id;
		this.idPersonApplicant = idPersonApplicant;
		this.idPerson = idPerson;
		this.status = status;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdPersonApplicant() {
		return idPersonApplicant;
	}
	public void setIdPersonApplicant(Long idPersonApplicant) {
		this.idPersonApplicant = idPersonApplicant;
	}
	public Long getIdPerson() {
		return idPerson;
	}
	public void setIdPerson(Long idPerson) {
		this.idPerson = idPerson;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
