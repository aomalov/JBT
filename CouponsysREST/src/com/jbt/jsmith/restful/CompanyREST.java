/**
 * 
 */
package com.jbt.jsmith.restful;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jbt.jsmith.CouponSystem;
import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dto.Company;
import com.jbt.jsmith.facade.AdminFacade;

/**
 * @author andrew
 *
 */
@Path("/company")
public class CompanyREST {
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getCompanies(@PathParam("id") long ID, @Context HttpServletRequest httpServletRequest) {
		Collection<Company> res=null;
 
		try {
			AdminFacade admin=(AdminFacade) httpServletRequest.getSession().getAttribute("userFacade");
			res= admin.getAllCompanies();
		}
		catch (CouponSystemException ex)
		{
			System.out.println(ex.getMessage());
		}
		return res;
	}
	
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
		
		System.out.println("[POST COMPANY] "+aComp);
 
		return Response.status(200).entity(aComp.toString()).build();
	}

}
