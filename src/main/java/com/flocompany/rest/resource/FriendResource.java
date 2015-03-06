package com.flocompany.rest.resource;

import static com.flocompany.util.RestUtil.ID;
import static com.flocompany.util.RestUtil.ID_APPLICANT;
import static com.flocompany.util.RestUtil.ID_PERSON;
import static com.flocompany.util.StringUtil.isBlank;
import static com.flocompany.util.StringUtil.isEmpty;

import java.util.List;

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
import javax.ws.rs.core.UriInfo;

import com.flocompany.dao.impl.FriendImpl;
import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.exception.BadRequestException;
import com.flocompany.rest.exception.ResourceNotFindException;
import com.flocompany.rest.exception.TechnicalException;
import com.flocompany.rest.model.FriendDTO;
import com.flocompany.rest.model.PersonDTO;


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
    
    /** Rest Service witch allow to register a new User in the application
     * @param personParams
     * @return
     */
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO signUpPerson(MultivaluedMap<String, String> friendParams) {
		String idApplicant = friendParams.getFirst(ID_APPLICANT);
		String idPerson = friendParams.getFirst(ID_PERSON);
		PersonDTO newFriend = null;
		if (isEmpty(idApplicant) || isEmpty(idPerson)
				|| isBlank(idApplicant) || isBlank(idPerson) ) {
			throw new BadRequestException(
					"Missing parameters");
		}


		PersonDTO personApplicant = UserImpl.getInstance().findById(idApplicant);
		if (personApplicant == null) {
			throw new BadRequestException(
					"Applicant parameter do not exist");
		}
		
		PersonDTO person = UserImpl.getInstance().findById(idPerson);
		if (person == null) {
			throw new BadRequestException(
					"Person parameter do not exist");
		}

		long result = FriendImpl.getInstance().addFriend(idApplicant, idPerson);

		if (result==-1){
			throw new TechnicalException("Save data error.");
		}else{
			newFriend = UserImpl.getInstance().findById(idApplicant);
		}
		return newFriend;
    }
    
}
