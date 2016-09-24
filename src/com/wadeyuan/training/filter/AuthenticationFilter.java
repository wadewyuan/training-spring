package com.wadeyuan.training.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wadeyuan.training.common.Constants;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(
        urlPatterns = { "/*" },
        initParams = {
                @WebInitParam(name = "excludedURLs", value = "/login;/static;/logout;/rest;"),
                @WebInitParam(name = "loginPage", value = "/user/login")
        })
public class AuthenticationFilter implements Filter {

    private String loginPage = "";
    private List<String> excludedURLs;

    /**
     * Default constructor.
     */
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        String[] excludedURLsArray = fConfig.getInitParameter("excludedURLs").split(";");
        excludedURLs = new ArrayList<String>();
        for(String url : excludedURLsArray) {
            excludedURLs.add(url);
        }
        loginPage = fConfig.getInitParameter("loginPage");
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = request instanceof HttpServletRequest ? (HttpServletRequest) request : null;
        HttpServletResponse httpResponse = response instanceof HttpServletResponse ? (HttpServletResponse) response : null;

        if(httpRequest == null || httpResponse == null) {
            // pass the request along the filter chain if not a http servlet request
            chain.doFilter(request, response);
            return;
        }

        String requestURL = httpRequest.getRequestURL().toString();
        Boolean isExcluedURL = Boolean.FALSE;
        for(String url : excludedURLs) {
            if(requestURL.indexOf(url) >= 0) {
                isExcluedURL = Boolean.TRUE;
                break;
            }
        }

        if(isExcluedURL) {
            // pass the request along the filter chain if it is excludedURL
            chain.doFilter(request, response);
        } else {
            // for regular URLs, always check if the user objects is valid
            if(httpRequest.getSession().getAttribute(Constants.USER) == null) {
                String contextPath = httpRequest.getContextPath();
                String redirectTo = requestURL.substring(requestURL.indexOf(contextPath) + contextPath.length() + 1);
                if(httpRequest.getQueryString() != null && !httpRequest.getQueryString().isEmpty()) {
                    redirectTo = redirectTo + "?" + httpRequest.getQueryString();
                }
                redirectTo = URLEncoder.encode(redirectTo, "ISO-8859-1");
                if(redirectTo.length() > 0 && !redirectTo.equals("user/login")) {
                    httpResponse.sendRedirect(contextPath + loginPage + "?" + Constants.REDIRECT_TO + "=" + redirectTo);
                } else {
                    httpResponse.sendRedirect(contextPath + loginPage);
                }
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
        loginPage = "";
    }
}