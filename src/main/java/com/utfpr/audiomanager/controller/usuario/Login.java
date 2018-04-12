/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller.usuario;

import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Usuario;
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
        
        Usuario u = new Usuario();
        try {
            u.setEmail(email);
            u.setSenha(senha);
            
            List<Usuario> l = new UsuarioDao().getList();
            // garantir que tabela usuário sempre tenha um usuário
            for(Usuario uu : l) {
                if(uu.getEmail().equalsIgnoreCase(email) && uu.getSenha().equalsIgnoreCase(u.getSenha())) {                   
                    response.getWriter().write("logado");
                    return;
                }
            }
            response.getWriter().write("email ou senha inválidos");
        } catch(Exception e) {
            response.getWriter().write(e.getMessage());
        }
    }
}
