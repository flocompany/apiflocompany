package com.flocompany.rest.resource;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.flocompany.util.ResponseCorsFilter;

@ApplicationPath("/")
public class SongSendApplication extends ResourceConfig  {
	public SongSendApplication() {
 
		packages("com.flocompany.rest.resource");
        // Register my custom provider - not needed if it's in my.package.
        register(ResponseCorsFilter.class);
    }
	
	
}
