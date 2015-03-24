package com.flocompany.dao.model;

import com.flocompany.rest.model.DeviceDTO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Device {

	@Id Long id;
	@Index String idRegDevice;
	private String type;
	
	@Parent Key<Person> parent  = Key.create(Person.class, "Person");
	
	public Device() {
		super();
	}
	public Device(String idRegDevice, String type, Key<Person> parent) {
		super();
		this.idRegDevice = idRegDevice;
		this.type = type;
		this.parent = parent;
	}
	public Device(Long id, String idRegDevice, String type, Key<Person> parent) {
		super();
		this.id = id;
		this.idRegDevice = idRegDevice;
		this.type = type;
		this.parent = parent;
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

	public Key<Person> getParent() {
		return parent;
	}

	public void setParent(Key<Person> parent) {
		this.parent = parent;
	}
	
	public DeviceDTO toDto() {
		DeviceDTO d = new DeviceDTO(idRegDevice, type, null);
		d.setId(this.id);
		return d;
	}

	public void initFromDTO(DeviceDTO dto) {
		this.idRegDevice = dto.getIdRegDevice();
		this.type = dto.getType();
	}
}
