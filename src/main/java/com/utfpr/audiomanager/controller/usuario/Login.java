package com.utfpr.audiomanager.controller.usuario;

import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Usuario;
import com.utfpr.audiomanager.util.ETagUtil;
import com.utfpr.audiomanager.util.HashingUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Login", urlPatterns = {"/usuario/entrar"})
public class Login extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String eTagFromBrowser = request.getHeader("If-None-Match");
        String eTagFromServer = ETagUtil.get(request, "/WEB-INF/view/usuario/login.jsp");

        if (eTagFromServer.equals(eTagFromBrowser)) {
            // retornar código 304
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }

        response.addHeader("ETag", ETagUtil.get(request, "/WEB-INF/view/usuario/login.jsp"));
        
        response.setContentType("text/html;charset=UTF-8");
        getServletContext()
                .getRequestDispatcher("/WEB-INF/view/usuario/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        // TODO: adicionar no filtro !
        response.setContentType("text/plain;charset=UTF-8");
        
        String email = (String) request.getParameter("email");
        String senha = (String) request.getParameter("senha");
        
        try {
            Usuario resultUsuario = new UsuarioDao().getUsuarioByEmail(email);
            
            if (resultUsuario == null) {
                throw new Exception("email ou senha inválidos");
            }
            
            boolean isSenhaValid = HashingUtil.validateHashedPassword(senha, resultUsuario.getSenha());
            
            if (isSenhaValid) {
                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }
                HttpSession newSession = request.getSession(true);
                newSession.setMaxInactiveInterval(5*60);
                newSession.setAttribute("user", resultUsuario);
                session.setAttribute("suMessage", "Bem Vindo");
                response.sendRedirect("../audio/lista");
            } else {
                throw new Exception("email ou senha inválidos");
            }
        } catch(IllegalStateException ex) {
            response.sendRedirect("../audio/lista");
        } catch(Exception e) {
            HttpSession lastSession = request.getSession(false);
            if (lastSession != null) {
                lastSession.setAttribute("erMessage", e.getMessage());
            }
            response.sendRedirect("entrar");
        }
    }
}
