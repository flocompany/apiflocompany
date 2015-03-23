package com.flocompany.rest.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class FriendWrappedDTO {
 
	private Long id;
	private Long idPersonApplicant;
	private Long idPerson;
	private String status;
	private String pseudo;
	private String email;
	private String nbMessage;
	
	
	
	public FriendWrappedDTO() {
	}

	public FriendWrappedDTO(Long id, Long idPersonApplicant, Long idPerson,
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

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNbMessage() {
		return nbMessage;
	}

	public void setNbMessage(String nbMessage) {
		this.nbMessage = nbMessage;
	}

	

}
