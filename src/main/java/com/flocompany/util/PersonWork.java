package com.flocompany.util;

import com.googlecode.objectify.Work;

public class PersonWork<DTO> implements Work {
	
	private DTO dto;

	@Override
	public Object run() {
		return null;
	}
	
	public void setDto(DTO dto){
		this.dto=dto;
	}
}
