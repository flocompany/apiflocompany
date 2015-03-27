package com.flocompany.dao.model;

import com.flocompany.rest.model.PersonDTO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class Person {
 

    
	@Id Long id;
    @Index String pseudo;
    @Index String email;
    private String pwd;
    private String firstName;
    private String lastName;
    private int accessHomeCount=0;

	@Parent Key<Person> parent  = Key.create(Person.class, "Persons");
	
    public Person() {
    }
    
    public Person(String pseudo, String email, String pwd) {
    	super();
        this.pseudo = pseudo;
        this.email = email;
        this.pwd = pwd;
    }
 
    public Person(long id, String firstName, String lastName, String email, String pseudo) {
 
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pseudo = pseudo;
    }
    
    
    public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

	public int getAccessHomeCount() {
		return accessHomeCount;
	}

	public void setAccessHomeCount(int accessHomeCount) {
		this.accessHomeCount = accessHomeCount;
	}
     

     public PersonDTO toDto(){
    	 PersonDTO p = new PersonDTO(pseudo, email, pwd);
    	 p.setId(this.id);
    	 p.setAccessHomeCount(this.accessHomeCount);
    	 return p;
     }
  

     public void initFromDTO(PersonDTO dto){
    	 this.id=dto.getId();
    	 this.pseudo=dto.getPseudo();
    	 this.email=dto.getEmail();
    	 this.pwd=dto.getPwd();
    	 this.accessHomeCount=dto.getAccessHomeCount();
     }
}
