package com.flocompany.rest.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class PersonDTO {
 
	private long id;
    private String pseudo;
    private String email;
    private String pwd;
    private String firstName;
    private String lastName;
    private int accessHomeCount;
         
    public PersonDTO() {}
    
    public PersonDTO(String pseudo, String email, String pwd) {
    	super();
        this.pseudo = pseudo;
        this.email = email;
        this.pwd = pwd;
    }
 
    public PersonDTO(long id, String firstName, String lastName, String email, String pseudo) {
 
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
    
    public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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
    
         
}
