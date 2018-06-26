/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller;

import com.google.gson.Gson;
import com.utfpr.audiomanager.controller.usuario.SessaoController;
import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Usuario;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josevictor
 */
@WebFilter(filterName = "FiltroAutenticacao", urlPatterns = {"/webservice/audio*"})
public class FiltroAutenticacao implements Filter {
    
    private static final boolean debug = true;

    public FiltroAutenticacao() {
    }    

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String header = httpRequest.getHeader("Authorization");
        try {
            if (header == null || !header.startsWith("Bearer ")) {
                throw new Exception("Authorization header precisa ser provido");
            }
            String token = header.substring("Bearer".length()).trim();
            Claims claims = new SessaoController().validaToken(token);
            if (claims == null) {
                throw new Exception("Token invalido");
            }
            String email = claims.getId();
            Usuario resultUsuario = new UsuarioDao().getUsuarioByEmail(email);
            httpRequest.setAttribute("usuario", resultUsuario);
            chain.doFilter(request, response);
        } catch (Exception e) {
            response.setContentType("application/json;charset=UTF-8");
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = httpResponse.getWriter();
            out.print(new Gson().toJson(new Exception("Não autorizado: " + e.getMessage())));
        }
    }

    public void destroy() {        
    }

    public void init(FilterConfig filterConfig) {
    }
    
}
