
package com.utfpr.audiomanager.controller.audio;

import com.google.gson.Gson;
import com.utfpr.audiomanager.dao.AudioDao;
import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Audio;
import com.utfpr.audiomanager.model.Usuario;
import com.utfpr.audiomanager.util.AppUtil;
import com.utfpr.audiomanager.util.HorarioUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@WebServlet(name = "AudioController", urlPatterns = {"/api/audio2"})
@MultipartConfig
public class AudioController extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    
              
        Usuario user = (Usuario) request.getAttribute("usuario");
//        Usuario user = new UsuarioDao()
//                    .getUsuarioByEmail(email);
//        System.out.println(user.getId());
//        try {
//            user.setId(100L);
//            user.setEmail("nakaima.prk@gmail.com");
//            user.setSenha("emailemail");
//        } catch (Exception ex) {
//            Logger.getLogger(AudioController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
//        String queryTitulo = request.getParameter("titulo");
        List<Audio> audios = null;
//        if (queryTitulo == null) {
        audios = new AudioDao().getAudiosByUsuario(user);
//        } else {
//            audios = new AudioDao().getAudiosByUsuarioAndTitulo(user, queryTitulo);
//        }
//        String uploadPath = request.getServletContext().getRealPath("")
//                                                    + File.separator + "uploads";
        
//        HttpSession session = request.getSession(false);
//        session.setAttribute("audios", audios);
//        session.setAttribute("uploadpath", uploadPath);

//        if(audios.isEmpty() || audios == null) try {
//            throw new Exception(user.toString());
//        } catch (Exception ex) {
//            Logger.getLogger(AudioController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(audios));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario currentUser = (Usuario) request.getAttribute("usuario");
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
                throw new Exception("Erro ao salvar o arquivo.");
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
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson("yohoho"));

        } catch (FileUploadException e) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(e));
        } catch (Exception e) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(e));
        }
    }
}
