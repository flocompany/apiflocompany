package com.flocompany.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;


import com.flocompany.dao.model.Person;
import com.flocompany.rest.model.PersonDTO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
public class UserImpl {
	static {
        ObjectifyService.register(Person.class);
    }
	
	/** Constructeur privé */
	private UserImpl()
	{}
 
	/** Instance unique pré-initialisée */
	private static UserImpl INSTANCE = new UserImpl();
 
	/** Point d'accès pour l'instance unique du singleton */
	public static UserImpl getInstance()
	{	return INSTANCE;
	}
	
	
	public void initUser(String pseudo, String mail, String pwd){
		if(findUserByEmail(mail).size()==0){
			addUser(pseudo, mail, pwd);
		}
	}
	
	public long addUser(String pseudo, String mail, String pwd){
		Person p = new Person(pseudo, mail, pwd);
		Key<Person> key = ofy().save().entity(p).now(); 
		System.out.println("id >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + key.getId());
		
		return key.getId();
	}
	
	
	public PersonDTO addUser(PersonDTO person){
		Person p = new Person();
		p.initFromDTO(person);
		Key<Person> key = ofy().save().entity(p).now(); 
		Person newPerson = ofy().load().key(key).now();
		return newPerson.toDto();
	}
	
	public List<PersonDTO> findAllUsers(){
		// Retourne tous les personnages dont la vie est supérieure à 50
		List<Person> users = ofy().load().type(Person.class).list();
		List<PersonDTO> results = new ArrayList<PersonDTO>();
		for(Person p : users){
			results.add(p.toDto());
		}
		
		return results;
	}
	
	public List<PersonDTO> findUserByPseudo(final String pseudo){
		List<Person> users = ofy().load().type(Person.class).filter("pseudo",pseudo).list();
		List<PersonDTO> results = new ArrayList<PersonDTO>();
		for(Person p : users){
			results.add(p.toDto());
		}
		
		return results;
	}
	
	public List<PersonDTO> findUserByEmail(final String mail){
		List<Person> users = ofy().load().type(Person.class).filter("email",mail).list();
		List<PersonDTO> results = new ArrayList<PersonDTO>();
		for(Person p : users){
			results.add(p.toDto());
		}
		
		return results;
	}
	
	public boolean deleteUser(long id){
		Person p = ofy().load().type(Person.class).id(id).now();
		
		ofy().delete().entity(p).now();
		return true;
	}
}
