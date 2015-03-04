package com.flocompany.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResourceNotFindException extends WebApplicationException {
    public ResourceNotFindException(String message) {
        super(Response.status(Response.Status.FOUND)
            .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
