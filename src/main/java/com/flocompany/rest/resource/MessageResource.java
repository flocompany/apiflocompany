package com.flocompany.rest.resource;

import static com.flocompany.util.RestUtil.ID_FRIEND_MESSAGE;
import static com.flocompany.util.RestUtil.PSEUDO;
import static com.flocompany.util.StringUtil.isBlank;
import static com.flocompany.util.StringUtil.isEmpty;
import static com.flocompany.util.StringUtil.isNotEmpty;

import java.util.ArrayList;
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
import com.flocompany.dao.impl.MessageImpl;
import com.flocompany.dao.impl.ParameterImpl;
import com.flocompany.dao.impl.SongImpl;
import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.exception.BadRequestException;
import com.flocompany.rest.exception.ResourceNotFindException;
import com.flocompany.rest.exception.TechnicalException;
import com.flocompany.rest.model.CategoryDTO;
import com.flocompany.rest.model.MessageDTO;
import com.flocompany.rest.model.ParameterDTO;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.rest.model.SongDTO;
import com.flocompany.rest.model.SongWrappedDTO;
import com.flocompany.util.EnumCategorySong;
import com.flocompany.util.RestUtil;
import com.flocompany.util.StringUtil;


/** Rest service for the Message resource
 * @author FC07315S
 *
 */
@Path("/message")
public class MessageResource extends AbstractResource{


         
    private MessageDTO song = new MessageDTO();
     
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
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<MessageDTO> list(@QueryParam(ID_FRIEND_MESSAGE) String idFriend) {

    	System.out.println("idFriend" + idFriend);
    	List<MessageDTO> messages = MessageImpl.getInstance().findAllMessagesByFriend(idFriend);
        return messages;
    }
    
    
}
