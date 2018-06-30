/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.africana.controller.usuario;

import com.google.gson.Gson;
import com.utfpr.africana.dao.UsuarioDao;
import com.utfpr.africana.model.Status;
import com.utfpr.africana.model.Usuario;
import com.utfpr.africana.util.AppUtil;
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
@WebServlet(name = "UsuarioController", urlPatterns = {"/webservice/user"})
public class UsuarioController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        try {            
            response.setContentType("application/json;charset=UTF-8");
            String credenciaisJson = AppUtil.getJsonRequest(request);
            Usuario user = gson.fromJson(credenciaisJson, Usuario.class);
            String nome = (String) user.getNome();
            String email = (String) user.getEmail();
            String senha = (String) user.getSenha();
            HttpSession session = request.getSession();

            Usuario u = new Usuario();
            u.setNome(nome);
            u.setEmail(email);
            u.setSenha(senha);
            
            List<Usuario> l = new UsuarioDao().getList();
            for(Usuario uu : l) {
                if(uu.getEmail() != null && uu.getEmail().equalsIgnoreCase(email)) {
                    throw new Exception("Email ja cadastrado.");
                }
            }
            new UsuarioDao().salvar(u);
            response.getOutputStream().print(gson.toJson(u));
            response.getOutputStream().flush();
        } catch(Exception e) {
            Status status = new Status();
            status.setSucesso(false);
            status.setDescription(e.getMessage());
            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();
        }        
    }

}
