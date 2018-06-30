package edu.utfpr.africana.security;

import com.google.gson.Gson;
import edu.utfpr.africana.dao.UsuarioDao;
import edu.utfpr.africana.model.Usuario;
import edu.utfpr.africana.util.HashingUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Login", urlPatterns = {"/api/login"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println((char)27 + "[33m" + "login");
        
        response.setContentType("application/json;charset=UTF-8");
        
        BufferedReader reader = request.getReader();
        Usuario userReceived = new Gson().fromJson(reader, Usuario.class);
        
        System.out.println(userReceived);
                        
        Usuario userStored = new UsuarioDao().getUsuarioByEmail(userReceived.getEmail());

        if (userStored == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(new Gson().toJson(new Exception("Usuário ou senha inválido.")));
        }
        
        boolean isValidPw = 
            HashingUtil.validateHashedPassword(userReceived.getSenha(),
            userStored.getSenha());

        if (isValidPw) {
            String tokenString = userReceived.getEmail() + ":" + userReceived.getSenha();
            tokenString = Base64.getEncoder().encodeToString(tokenString.getBytes());
            Cookie c = new Cookie("credentials", tokenString);
            c.setMaxAge(-1);
            c.setPath("/");
            response.addCookie(c);            
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(new Gson().toJson(new Exception("Usuário ou senha inválido.")));
        }
    }
}
