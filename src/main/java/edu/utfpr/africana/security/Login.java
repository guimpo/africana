package edu.utfpr.africana.security;

import com.google.gson.Gson;
import edu.utfpr.africana.dao.UsuarioDao;
import edu.utfpr.africana.model.Usuario;
import edu.utfpr.africana.util.ETagUtil;
import edu.utfpr.africana.util.HashingUtil;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Login", urlPatterns = {"/usuario/entrar"})
public class Login extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.invalidate();
        
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
        
        // BufferedReader reader = request.getReader();
        // Usuario user = new Gson().fromJson(reader, Usuario.class);
        
        // String email = user.getEmail();
        // String senha = user.getSenha();

        // System.out.println(user);

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        try {
            
            Usuario resultUsuario = new UsuarioDao().getUsuarioByEmail(email);
            
            if (resultUsuario == null) {
                throw new Exception("email ou senha inválidos");
            }
            
            boolean isSenhaValid = HashingUtil.validateHashedPassword(senha, resultUsuario.getSenha());
            
            if (isSenhaValid) {
                session.setMaxInactiveInterval(5*60);
                session.setAttribute("user", resultUsuario);
                getServletContext()
                    .getRequestDispatcher("/WEB-INF/view/audio/lista.jsp")
                    .forward(request, response);
            } else {
                throw new Exception("email ou senha inválidos");
            }
//        }
//        catch(IllegalStateException ex) {
//            response.sendRedirect("../audio/lista");
        } catch(Exception e) {
            session.setAttribute("erMessage", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
