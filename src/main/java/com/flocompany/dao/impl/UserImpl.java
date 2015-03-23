package com.flocompany.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.flocompany.dao.model.Friend;
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
		List<Person> users = ofy().cache(false).load().type(Person.class).ancestor(Key.create(Person.class, "Persons")).list();
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
		Person user = ofy().cache(false).load().type(Person.class).ancestor(Key.create(Person.class, "Persons")).filter("pseudo",pseudo).first().now();
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
		Person user = ofy().cache(false).load().type(Person.class).ancestor(Key.create(Person.class, "Persons")).filter("email",mail).first().now();
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
		boolean result =false;
		List<Long> friendList = FriendImpl.getInstance().findFriendIdsByPerson(String.valueOf(id));
			if(friendList.size()>0){		
			Key<Person> key = Key.create(Key.create(Key.create(Person.class, "Persons"), Person.class, id).getString());
			ofy().cache(false).delete().key(key).now();
			result=true;
		}
		return result;
	}
	
	
	public PersonDTO findById(String id){
		PersonDTO result = null;
		Person p = ofy().cache(false).load().key(Key.create(Key.create(Person.class, "Persons"), Person.class, Long.valueOf(id))).now();
		if(p !=null){
			result = p.toDto();
		}
		return result;
	}
	
	
	public PersonDTO findRandomById(String id, List<Long> friendListTosubscrire){
		PersonDTO result = null;
		List<PersonDTO> persons = findAllUsers();
		List<PersonDTO> personsWithoutFriend = new ArrayList<PersonDTO>();
		if(friendListTosubscrire.size()>0){
			for(PersonDTO p : persons){
				if(!friendListTosubscrire.contains(p.getId())){
					personsWithoutFriend.add(p);
				}
			}
		}else{
			personsWithoutFriend=persons;
		}

		System.out.println("********* personsWithoutFriend.size()" + personsWithoutFriend.size());
		int index = 0;
		if(personsWithoutFriend.size()>0){
			Double random = Math.random() * ( personsWithoutFriend.size() - 0 );
			result = personsWithoutFriend.get(random.intValue());
		}
		
		return result;
	}
	
	/** Get Users by ids
	 * @return
	 */
	public List<PersonDTO> findUsersByIds(List<Long> idPersons){

		List<PersonDTO> results = new ArrayList<PersonDTO>();
		List<Key<Person>> keys = new ArrayList<Key<Person>>();
		for(Long id : idPersons){
			Key<Person> m = Key.create(Key.create(Person.class, "Persons"), Person.class, id);
			keys.add(m);
		}
		if(keys.size()>0){
			Map<Key<Person>, Person> users = ofy().cache(false).load().keys(keys);
			for (Key<Person> mapKey : users.keySet()) {
				results.add(users.get(mapKey).toDto());
			}
		}
		
		return results;
	}
	
}
