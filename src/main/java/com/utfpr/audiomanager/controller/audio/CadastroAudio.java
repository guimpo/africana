package com.utfpr.audiomanager.controller.audio;

import com.utfpr.audiomanager.dao.AudioDao;
import com.utfpr.audiomanager.model.Audio;
import com.utfpr.audiomanager.model.Usuario;
import com.utfpr.audiomanager.util.AppUtil;
import com.utfpr.audiomanager.util.HorarioUtil;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        String UPLOAD_DIRECTORY = "uploads";
        String uploadPath = request.getServletContext().getRealPath("")
            + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            List<FileItem> fields = upload.parseRequest(request);
            System.out.println(fields);
            Iterator<FileItem> it = fields.iterator();
            if (!it.hasNext()) {
                throw new Exception("Could not write file");
            }
            String titulo = null;
            String newFileName = null;
            while (it.hasNext()) {
                FileItem fileItem = it.next();
                boolean isFormField = fileItem.isFormField();
                if (!isFormField) {
                    String fileName = new File(fileItem.getName()).getName();
                    newFileName = AppUtil.encodeString(currentUser.getEmail()) + "_" + HorarioUtil.getCurrentDateString("yyyyMMddHHmmss") + ".mp3";
                    String filePath = uploadPath + File.separator + newFileName;
                    File storeFile = new File(filePath);
                    fileItem.write(storeFile);
                } else {
                    titulo = fileItem.getString();
                }
            }
            Audio audio = new Audio();
            audio.setTitulo(titulo);
            audio.setCaminho(newFileName);
            audio.setUsuario(currentUser);
            new AudioDao().salvar(audio);
            response.sendRedirect("cadastro");
            return;
        } catch (FileUploadException e) {
            e.printStackTrace();
            response.sendRedirect("../usuario/entrar");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("../usuario/cadastro");
        }
    }
}
