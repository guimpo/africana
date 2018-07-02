package edu.utfpr.africana.controller.usuario;

import com.google.gson.Gson;
import edu.utfpr.africana.dao.UsuarioDao;
import edu.utfpr.africana.model.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josevictor
 */
@WebServlet(name = "CadastroUsuario", urlPatterns = {"/api/usuario"})
public class CadastroUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println((char)27 + "[33m" + "login");
        
        response.setContentType("application/json;charset=UTF-8");
        
        BufferedReader reader = request.getReader();
        Usuario userReceived = new Gson().fromJson(reader, Usuario.class);
        
        System.out.println(userReceived);
        
        Usuario u = new Usuario();
        try {
            u.setNome(userReceived.getNome());
            u.setEmail(userReceived.getEmail());
            u.setSenha(userReceived.getSenha());
            new UsuarioDao().salvar(u);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(new Gson().toJson(ex));
        }
    }
}
