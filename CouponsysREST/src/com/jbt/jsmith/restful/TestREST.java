/**
 * 
 */
package com.jbt.jsmith.restful;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class TestREST {

	@GET
	@Path("/{path}")
	public Response getMsg(@PathParam("path") String path, @QueryParam("param") String qparam) {
 
		String output = "Jersey say : " + path + " param "+ qparam;
 
		return Response.status(200).entity(output).build();
	}
	
	@POST
	public Response logon(@FormParam("userName") String userName, 
						  @FormParam("password") String password, 
						  @FormParam("clientType") int clientType,
						  @Context HttpServletRequest httpRequest,
						  @Context HttpServletResponse httpResponse
						  ) {
		
		CouponClientFacade aFacade=null;
		ClientType loginClientType = null;
		
		
		try {
			CouponSystem theCouponius=CouponSystem.getInstance();
			
			switch(clientType) {
				case 1: loginClientType=ClientType.Client;
					break;
				case 2: loginClientType=ClientType.Company;
					break;
				case 3: loginClientType=ClientType.Admin;
					break;
			}
			aFacade=theCouponius.login(userName, password, loginClientType);
			httpRequest.getSession(true).setAttribute("userFacade", aFacade);
			httpResponse.sendRedirect("http://localhost:8080/CouponsysREST/");
		}
		catch (CouponSystemException | IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		return Response.status(200).entity(httpResponse).build();
	}
}



