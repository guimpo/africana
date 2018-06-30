package edu.utfpr.africana.controller.plano;

import edu.utfpr.africana.dao.PlanoDao;
import edu.utfpr.africana.model.Plano;
import edu.utfpr.africana.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PlanoVerController", urlPatterns = {"/plano/ver"})
public class PlanoVerController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        Long id = Long.parseLong(request.getParameter("planoId"));
        
        Usuario u = (Usuario) request.getAttribute("usuario");
        
        Plano p = new PlanoDao().encontrar(id);
        
        request.setAttribute("plano", p);
        
        getServletContext()
                .getRequestDispatcher("/WEB-INF/view/plano/atualizarPlano.jsp")
                .forward(request, response);
    }
}
