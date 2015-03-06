package com.flocompany.rest.resource;

import static com.flocompany.util.RestUtil.*;
import static com.flocompany.util.StringUtil.*;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.flocompany.dao.impl.ParameterImpl;
import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.exception.NotAuthorizedException;
import com.flocompany.rest.exception.ResourceNotFindException;
import com.flocompany.rest.exception.TechnicalException;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.util.MailUtil;
import com.google.appengine.repackaged.com.google.api.client.util.StringUtils;


/** Rest service for the Person resource
 * @author FC07315S
 *
 */
@Path("/person")
public class PersonResource extends AbstractResource{


         
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
    	
    	
        return "Demo service is ready!";
    }
 
    @GET
    @Path("sample")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO getSamplePerson() {
         
        System.out.println("Returning sample person: " + person.getFirstName() + " " + person.getLastName());
         
        return person;
    }
         
    
    
    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public PersonDTO findPerson(@QueryParam(PSEUDO) String pseudo) {
    	System.out.println("findperson >>>>>>>>>>>>>>>>>>>>>" + pseudo);
    	PersonDTO  person = UserImpl.getInstance().findUserByPseudo(pseudo);
    	if(person==null){
    		person = UserImpl.getInstance().findUserByEmail(pseudo);
    		if(person==null){
	    		throw new ResourceNotFindException(
						"Sorry, not user found for " + pseudo +".");
    		}
    	}
    	
        return person;
    }
    
    /** Rest Service witch allow to register a new User in the application
     * @param personParams
     * @return
     */
    @POST
    @Path("signup")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO signUpPerson(MultivaluedMap<String, String> personParams) {
		String pseudo = personParams.getFirst(PSEUDO);
		String mail = personParams.getFirst(MAIL);
		String pwd = personParams.getFirst(PWD);

		if (isEmpty(pseudo) || isEmpty(mail) || isEmpty(pwd)
				|| isBlank(pseudo) || isBlank(mail) || isBlank(pwd)) {
			throw new NotAuthorizedException(
					"Sorry, all fiels must be enter");
		}

		if (!isValidMail(mail)) {
			throw new NotAuthorizedException(
					"Sorry, it is not a valid email");
		}

		PersonDTO personPseudo = UserImpl.getInstance().findUserByPseudo(
				pseudo);
		if (personPseudo != null) {
			throw new NotAuthorizedException(
					"Sorry, pseudo already exist!!");
		}
		
		PersonDTO personMail = UserImpl.getInstance().findUserByEmail(mail);
		if (personMail != null) {
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
					ParameterImpl.getInstance().getValueByName(MAIL_ADMIN),
					"WELCOME TO SONGSEND",
					"Hello "
							+ person.getPseudo()
							+ " !!, We confirm your new account in the application. Best regards.");
			mailToSend.envoieMails();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return person; 
    }
    
    
    /** Rest Service witch authenticate the user
     * @param personParams
     * @return
     */
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO logInPerson(
            MultivaluedMap<String, String> personParams
            ) {
			String pseudoOrMail = personParams.getFirst(PSEUDO);
			String pwd = personParams.getFirst(PWD);

			if (isEmpty(pseudoOrMail) || isEmpty(pwd) || isBlank(pseudoOrMail)	|| isBlank(pwd)) {
				throw new NotAuthorizedException(
						"Sorry, all fiels must be enter.");
			}
			
			person = UserImpl.getInstance().findUserByPseudo(pseudoOrMail);
			if (person != null) {
				if (!pwd.equals(person.getPwd())) {
					throw new NotAuthorizedException("Sorry, bad password.");
				}
			} else {
				person = UserImpl.getInstance().findUserByEmail(pseudoOrMail);
				if (person != null) {
					if (!pwd.equals(person.getPwd())) {
						throw new NotAuthorizedException("Sorry, bad password.");
					}
				} else {
					throw new NotAuthorizedException(
							"Sorry, pseudo or mail not exist.");
				}
			}

//			testPrivilege(String.valueOf(person.getId()), person.getPwd());

        return person;   
    }
    
}
