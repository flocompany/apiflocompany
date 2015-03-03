package com.flocompany.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.flocompany.dao.model.Parametre;
import com.flocompany.dao.model.Person;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
public class ParametreImpl {
	static {
        ObjectifyService.register(Person.class);
    }
	
	/** Constructeur privé */
	private ParametreImpl(){}
 
	/** Instance unique pré-initialisée */
	private static ParametreImpl INSTANCE = new ParametreImpl();
 
	/** Point d'accès pour l'instance unique du singleton */
	public static ParametreImpl getInstance()
	{	return INSTANCE;
	}
	
	public void initParametre(String name, String value){
		List<Parametre> params = findParametre(name);
		if(params.size()==0){
			addParametre(name, value);
		}else{
			for(Parametre p : params){
				p.setValue(value);
			}
		}
	}
	
	public long addParametre(String name, String value){
		Parametre p = new Parametre(name, value);
		Key<Parametre> key = ofy().save().entity(p).now(); 
		return key.getId();
	}
	
	
	public List<Parametre> findParametre(final String name){
		List<Parametre> parametres = ofy().load().type(Parametre.class).filter("name",name).list();
		return parametres;
	}
	
}
