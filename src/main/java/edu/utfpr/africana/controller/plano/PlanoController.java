package edu.utfpr.africana.controller.plano;

import edu.utfpr.africana.dao.PlanoDao;
import edu.utfpr.africana.dao.UsuarioDao;
import edu.utfpr.africana.model.Plano;
import edu.utfpr.africana.model.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PlanoController", urlPatterns = {"/plano/cadastro"})
public class PlanoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        getServletContext()
                .getRequestDispatcher("/WEB-INF/view/plano/cadastroPlano.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {         
      
        String componenteCurricular = (String) request.getParameter("componenteCurricular");
        String tema = (String) request.getParameter("tema");
        String objetivos = (String) request.getParameter("objetivos");
        String duracao = (String) request.getParameter("duracao");
            String conhecimentosPrevios = (String) request.getParameter("conhecimentosPrevios");
        String recursos = (String) request.getParameter("recursos");
        String descricao = (String) request.getParameter("descricao");
        String avaliacao = (String) request.getParameter("avaliacao");

//        Cookie[] cookies = request. getCookies();
//        String cookieName = "email";
//        String cookieValue = "";
//        
//        for(Cookie c : cookies) {
//            if(cookieName.equals(c.getName())) {
//                cookieValue = c.getValue();
//            }
//        }
       
//        Usuario u = new UsuarioDao().getUsuarioByEmail(cookieValue);
//        if(u == null) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
        Usuario u = new UsuarioDao().encontrar(150L);
        
        try {
            Plano p = new Plano();
            p.setComponenteCurricular(componenteCurricular);
            p.setTema(tema);
            p.setObjetivos(objetivos);
            p.setDuracao(Integer.parseInt(duracao));
            p.setConhecimentosPrevios(conhecimentosPrevios);
            p.setRecursos(recursos);
            p.setDescricao(descricao);
            p.setAvaliacao(avaliacao);
            p.setUsuario(u);
            
            new PlanoDao().salvar(p);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("text/html;charset=UTF-8");
            
            request.setAttribute("plano", p);
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/view/plano/cadastroPlano.jsp")
                    .forward(request, response);
           
        } catch(Exception e) {
            System.err.println(e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }    
    }
}
