package com.sand.updiff.ci.ui.filter;

import com.sand.updiff.ci.ui.Constants;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : sun.mt
 * @create : 2015/9/17 19:39
 * @since : 1.0.0
 */
public class SessionFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal (
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		Object sessionModel = request.getSession().getAttribute(Constants.SESS_USER_MODEL);
		if(sessionModel == null){

			String queryString;
			if(request.getQueryString() == null){
				queryString = "";
			} else {
				queryString = "?" + request.getQueryString();
			}
			response.sendRedirect("/users/login?to=" + request.getRequestURL() + queryString);
			return;
		}
		filterChain.doFilter(request, response);

	}



}
