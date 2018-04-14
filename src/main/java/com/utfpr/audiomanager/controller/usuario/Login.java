/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller.usuario;

import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Usuario;
import com.utfpr.audiomanager.util.Hashing;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author paulo
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        getServletContext()
                .getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/plain;charset=UTF-8");
        
        String email = (String) request.getParameter("email");
        String senha = (String) request.getParameter("senha");
        
        try {
            Usuario resultUsuario = new UsuarioDao().getUsuarioByEmail(email);
            
            if (resultUsuario == null) {
                throw new Exception("email ou senha inválidos");
            }
            
            boolean isSenhaValid = Hashing.validateHashedPassword(senha, resultUsuario.getSenha());
            
            if (isSenhaValid) {
                response.getWriter().write("logado");
                return;
            } else {
                throw new Exception("email ou senha inválidos");
            }
        } catch(Exception e) {
            response.getWriter().write(e.getMessage());
        }
    }
}
