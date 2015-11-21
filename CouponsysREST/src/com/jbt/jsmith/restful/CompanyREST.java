/**
 * 
 */
package com.jbt.jsmith.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jbt.jsmith.ConnectionPool;
import com.jbt.jsmith.CouponDbHelper;
import com.jbt.jsmith.CouponSystem;
import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.DTO.Company;
import com.jbt.jsmith.FACADE.AdminFacade;

/**
 * @author andrew
 *
 */
@Path("/company")
public class CompanyREST {
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@PathParam("id") long ID) {
		Company res=null;
 
		try {
			CouponSystem theCouponius=CouponSystem.getInstance();
			
			AdminFacade admin=(AdminFacade)theCouponius.login("Admin", "1234", ClientType.Admin);
			return admin.getCompany(ID);
		}
		catch (CouponSystemException ex)
		{
	
		}
		return res;
	}
	
	@GET
	@Path("/{id}/edit")
	@Produces(MediaType.TEXT_HTML)
	public Response editCompany(@PathParam("id") long ID) {
		Company aComp = null;
 
		try {
			CouponSystem theCouponius=CouponSystem.getInstance();
			
			AdminFacade admin=(AdminFacade)theCouponius.login("Admin", "1234", ClientType.Admin);
			aComp= admin.getCompany(ID);
		}
		catch (CouponSystemException ex)
		{
			return Response.status(200).entity(ex.getMessage()).build();
		}
		return Response.status(200).entity(aComp.toString()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response postCompany(Company aComp) {
 
		return Response.status(200).entity(aComp.toString()).build();
	}
	

}
