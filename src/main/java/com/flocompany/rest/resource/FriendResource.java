package com.flocompany.rest.resource;

import static com.flocompany.util.RestUtil.ID;
import static com.flocompany.util.RestUtil.PWD;
import static com.flocompany.util.RestUtil.ID_APPLICANT;
import static com.flocompany.util.RestUtil.ID_PERSON;
import static com.flocompany.util.RestUtil.ID_FRIEND;
import static com.flocompany.util.RestUtil.STATUS;
import static com.flocompany.util.RestUtil.PUB_CONTENT_PARAMETER;
import static com.flocompany.util.RestUtil.PUB_PARAMETER;
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
import com.flocompany.dao.impl.ParameterImpl;
import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.exception.BadRequestException;
import com.flocompany.rest.exception.NotAcceptableException;
import com.flocompany.rest.exception.PublicityException;
import com.flocompany.rest.exception.ResourceNotFindException;
import com.flocompany.rest.exception.TechnicalException;
import com.flocompany.rest.model.FriendDTO;
import com.flocompany.rest.model.FriendWrappedDTO;
import com.flocompany.rest.model.ParameterDTO;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.util.RestUtil;


/** Rest service for the Person resource
 * @author FCOU
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
    public List<FriendWrappedDTO> myList(@QueryParam(ID) String id) {
    	
    	
    	sendPublicity(id);
    	
    	
    	//recupération de la liste
    	List<FriendWrappedDTO> results = FriendImpl.getInstance().findFriendWrappedDTOByIdperson(id);
    	if(results.size()<=0){
    		throw new ResourceNotFindException("Sorry, no friends for the moment. You must add to use application");
    	}
        return results;
    }
    
    /** Rest Service witch allow to register a new Friend entity in the application
     * @param personParams
     * @return
     */
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO add(MultivaluedMap<String, String> friendParams) {
		String idApplicant = friendParams.getFirst(ID_APPLICANT);
		String idPerson = friendParams.getFirst(ID_PERSON);
		PersonDTO newFriend = null;
		if (isEmpty(idApplicant) || isEmpty(idPerson)
				|| isBlank(idApplicant) || isBlank(idPerson) ) {
			throw new BadRequestException(
					"Missing parameters");
		}
		
		if(idApplicant.equals(idPerson)){
			throw new BadRequestException(
					"You can't add yourself.");
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
    
    
    /** Rest Service witch allow to register a new User in the application
     * @param personParams
     * @return
     */
    @POST
    @Path("addrandom")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO addRandom(MultivaluedMap<String, String> friendParams) {
		String idApplicant = friendParams.getFirst(ID_APPLICANT);
		PersonDTO newFriend = null;
		if (isEmpty(idApplicant)
				|| isBlank(idApplicant) ) {
			throw new BadRequestException(
					"Missing parameters");
		}


		PersonDTO personApplicant = UserImpl.getInstance().findById(idApplicant);
		if (personApplicant == null) {
			throw new BadRequestException(
					"Applicant parameter do not exist");
		}
		
		
		//get all friends
		List<Long> myFriendsId = FriendImpl.getInstance().findFriendIdsByPerson(idApplicant);
		myFriendsId.add(personApplicant.getId());
		//get random person not in existing friends
		PersonDTO result = UserImpl.getInstance().findRandomById(idApplicant, myFriendsId);
		
		if (result==null){
			throw new ResourceNotFindException("No random friend find.");
		}else{
			FriendImpl.getInstance().addFriend(idApplicant, String.valueOf(result.getId()));
		}
		return result;
    }
    
    
    /** Rest Service witch allow to update the Friend status in the application
     * @param personParams
     * @return
     */
    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public void update(MultivaluedMap<String, String> friendParams) {
		String id = friendParams.getFirst(ID);
		String pwd = friendParams.getFirst(PWD);
		testPrivilege(id, pwd);
		String idFriend = friendParams.getFirst(ID_FRIEND);
		String status = friendParams.getFirst(STATUS);
		if (isEmpty(idFriend) || isEmpty(status)
				|| isBlank(idFriend) || isBlank(status) ) {
			throw new BadRequestException(
					"Missing parameters");
		}
		long result = 0;
		FriendDTO friend = FriendImpl.getInstance().findById(idFriend);
		if(friend!=null){
			if(RestUtil.BLOCKED.equals(status)){
				if(!friend.getStatus().equals(RestUtil.BLOCKED)){
					friend.setStatus(status);
					friend.setIdBlocker(Long.valueOf(id));
					result = FriendImpl.getInstance().updateFriend(friend);
				}
			}else if(RestUtil.ACCEPTED.equals(status)){
				if(friend.getStatus().equals(RestUtil.BLOCKED)){
					if(friend.getIdBlocker()==null){
						friend.setStatus(status);
						friend.setIdBlocker(null);
						result = FriendImpl.getInstance().updateFriend(friend);
					}else if (friend.getIdBlocker().equals(Long.valueOf(id))){
						friend.setStatus(status);
						friend.setIdBlocker(null);
						result = FriendImpl.getInstance().updateFriend(friend);
					}else{
						throw new NotAcceptableException("You cannot active this friend because he blocked you.");
					}
				}
			}
		}
		if (result==-1){
			throw new TechnicalException("Sorry, Internal error was occured.");
		}
    }
    
    /** Rest Service witch allow to delete a friend resource in the application
     * @param personParams
     * @return
     */
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(MultivaluedMap<String, String> friendParams) {
		String id = friendParams.getFirst(ID);
		String pwd = friendParams.getFirst(PWD);
		testPrivilege(id, pwd);
		String idFriend = friendParams.getFirst(ID_FRIEND);
		if (isEmpty(idFriend) || isBlank(idFriend) ) {
			throw new BadRequestException(
					"Missing parameters");
		}

		boolean result = FriendImpl.getInstance().delete(Long.valueOf(idFriend));

		if (!result){
			throw new TechnicalException("Sorry, Internal error was occured.");
		}
    }
    
}
