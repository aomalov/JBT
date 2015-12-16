package com.jbt.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class SessionFilter
 */
@WebFilter("/sessionFilter")
public class SessionFilter implements Filter {


	public void init(FilterConfig fConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session=((HttpServletRequest)request).getSession(false);
		boolean failedRequest=false;

		if(session==null) failedRequest=true;
		else if (session.getAttribute("userFacade")==null) failedRequest=true;
		
		if(failedRequest)
		{
			((HttpServletResponse)response).setContentType("application/json");
			PrintWriter out = ((HttpServletResponse)response).getWriter();
			out.println("{"+"\"error\":"+"\"You must log in!\"}");
			return;
		}
		chain.doFilter(request, response); 
	}

	public void destroy() {}

}
