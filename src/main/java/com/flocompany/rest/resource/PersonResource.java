package com.flocompany.rest.resource;

import static com.flocompany.util.RestUtil.*;
import static com.flocompany.util.StringUtil.*;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.exception.NotAuthorizedException;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.util.MailUtil;
import com.google.appengine.repackaged.com.google.api.client.util.StringUtils;


@Path("/person")
public class PersonResource {


         
    private PersonDTO person = new PersonDTO();
     
    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;
 
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    Request request;
     
    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
    	
//    	UserInterfaceImpl u = new UserInterfaceImpl();
//    	u.createUser();
    	
    	
        return "Demo service is ready!";
    }
 
    @GET
    @Path("sample")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO getSamplePerson() {
         
        System.out.println("Returning sample person: " + person.getFirstName() + " " + person.getLastName());
         
        return person;
    }
         
    // Use data from the client source to create a new Person object, returned in JSON format. 
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO postPerson(
            MultivaluedMap<String, String> personParams
            ) {
         
        String pseudo = personParams.getFirst(PSEUDO);
        String mail = personParams.getFirst(MAIL);
        String pwd = personParams.getFirst(PWD);
         
        System.out.println("Storing posted " + pseudo + " " + mail + "  " + pwd);
         
        person.setPseudo(pseudo);
        person.setEmail(mail);
        person.setPseudo(pwd);
         
        System.out.println("person info: " + person.getFirstName() + " " + person.getLastName() + " " + person.getEmail());
         
        return person;
                         
    }
    
    
    // Use data from the client source to create a new Person object, returned in JSON format. 
    @POST
    @Path("signup")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO signUpPerson(
            MultivaluedMap<String, String> personParams
            ) {
         
        String pseudo = personParams.getFirst(PSEUDO);
        String mail = personParams.getFirst(MAIL);
        String pwd = personParams.getFirst(PWD);
        
        
        if(isEmpty(pseudo)||isEmpty(mail)||isEmpty(pwd)||isBlank(pseudo)||isBlank(mail)||isBlank(pwd)){
        	throw new NotAuthorizedException("Sorry, all fiels must be enter");
        }
        
        if(!isValidMail(mail)){
        	throw new NotAuthorizedException("Sorry, it is not a valid email");
        }
        
        List<PersonDTO> personPseudo = UserImpl.getInstance().findUserByPseudo(pseudo);
        if(personPseudo.size()>0){
            throw new NotAuthorizedException("Sorry, pseudo already exist!!");
        }
        List<PersonDTO> personMail = UserImpl.getInstance().findUserByEmail(mail);
        if(personMail.size()>0){
            throw new NotAuthorizedException("Sorry, email already exist!!");
        }
        
        person.setPseudo(pseudo);
        person.setEmail(mail);
        person.setPwd(pwd);

        person = UserImpl.getInstance().addUser(person);
        
        // Send mail
		List<String> mailTo = new ArrayList<String>();
		mailTo.add(mail);
		try {
			MailUtil mailToSend = new MailUtil(
					mailTo,
					null,
					null,
					null,
					"SongSend : ",
					"Welcome "
							+ person.getPseudo()
							+ " !!, We confirm your new account in the application. Best regards.");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

        return person;
                         
    }
}
