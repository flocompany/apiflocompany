package com.flocompany.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.flocompany.dao.model.Person;
import com.flocompany.rest.model.PersonDTO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
/**
 * Persist operation on the Person Entity
 * @author FC07315S
 *
 */
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
	
	/** Initialise data of user
	 * @param pseudo
	 * @param mail
	 * @param pwd
	 */
	public void init(){
		initUser("titteuf", "titteuf@gmail.com", "AZERTY1");
		initUser("tazman", "tazman@voila.fr", "123356");
		initUser("titi", "titi@yahoo.fr", "Defebbdf");
		initUser("tata32", "tata32@voila.fr", "dsgbtrhrtf");
	}
	
	
	/** Control if the user not exit before save
	 * @param pseudo
	 * @param mail
	 * @param pwd
	 */
	public void initUser(String pseudo, String mail, String pwd){
		if(findUserByEmail(mail)==null){
			addUser(pseudo, mail, pwd);
		}
	}
	
	/** Add a new Person Entity
	 * @param pseudo
	 * @param mail
	 * @param pwd
	 * @return
	 */
	public long addUser(String pseudo, String mail, String pwd){
		Person p = new Person(pseudo, mail, pwd);
		Key<Person> key = ofy().save().entity(p).now(); 
		System.out.println("id >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + key.getId());
		
		return key.getId();
	}
	
	
	/** Add a new Person Entity
	 * @param person
	 * @return
	 */
	public PersonDTO addUser(PersonDTO person){
		Person p = new Person();
		p.initFromDTO(person);
		Key<Person> key = ofy().save().entity(p).now(); 
		Person newPerson = ofy().load().key(key).now();
		return newPerson.toDto();
	}
	
	
	
	/** Get all Users
	 * @return
	 */
	public List<PersonDTO> findAllUsers(){
		// Retourne tous les personnages dont la vie est supérieure à 50
		List<Person> users = ofy().load().type(Person.class).list();
		List<PersonDTO> results = new ArrayList<PersonDTO>();
		for(Person p : users){
			results.add(p.toDto());
		}
		
		return results;
	}
	
	/** Get the Person Entity by his pseudo
	 * @param pseudo
	 * @return
	 */
	public PersonDTO findUserByPseudo(final String pseudo){
		Person user = ofy().load().type(Person.class).filter("pseudo",pseudo).first().now();
		PersonDTO result = null;
		if (user!=null){
			result=user.toDto();
		}
		
		return result;
	}
	
	/** Get the person Entity by his mail
	 * @param mail
	 * @return
	 */
	public PersonDTO findUserByEmail(final String mail){
		Person user = ofy().load().type(Person.class).filter("email",mail).first().now();
		PersonDTO result = null;
		if (user!=null){
			result=user.toDto();
		}
		
		return result;
	}
	
	/** delete the User entity by his Id
	 * @param id
	 * @return
	 */
	public boolean deleteUser(long id){
		Person p = ofy().load().type(Person.class).id(id).now();
		
		ofy().delete().entity(p).now();
		return true;
	}
	
	
	public PersonDTO findById(String id){
		PersonDTO result = null;
		Person p = ofy().load().type(Person.class).id(Long.valueOf(id)).now();
		if(p !=null){
			result = p.toDto();
		}
		return result;
	}
}
