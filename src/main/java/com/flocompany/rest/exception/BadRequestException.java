package com.flocompany.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class BadRequestException extends WebApplicationException {
    public BadRequestException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(message).header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN+"; charset=utf-8").type(MediaType.TEXT_PLAIN+"; charset=utf-8").build());
    }
}
