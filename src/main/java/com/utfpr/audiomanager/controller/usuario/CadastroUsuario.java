package com.utfpr.audiomanager.controller.usuario;

import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Usuario;
import com.utfpr.audiomanager.util.ETagUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author josevictor
 */
@WebServlet(name = "CadastroUsuario", urlPatterns = {"/usuario/cadastro"})
public class CadastroUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String eTagFromBrowser = request.getHeader("If-None-Match");
        String eTagFromServer = ETagUtil.get(request, "WEB-INF/view/usuario/cadastroUsuario.jsp");

        if (eTagFromServer.equals(eTagFromBrowser)) {
            // retornar código 304
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }

        response.addHeader("ETag", ETagUtil.get(request, "WEB-INF/view/usuario/cadastroUsuario.jsp"));
        
        response.setContentType("text/html;charset=UTF-8");
        getServletContext()
                .getRequestDispatcher("/WEB-INF/view/usuario/cadastroUsuario.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nome = (String) request.getParameter("nome");
        String email = (String) request.getParameter("email");
        String senha = (String) request.getParameter("senha");
        HttpSession session = request.getSession();
        
        Usuario u = new Usuario();
        try {
            u.setNome(nome);
            u.setEmail(email);
            u.setSenha(senha);
            
            List<Usuario> l = new UsuarioDao().getList();
            // garantir que tabela usuário sempre tenha um usuário
            for(Usuario uu : l) {
                if(uu.getEmail() != null && uu.getEmail().equalsIgnoreCase(email)) {
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().write("email já cadastrado");
                    return;
                }
            }
            new UsuarioDao().salvar(u);
            session.setAttribute("su-message", "Usuario cadastrado com sucesso!");
            response.sendRedirect("entrar");
        } catch(Exception e) {
            session.setAttribute("er-message", e.getMessage());
            response.sendRedirect("cadastro");
        }    
    }
}
