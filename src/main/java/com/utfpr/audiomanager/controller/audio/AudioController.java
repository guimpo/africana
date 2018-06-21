
package com.utfpr.audiomanager.controller.audio;

import com.google.gson.Gson;
import com.utfpr.audiomanager.dao.AudioDao;
import com.utfpr.audiomanager.model.Audio;
import com.utfpr.audiomanager.model.Usuario;
import com.utfpr.audiomanager.util.AppUtil;
import com.utfpr.audiomanager.util.HorarioUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "AudioController", urlPatterns = {"/api/audio2/*"})
@MultipartConfig
public class AudioController extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";
    private static final int SEM_ID = 3;
    private static final int COM_ID = 4;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    
              
        Usuario user = (Usuario) request.getAttribute("usuario");
        String uri = request.getRequestURI();     
        List uriList = Arrays.asList(uri.split("/"));
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // ["", "api", "audio2"]
        switch (uriList.size()) {
            case SEM_ID:
                List<Audio> audios = new AudioDao().getAudiosByUsuario(user);          
                out.print(new Gson().toJson(audios));
                break;
            case COM_ID:
//              System.out.println(uriList);
                Long id = Long.parseLong(uriList.get(COM_ID - 1).toString());
                Audio audio = new AudioDao().getAudioById(id);
                if(audio == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
                }
                out.print(new Gson().toJson(audio));
                break;
            default:
                out.print(new Gson().toJson(new Exception("boring")));
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
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

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String uri = request.getRequestURI();     
        List uriList = Arrays.asList(uri.split("/"));
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        //System.out.println(uriList);
        Long id = Long.parseLong(uriList.get(COM_ID - 1).toString());
        Audio audio = new AudioDao().getAudioById(id);
        if(audio == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } else {
            BufferedReader reader = request.getReader();
            Audio updatedAudio = new Gson().fromJson(reader, Audio.class);
            new AudioDao().atualizar(updatedAudio);
//            System.out.println(updatedAudio);
            out.print(new Gson().toJson(updatedAudio));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String uri = request.getRequestURI();     
        List uriList = Arrays.asList(uri.split("/"));
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        //System.out.println(uriList);
        Long id = Long.parseLong(uriList.get(COM_ID - 1).toString());
        Audio audio = new AudioDao().getAudioById(id);
        if(audio == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {         
            new AudioDao().remover(audio.getId());
//            System.out.println(updatedAudio);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
    
    
}
