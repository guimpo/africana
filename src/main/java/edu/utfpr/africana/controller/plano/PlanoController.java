package edu.utfpr.africana.controller.plano;

import com.google.gson.Gson;
import edu.utfpr.africana.dao.PlanoDao;
import edu.utfpr.africana.model.Plano;
import edu.utfpr.africana.model.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PlanoController", urlPatterns = {"/api/plano"})
public class PlanoController extends HttpServlet {

    private Usuario user = null;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        user = (Usuario) request.getAttribute("user");
        response.setContentType("application/json;charset=UTF-8");
        
        System.out.println((char)27 + "[33m" + "entrou no get plano");
        
        System.out.println((char)27 + "[33m" + user);
        
        List<Plano> plans = new PlanoDao().getPlanosByUsuario(user);
        plans.forEach((p) -> {
            System.out.println((char)27 + "[33m" + p.getTema());
        });
                
        response.getWriter().println(new Gson().toJson(plans));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        user = (Usuario) request.getAttribute("user");
        
        response.setContentType("application/json;charset=UTF-8");
        
        System.out.println((char)27 + "[33m" + "entrou no post plano");
        
        System.out.println((char)27 + "[33m" + user);
        
        BufferedReader reader = request.getReader();
        Plano receivedPlan = new Gson().fromJson(reader, Plano.class);
        
        System.out.println((char)27 + "[33m" + receivedPlan);
        
        try {
            receivedPlan.setUsuario(user);
        } catch (Exception ex) {
            Logger.getLogger(PlanoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new PlanoDao().salvar(receivedPlan);
        response.setStatus(HttpServletResponse.SC_CREATED);
      
//        String componenteCurricular = (String) request.getParameter("componenteCurricular");
//        String tema = (String) request.getParameter("tema");
//        String objetivos = (String) request.getParameter("objetivos");
//        String duracao = (String) request.getParameter("duracao");
//        String conhecimentosPrevios = (String) request.getParameter("conhecimentosPrevios");
//        String recursos = (String) request.getParameter("recursos");
//        String descricao = (String) request.getParameter("descricao");
//        String avaliacao = (String) request.getParameter("avaliacao");     
        
//        try {
//            Plano p = new Plano();
//            p.setComponenteCurricular(componenteCurricular);
//            p.setTema(tema);
//            p.setObjetivos(objetivos);
//            p.setDuracao(Integer.parseInt(duracao));
//            p.setConhecimentosPrevios(conhecimentosPrevios);
//            p.setRecursos(recursos);
//            p.setDescricao(descricao);
//            p.setAvaliacao(avaliacao);
//            p.setUsuario(user);
//            
//            new PlanoDao().salvar(p);
//            response.setStatus(HttpServletResponse.SC_CREATED);
//            response.setContentType("text/html;charset=UTF-8");
//            
//            request.setAttribute("plano", p);
//            getServletContext()
//                    .getRequestDispatcher("/WEB-INF/view/plano/cadastroPlano.jsp")
//                    .forward(request, response);
//        } catch(Exception e) {
//            System.err.println(e);
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }    
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        user = (Usuario) request.getAttribute("user");
        
        response.setContentType("application/json;charset=UTF-8");
        
        System.out.println((char)27 + "[33m" + "entrou no put plano");
        
        System.out.println((char)27 + "[33m" + user);
        
        BufferedReader reader = request.getReader();
        Plano receivedPlan = new Gson().fromJson(reader, Plano.class);
        
        System.out.println((char)27 + "[33m" + receivedPlan);
        
        new PlanoDao().atualizar(receivedPlan);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
    
    
}
