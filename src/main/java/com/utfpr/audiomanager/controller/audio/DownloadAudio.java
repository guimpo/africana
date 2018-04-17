/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller.audio;

import com.utfpr.audiomanager.dao.AudioDao;
import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Audio;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josevictor
 */
@WebServlet(name = "DownloadAudio", urlPatterns = {"/audio/download"})
public class DownloadAudio extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String fileName = request.getParameter("filename");      
            
            if(false) {
                throw new ServletException("File doesn't exists on server.");
            }
            
            
            List<Audio> l = new AudioDao()
                    .getAudiosByUsuario(new UsuarioDao().getUsuarioByEmail("emailemail"));
            byte[] arquivo = l.get(0).getArquivo();
            
            InputStream input = new ByteArrayInputStream(arquivo);
            
            String mimeType = "audio/mpeg";
            response.setContentType(mimeType != null? mimeType:"application/octet-stream");
            response.setContentLength((int) l.get(0).getArquivo().length);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            ServletOutputStream os = response.getOutputStream();
            os.write(arquivo);

            os.flush();
            os.close();
            input.close();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
