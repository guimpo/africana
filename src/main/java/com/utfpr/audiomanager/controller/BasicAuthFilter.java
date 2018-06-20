
package com.utfpr.audiomanager.controller;

import com.google.gson.Gson;
import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Usuario;
import com.utfpr.audiomanager.util.HashingUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "BasicAuthFilter", urlPatterns = {"/api/audio2/*"})
public class BasicAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        try {
            String header = httpRequest.getHeader("Authorization");
            header.substring(0, 6).equals("Basic ");
            String basicAuthEncoded = header.substring(6);
            String basicAuthAsString = new String(
                            Base64.getDecoder().decode(basicAuthEncoded.getBytes()));
            Usuario resultUsuario = new UsuarioDao()
                    .getUsuarioByEmail(basicAuthAsString.split(":")[0]); 

            boolean isSenhaValid = HashingUtil
                        .validateHashedPassword(basicAuthAsString.split(":")[1],
                                resultUsuario.getSenha());

            if (isSenhaValid) {
                httpRequest.setAttribute("usuario", resultUsuario);
                chain.doFilter(request,response);
            }
            
        } catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
            response.setContentType("application/json;charset=UTF-8");
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = httpResponse.getWriter();
            out.print(new Gson().toJson(new Exception("Não autorizado")));
        }     
    }

    @Override
    public void destroy() { }
    
}
