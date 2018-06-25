/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author josevictor
 */
@WebFilter(filterName = "FiltroUsuarioLogado", urlPatterns = {"/a/*"})
public class FiltroUsuarioLogado implements Filter {
    private static final boolean debug = true;

    private FilterConfig filterConfig = null;
    private ServletContext context;
    
    public FiltroUsuarioLogado() {
    }
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        
//        HttpSession session = req.getSession(false);
//        
//        if (session == null || session.getAttribute("user") == null) {
//            this.context.log("Unauthorized access request");
//            chain.doFilter(request, response);
//        } else {
//            this.context.log("Unauthorized access request");
//            res.sendRedirect(req.getContextPath() + "/audio/lista");
//        }
    }

    public void destroy() {        
    }

    public void init(FilterConfig filterConfig) {        
//        this.context = filterConfig.getServletContext();
//        this.context.log("AuthenticationFilter initialized");
    }   
}
