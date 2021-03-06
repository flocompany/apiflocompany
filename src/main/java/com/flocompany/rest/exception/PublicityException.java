package com.flocompany.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class PublicityException extends WebApplicationException {
    public PublicityException(String message) {
        super(Response.status(Response.Status.PAYMENT_REQUIRED)
                .entity(message).header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN+"; charset=utf-8").type(MediaType.TEXT_PLAIN+"; charset=utf-8").build());
    }
}
