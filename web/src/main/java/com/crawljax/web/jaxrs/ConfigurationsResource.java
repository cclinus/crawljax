package com.crawljax.web.jaxrs;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.crawljax.web.model.*;
import com.google.inject.Inject;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/rest/configurations")
public class ConfigurationsResource {

	private final Configurations configurations;
	
	@Inject
	ConfigurationsResource(Configurations configurations) {
        this.configurations = configurations;
    }
	
	@GET
	public Response getConfigurations() {
		return Response.ok(configurations.getConfigList()).build();
	}
	
	@POST
	public Response addConfiguration(Configuration config){
		String id = config.getName().replaceAll("/[^a-z0-9]+/g", "-");
		config.setId(id);
		configurations.getConfigList().put(id, config);
		return Response.ok(config).build();
	}
	
	@GET
	@Path("/new")
	public Response getNewConfiguration() {
		Configuration config = new Configuration();
		return Response.ok(config).build();
	}
	
	@GET
	@Path("{id}")
	public Response getConfiguration(@PathParam("id") String id) {
		Configuration config = configurations.findByID(id);
		if (config != null) return Response.ok(config).build();
		else return Response.serverError().build();
	}
	
	@PUT
	@Path("{id}")
	public Response updateConfiguration(Configuration config) {
		config.setLastModified(new Date());
		configurations.getConfigList().put(config.getId(), config);	
		return Response.ok(config).build();
	}
}