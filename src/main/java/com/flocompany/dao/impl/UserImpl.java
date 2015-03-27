package com.flocompany.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.flocompany.dao.model.Device;
import com.flocompany.dao.model.Person;
import com.flocompany.rest.model.DeviceDTO;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.rest.model.PersonWrappedDTO;
import com.flocompany.util.SecurityUtil;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
/**
 * Persist operation on the Person Entity
 * @author FCOU
 *
 */
public class UserImpl {
	static {
        ObjectifyService.register(Person.class);
        ObjectifyService.register(Device.class);
    }
	
	
	/** Transaction class for add user with devices
	 * @author FCOU
	 *
	 * @param <PersonDTO>
	 */
	public class PersonWork<PersonDTO> implements Work {
		
		private Person person;
		private Device device;
		
		public PersonWork(Person person, Device device) {
			super();
			this.person = person;
			this.device = device;
		}

		@Override
		public Object run() {
			
			Key<Person> keyPerson = ofy().save().entity(this.person).now(); 
			Person newPerson = ofy().load().key(keyPerson).now();
			Key<Person> key = Key.create(Person.class, (newPerson.getId()));
			Device d = new Device(device.getIdRegDevice(), device.getType(), key);
			ofy().save().entity(d).now(); 
			
	        return (PersonDTO) newPerson.toDto();
		}
		
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
		String pwd = "";
		try {
			pwd = SecurityUtil.hash256("toto");
			initUser("titteuf", "titteuf@gmail.com", pwd);
			initUser("tazman", "tazman@voila.fr", pwd);
			initUser("titi", "titi@yahoo.fr", pwd);
			initUser("tata32", "tata32@voila.fr", pwd);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
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
	
	/** Add a new Person Entity with device
	 * @param person
	 * @param idRegDevice
	 * @param type
	 * @return
	 */
	public PersonDTO addUserWithDevice(PersonDTO person, String idRegDevice, String type){
		Person p = new Person();
		p.initFromDTO(person);
		Device d= new Device(idRegDevice, type, null);
		
		PersonWork<PersonDTO> pw = new PersonWork<>(p,d);
		
		PersonDTO result = (PersonDTO) ofy().transact(pw);
    	
		return result;
	}
	
	
	
	/** update a Person Entity
	 * @param person
	 * @return
	 */
	public PersonDTO updateUser(PersonDTO person){
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
	
	/** Get all Users with devices
	 * @return
	 */
	public List<PersonWrappedDTO> findAllUsersWithDevices(){
		List<Person> users = ofy().cache(false).load().type(Person.class).ancestor(Key.create(Person.class, "Persons")).list();
		List<PersonWrappedDTO> results = new ArrayList<PersonWrappedDTO>();
		for(Person p : users){
			List<String> listRegId = new ArrayList<String>();
			for(DeviceDTO d : findDeviceByPerson(String.valueOf(p.getId()))){
				listRegId.add(d.getIdRegDevice());
			}
			PersonWrappedDTO dto = new PersonWrappedDTO(p.getId(), p.getFirstName(), p.getLastName(), p.getEmail(), p.getPseudo(), StringUtils.join(listRegId, ", "));
			results.add(dto);
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
		if(friendList.size()<=0){		
			if(deleteDevices(String.valueOf(id))){
				Key<Person> key = Key.create(Key.create(Key.create(Person.class, "Persons"), Person.class, id).getString());
				ofy().cache(false).delete().key(key).now();
				result=true;
			}
		}
		return result;
	}
	
	
	/** Get the person device list
	 * @param name
	 * @return
	 */
	public List<DeviceDTO> findDeviceByPerson(String idPerson){
		Key<Person> person = Key.create(Person.class, (Long.valueOf(idPerson)));
		List<Device> devices = ofy().cache(false).load().type(Device.class).ancestor(person).list();
		List<DeviceDTO> results = new ArrayList<DeviceDTO>();
		for(Device d : devices){
			results.add(d.toDto());
		}
		
		return results;
	}
	 
	/** Get person by his id
	 * @param id
	 * @return
	 */
	public PersonDTO findById(String id){
		PersonDTO result = null;
		Person p = ofy().cache(false).load().key(Key.create(Key.create(Person.class, "Persons"), Person.class, Long.valueOf(id))).now();
		if(p !=null){
			result = p.toDto();
		}
		return result;
	}
	
	
	
	/** Get a random person with not in his friend yet
	 * @param id
	 * @param friendListTosubscrire
	 * @return
	 */
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
	
	/**Add Device to a person
	 * @param idperson
	 * @return
	 */
	public long addDevice(String idperson, String idDevice, String type){
		Key<Person> person = Key.create(Person.class, Long.valueOf(idperson));
		Device d = new Device(idDevice, type, person);
		Key<Device> key = ofy().save().entity(d).now(); 
		return key.getId();
	}
	
	/**delete all Devices of a person
	 * @param idperson
	 * @return
	 */
	public boolean deleteDevices(String idPerson){
		Key<Person> person = Key.create(Person.class, Long.valueOf(idPerson));
		List<Device> devices = ofy().cache(false).load().type(Device.class).ancestor(person).list();
		for(Device d : devices){
			Key<Device> key = Key.create(person, Device.class, d.getId());
			ofy().cache(false).delete().key(key).now();
		}
		return true;
	}
	
	/**delete the Device
	 * @param idperson
	 * @return
	 */
	public boolean deleteDevice(String idPerson, String idDevice){
		Key<Person> person = Key.create(Person.class, Long.valueOf(idPerson));
		List<Device> devices = ofy().cache(false).load().type(Device.class).ancestor(person).filter("idRegDevice", idDevice).list();
		for(Device d : devices){
			Key<Device> key = Key.create(person, Device.class, d.getId());
			ofy().cache(false).delete().key(key).now();
		}
		return true;
	}
	
}
