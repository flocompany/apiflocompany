package com.flocompany.dao.model;

import com.flocompany.rest.model.ParameterDTO;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Parameter {

	@Id Long id;
	@Index String name;
	private String value;
	
	
	
	
	public Parameter() {
	}

	public Parameter(Long id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	public Parameter(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
    
    public ParameterDTO toDto(){
    	ParameterDTO p = new ParameterDTO(id, name, value);
   	 	return p;
    }
 

    public void initFromDTO(ParameterDTO dto){
   	 this.name=dto.getName();
   	 this.value=dto.getValue();
    }
	
}
