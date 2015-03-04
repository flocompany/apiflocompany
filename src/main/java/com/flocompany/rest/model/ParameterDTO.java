package com.flocompany.rest.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ParameterDTO {
 
	private long id;
    private String name;
    private String value;
         
    public ParameterDTO() {}

	public ParameterDTO(long id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

         
}
