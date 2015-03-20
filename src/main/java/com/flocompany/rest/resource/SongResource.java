package com.flocompany.rest.resource;

import static com.flocompany.util.RestUtil.CATEGORY_CODE;
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
import com.flocompany.dao.impl.ParameterImpl;
import com.flocompany.dao.impl.SongImpl;
import com.flocompany.dao.impl.UserImpl;
import com.flocompany.rest.exception.BadRequestException;
import com.flocompany.rest.exception.ResourceNotFindException;
import com.flocompany.rest.exception.TechnicalException;
import com.flocompany.rest.model.CategoryDTO;
import com.flocompany.rest.model.ParameterDTO;
import com.flocompany.rest.model.PersonDTO;
import com.flocompany.rest.model.SongDTO;
import com.flocompany.rest.model.SongWrappedDTO;
import com.flocompany.util.EnumCategorySong;
import com.flocompany.util.RestUtil;
import com.flocompany.util.StringUtil;


/** Rest service for the Person resource
 * @author FC07315S
 *
 */
@Path("/song")
public class SongResource extends AbstractResource{


         
    private SongDTO song = new SongDTO();
     
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
    public SongWrappedDTO list(@QueryParam(CATEGORY_CODE) String codeCategory) {

    	SongWrappedDTO result = new SongWrappedDTO();
		List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
		List<SongDTO> songs = new ArrayList<SongDTO>();
    	
    	
    	ParameterDTO p = ParameterImpl.getInstance().findParametreDTO(RestUtil.CATEGORY_PARAMETER);
    	if(!p.getValue().equals(EnumCategorySong.NONE.getCode())){
    		String[] tabs = p.getValue().split(",");
    		for(int i=0;i<tabs.length;i++){
        		CategoryDTO c = new CategoryDTO(tabs[i], EnumCategorySong.get(tabs[i]).getLibelle());
        		categories.add(c);
    		}
    	}
    	
    	if(isNotEmpty(codeCategory)&&!EnumCategorySong.ALL.getCode().equals(codeCategory)){
    		songs = SongImpl.getInstance().findSongByCategory(codeCategory);
    	}else{
    		songs = SongImpl.getInstance().findAllSongs();
    	}
    	
    	result.setCategories(categories);
    	result.setSongs(songs);
        return result;
    }
    
    
}
