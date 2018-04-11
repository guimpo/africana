/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller.usuario;

import com.utfpr.audiomanager.dao.PapelDao;
import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Papel;
import com.utfpr.audiomanager.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josevictor
 */
@WebServlet(name = "Teste", urlPatterns = {"/teste"})
public class Teste extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CadastroUsuario</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CadastroUsuario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
            UsuarioDao ud = new UsuarioDao();
            Usuario u = new Usuario();
            u.setEmail("99");
            u.setSenha("99");
            ud.salvar(u);
            
            PapelDao pd = new PapelDao();
            Papel p = new Papel();
            p.setEmail("admin");
            p.setPapel("admin");
            pd.salvar(p);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
