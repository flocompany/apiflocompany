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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.flocompany.dao.impl.FriendImpl;
import com.flocompany.dao.impl.ParameterImpl;
import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.exception.NotAuthorizedException;
import com.flocompany.rest.exception.ResourceNotFindException;
import com.flocompany.rest.exception.TechnicalException;
import com.flocompany.rest.model.FriendDTO;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.util.MailUtil;
import com.google.appengine.repackaged.com.google.api.client.util.StringUtils;


/** Rest service for the Person resource
 * @author FC07315S
 *
 */
@Path("/friend")
public class FriendResource extends AbstractResource{


         
    private FriendDTO friend = new FriendDTO();
     
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
     
 
         
    
    
    @GET
    @Path("mylist")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<PersonDTO> myList(@QueryParam(ID) String id) {
    	List<PersonDTO> persons =  null;
    	List<Long> idPersons = FriendImpl.getInstance().findFriendByPerson(id);
    	if(idPersons.size()>0){
    		persons = UserImpl.getInstance().findUsersByIds(idPersons);
    	}else{
    		throw new ResourceNotFindException("Sorry, Not friends for the moment. You must add to use application");
    	}
        return persons;
    }
    
   
    
}
