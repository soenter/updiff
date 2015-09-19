package com.sand.sdrift.server.ui.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : sun.mt@sand.com.cn
 * @create : 2015/9/17 19:39
 * @since : 1.0.0
 */
public class SessionFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal (
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
//		//检查登陆状态
//		String uri = request.getRequestURI();
//		//只过滤.html结尾的请求
//		if (uri.indexOf(".html") == -1) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//		String servletPath = uri.substring(uri.lastIndexOf("/") + 1);
//
//		String[] notFilte = new String[]{
//				"",
//				"doLogin.html",
//				"login.html",
//				"user-not-login.html"
//		};
//		for (String string : notFilte) {
//			if (servletPath.equals(string)) {
//				filterChain.doFilter(request, response);
//				return;
//			}
//		}
//		Object user = request.getSession().getAttribute("");//TODO
//		if (user != null) {
//			filterChain.doFilter(request, response);
//		} else {
//			response.sendRedirect(request.getContextPath() + "/error/user-not-login.html");
//		}
	}
}
