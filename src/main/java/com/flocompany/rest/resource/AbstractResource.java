package com.flocompany.rest.resource;

import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.exception.NotAcceptableException;
import com.flocompany.rest.model.PersonDTO;

public abstract class AbstractResource {

	public PersonDTO testPrivilege(String id, String pwd){
		PersonDTO p = UserImpl.getInstance().findById(id);
		if(p !=null){
			if(p.getPwd().equals(pwd)){
				return p;
			}
		}
		throw new NotAcceptableException("Sorry, you are not logged to the application.");
	}
}
