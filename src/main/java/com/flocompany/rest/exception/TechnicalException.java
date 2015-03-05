package com.flocompany.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class TechnicalException extends WebApplicationException {
    public TechnicalException(String message) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}