package com.utfpr.audiomanager.controller.audio;

import com.utfpr.audiomanager.dao.AudioDao;
import com.utfpr.audiomanager.model.Audio;
import com.utfpr.audiomanager.model.Usuario;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josevictor
 */
@WebServlet(name = "CadastroAudio", urlPatterns = {"/audio/cadastro"})
@MultipartConfig
public class CadastroAudio extends HttpServlet {
    
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("user");
        getServletContext()
                .getRequestDispatcher("/audio/cadastro.jsp")
                .forward(request, response);        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario currentUser = (Usuario) session.getAttribute("user");
        Part part = request.getPart("arquivo");
        InputStream in = part.getInputStream();
                
        byte[] buffer = new byte[in.available()];

        int bytesLidos = 0;

        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        while((bytesLidos = in.read(buffer)) != -1) {
            bao.write(buffer, 0, bytesLidos);
        }

        byte[] arquivo = bao.toByteArray();       
        
        if (part.getContentType().equals("audio/mpeg")) {
            
        }
        Audio a = new Audio();
        try {
            a.setUsuario(currentUser);
            a.setArquivo(arquivo);
            new AudioDao().salvar(a);
        } catch (Exception ex) {
            Logger.getLogger(CadastroAudio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        part.delete();
        response.sendRedirect("../usuario/cadastro");
    }
}
