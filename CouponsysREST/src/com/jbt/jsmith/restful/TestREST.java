/**
 * 
 */
package com.jbt.jsmith.restful;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author andrew
 *
 */
@Path("/hello")
public class TestREST {

	@GET
	@Path("/{path}")
	public Response getMsg(@PathParam("path") String path, @QueryParam("param") String qparam) {
 
		String output = "Jersey say : " + path + " param "+ qparam;
 
		return Response.status(200).entity(output).build();
	}
}



