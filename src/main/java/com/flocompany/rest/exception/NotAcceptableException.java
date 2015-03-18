package com.flocompany.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NotAcceptableException extends WebApplicationException {
    public NotAcceptableException(String message) {
        super(Response.status(Response.Status.NOT_ACCEPTABLE)
                .entity(message).header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN+"; charset=utf-8").type(MediaType.TEXT_PLAIN+"; charset=utf-8").build());
    }
}


