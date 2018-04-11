/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller.usuario;

import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "Login", urlPatterns = {"/Login"})
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
        
        String email = (String) request.getAttribute("email");
        String senha = (String) request.getAttribute("senha");
        
        List<Usuario> l = new UsuarioDao().getList();
        for(Usuario u : l) {
            
        }
        
        
    }
}
