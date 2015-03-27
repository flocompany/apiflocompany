package com.flocompany.rest.resource;

import static com.flocompany.util.RestUtil.PUB_CONTENT_PARAMETER;
import static com.flocompany.util.RestUtil.PUB_PARAMETER;
import static com.flocompany.util.StringUtil.isBlank;
import static com.flocompany.util.StringUtil.isEmpty;

import java.security.NoSuchAlgorithmException;

import com.flocompany.dao.impl.ParameterImpl;
import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.exception.BadRequestException;
import com.flocompany.rest.exception.NotAcceptableException;
import com.flocompany.rest.exception.PublicityException;
import com.flocompany.rest.exception.TechnicalException;
import com.flocompany.rest.model.ParameterDTO;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.util.SecurityUtil;

public abstract class AbstractResource {

	protected void testPrivilege(String id, String pwd){

		System.out.println("qsdfsqqdf" + pwd);
		if (isEmpty(id) || isEmpty(pwd)
				|| isBlank(id) || isBlank(pwd) ) {
			throw new NotAcceptableException("Sorry, you must be logged to the application.");
		}

		PersonDTO p = UserImpl.getInstance().findById(id);
		if(p !=null){
			if(!p.getPwd().equals(pwd)){
				throw new NotAcceptableException("Sorry, you must be logged to the application.");
			}
		}else{
			throw new NotAcceptableException("Sorry, you must be logged to the application.");
		}
	}
	
	/** Send the publicity
	 * @param id
	 */
	protected void sendPublicity(String id){
		if(countAccess(id)){
			ParameterDTO pcontent = ParameterImpl.getInstance().findParametreDTO(PUB_CONTENT_PARAMETER);
			throw new PublicityException(pcontent.getValue());
		}
	}
	
	/** Increment the user access and return true if is in the limit
	 * @param id
	 * @return
	 */
	protected boolean countAccess(String id){
		boolean result = false;
		ParameterDTO p = ParameterImpl.getInstance().findParametreDTO(PUB_PARAMETER);
		System.out.println("pppppp" + p);
    	int limit = Integer.valueOf(p.getValue());
    	if(limit>0){ //integration de pub si parametre actif
    		PersonDTO personDTO = UserImpl.getInstance().findById(id);
    		if(limit<personDTO.getAccessHomeCount()){ //initialiser le compteur
    			personDTO.setAccessHomeCount(0);
    			UserImpl.getInstance().updateUser(personDTO);
    			result=true;
    		}else{ //incrementer
    			personDTO.setAccessHomeCount(personDTO.getAccessHomeCount()+1);
    			UserImpl.getInstance().updateUser(personDTO);
    		}
    	}
    	return result;
	}
}
