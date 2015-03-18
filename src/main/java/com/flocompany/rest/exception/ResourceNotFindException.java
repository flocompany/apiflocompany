package com.flocompany.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResourceNotFindException extends WebApplicationException {
    public ResourceNotFindException(String message) {
        super(Response.status(Response.Status.FOUND)
            .entity(message).header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN+"; charset=utf-8").type(MediaType.TEXT_PLAIN+"; charset=utf-8").build());
    
//        super(Response.status(Response.Status.FOUND)
//                .entity(message).header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN+"; charset=utf-8").header("Access-Control-Allow-Origin", "*")
//        		.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build());

    }
}