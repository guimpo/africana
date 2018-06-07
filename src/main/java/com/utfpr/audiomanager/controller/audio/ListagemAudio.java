/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller.audio;

import com.utfpr.audiomanager.dao.AudioDao;
import com.utfpr.audiomanager.model.Audio;
import com.utfpr.audiomanager.model.Usuario;
import java.io.File;
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
@WebServlet(name = "ListagemAudio", urlPatterns = {"/audio/lista"})
public class ListagemAudio extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Usuario user = (Usuario) request.getSession(false).getAttribute("user");
        String queryTitulo = request.getParameter("titulo");
        List<Audio> audios = null;
        if (queryTitulo == null) {
            audios = new AudioDao().getAudiosByUsuario(user);
        } else {
            audios = new AudioDao().getAudiosByUsuarioAndTitulo(user, queryTitulo);
        }
        String uploadPath = request.getServletContext().getRealPath("")
                                                    + File.separator + "uploads";
        HttpSession session = request.getSession(false);
        session.setAttribute("audios", audios);
        session.setAttribute("uploadpath", uploadPath);
        getServletContext()
                .getRequestDispatcher("/WEB-INF/view/audio/lista.jsp")
                .forward(request, response);
    }
}
