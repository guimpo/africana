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
import java.io.PrintWriter;
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
            if(fileName == null || fileName.equals("")){
                    throw new ServletException("File Name can't be null or empty");
            }
            String uploadPath = request.getServletContext().getRealPath("")
                + File.separator + "uploads";
            System.out.println(uploadPath + File.separator + fileName);
            File file = new File(uploadPath + File.separator + fileName);
            if(!file.exists()) {
                            throw new ServletException("File doesn't exists on server.");
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
            e.printStackTrace();
        }
    }
}