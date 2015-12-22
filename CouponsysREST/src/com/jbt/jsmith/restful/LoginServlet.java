/**
 * 
 */
package com.jbt.jsmith.restful;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jbt.jsmith.CouponSystem;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.facade.CouponClientFacade;

/**
 * @author andrew
 *
 */
@Path("/logon")
public class LoginServlet {

	@GET
	@Path("/{path}")
	public Response getMsg(@PathParam("path") String path, @QueryParam("param") String qparam) {
 
		String output = "Jersey say : " + path + " param "+ qparam;
 
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestJsonMessage logon(RestJsonUser aUser,
						  @Context HttpServletRequest httpRequest,
						  @Context HttpServletResponse httpResponse) {
		
		CouponClientFacade aFacade=null;		
		
		try {
			CouponSystem theCouponius=CouponSystem.getInstance();
			
			aFacade=theCouponius.login(aUser.getUserName(), aUser.getPassword(), ClientType.valueOf(aUser.getClientType()));
			httpRequest.getSession(true).setAttribute("userFacade", aFacade);
			httpResponse.sendRedirect("http://localhost:8080/CouponsysREST/");
			return new RestJsonMessage("success","You are logged in as "+aUser.getUserName());
		}
		catch (CouponSystemException | IOException ex)
		{
			System.out.println(ex.getMessage());
			return new RestJsonMessage("danger",ex.getMessage());
		}
	}
}



