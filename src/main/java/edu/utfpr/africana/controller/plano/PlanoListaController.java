package edu.utfpr.africana.controller.plano;

import com.google.gson.Gson;
import edu.utfpr.africana.dao.PlanoDao;
import edu.utfpr.africana.dao.UsuarioDao;
import edu.utfpr.africana.model.Plano;
import edu.utfpr.africana.model.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PlanoListaController", urlPatterns = {"/plano/lista"})
public class PlanoListaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
//        Usuario usuario = (Usuario) request.getAttribute("usuario");
        Usuario u = new UsuarioDao().encontrar(150L);
//        if(usuario != null) {
            List<Plano> planos = new PlanoDao().getPlanosByUsuario(u);
//            request.setAttribute("planos", planos);
//        }
        ServletOutputStream os = response.getOutputStream();
        os.print(new Gson().toJson(planos));
    }
}
