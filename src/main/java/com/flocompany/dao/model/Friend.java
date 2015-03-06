package com.flocompany.dao.model;

import com.flocompany.rest.model.FriendDTO;
import com.flocompany.util.RestUtil;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Friend {

	@Id Long id;
	@Index Long idPersonApplicant;
	@Index Long idPerson;
	private String status=RestUtil.ACCEPTED;
	
	@Parent Key<Friend> parent;
	
	public Friend() {
		super();
	}


    public Friend(Long idPersonApplicant, Long idPerson) {
		super();
		this.idPersonApplicant = idPersonApplicant;
		this.idPerson = idPerson;
        this.parent = Key.create(Friend.class, "friends");
	}
    public Friend(Long idPersonApplicant, Long idPerson, String status) {
		super();
		this.idPersonApplicant = idPersonApplicant;
		this.idPerson = idPerson;
		this.status = status;
	}
    public Friend(Long id, Long idPersonApplicant, Long idPerson, String status) {
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
	public FriendDTO toDto(){
    	FriendDTO f = new FriendDTO(id, idPersonApplicant, idPerson, status);
   	 	return f;
    }
    public void initFromDTO(FriendDTO dto){
    	this.idPerson=dto.getIdPerson();
		this.idPersonApplicant=dto.getIdPersonApplicant();
		this.status=dto.getStatus();
    }
	
}
