package com.flocompany.rest.model;

import com.flocompany.dao.model.Person;
import com.googlecode.objectify.Key;

public class DeviceDTO {
	private Long id;
	private String idRegDevice;
	private String type;

	public DeviceDTO() {
		super();
	}

	public DeviceDTO(String idRegDevice, String type, Key<Person> parent) {
		super();
		this.idRegDevice = idRegDevice;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdRegDevice() {
		return idRegDevice;
	}

	public void setIdRegDevice(String idRegDevice) {
		this.idRegDevice = idRegDevice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



}
