/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author josevictor
 */
@WebServlet(name = "DownloadAudio", urlPatterns = {"/audio/download"})
public class DownloadAudio extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        try {
            String fileName = request.getParameter("filename");
            if(fileName == null || fileName.equals("")){
                throw new Exception("Nome do áudio em branco.");
            }
            String uploadPath = request.getServletContext().getRealPath("")
                + File.separator + "uploads";
            System.out.println(uploadPath + File.separator + fileName);
            File file = new File(uploadPath + File.separator + fileName);
            if(!file.exists()) {
                throw new Exception("Áudio não encontrado");
            }
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());
            response.setContentType(mimeType != null? mimeType:"application/octet-stream");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            ServletOutputStream os = response.getOutputStream();
            byte[] bufferData = new byte[1024];
            int read=0;
            while((read = fis.read(bufferData))!= -1){
                    os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();            
        } catch (Exception e) {
            session.setAttribute("er-message", e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            getServletContext()
                    .getRequestDispatcher("/audio/lista.jsp")
                    .forward(request, response);
        }
    }
}