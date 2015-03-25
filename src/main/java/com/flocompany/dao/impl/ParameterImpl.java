package com.flocompany.dao.impl;

import static com.flocompany.util.RestUtil.MAIL_PARAMETER;
import static com.flocompany.util.RestUtil.URL_WEB_SERVICE_PARAMETER;
import static com.flocompany.util.RestUtil.CATEGORY_PARAMETER;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.flocompany.dao.model.Parameter;
import com.flocompany.rest.model.ParameterDTO;
import com.flocompany.util.EnumCategorySong;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;


/** Persist operation on the Person Entity
 * @author FCOU
 *
 */
public class ParameterImpl {
	static {
        ObjectifyService.register(Parameter.class);
    }
	
	/** Constructeur privé */
	private ParameterImpl(){}
 
	/** Instance unique pré-initialisée */
	private static ParameterImpl INSTANCE = new ParameterImpl();
 
	/** Point d'accès pour l'instance unique du singleton */
	public static ParameterImpl getInstance()
	{	return INSTANCE;
	}
	
	/**
	 * Initialise data of parameter
	 */
	public void init(){
		initParametre(URL_WEB_SERVICE_PARAMETER, "http://localhost/rest/");
		initParametre(MAIL_PARAMETER, "florent.courtiade@gmail.com");
		initParametre(CATEGORY_PARAMETER, EnumCategorySong.NONE.getCode());
	}
	
	/** Control if the user not exit before save
	 * @param name
	 * @param value
	 */
	public void initParametre(String name, String value){
		Parameter param = findParametre(name);
		if(param==null){
			addParametre(name, value);
		}
	}
	
	/** Add a new Parameter Entity
	 * @param name
	 * @param value
	 * @return
	 */
	public long addParametre(String name, String value){
		Parameter p = new Parameter(name, value);
		Key<Parameter> key = ofy().save().entity(p).now(); 
		return key.getId();
	}
	
	/** Update the valus of a Parameter Entity
	 * @param name
	 * @param value
	 * @return
	 */
	public long updateParametre(String name, String value){
		Parameter p = findParametre(name);
		if(p!=null){
			p.setValue(value);
			Key<Parameter> key = ofy().save().entity(p).now(); 
			return key.getId();
		}
		return -1;
	}
	
	/** Get a Parameter Entity by his name
	 * @param name
	 * @return
	 */
	private Parameter findParametre(final String name){
		Parameter parametre = ofy().load().type(Parameter.class).filter("name",name).first().now();
		return parametre;
	}
	
	/** Get a Parameter Entity by his name
	 * @param name
	 * @return
	 */
	public ParameterDTO findParametreDTO(final String name){
		ParameterDTO result = null;
		Parameter parametre = ofy().load().type(Parameter.class).filter("name",name).first().now();
		if(parametre!=null){
			result=parametre.toDto();
		}
		return result;
	}
	
	/** Get the value of the parameter Entity by his name
	 * @param name
	 * @return
	 */
	public String getValueByName(final String name){
		String result="";
		Parameter parametre = ofy().load().type(Parameter.class).filter("name",name).first().now();
		if(parametre!=null){
			result = parametre.getValue();
		}
		return result;
	}
	
	/** Get all Parameter Entity
	 * @return
	 */
	public List<ParameterDTO> findAllParameters(){
		List<Parameter> params = ofy().load().type(Parameter.class).list();
		List<ParameterDTO> results = new ArrayList<ParameterDTO>();
		for(Parameter p : params){
			results.add(p.toDto());
		}
		
		return results;
	}
	
	
}
