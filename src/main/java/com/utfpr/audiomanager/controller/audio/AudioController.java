
package com.utfpr.audiomanager.controller.audio;

import com.google.gson.Gson;
import com.sun.org.apache.xalan.internal.xsltc.trax.OutputSettings;
import com.utfpr.audiomanager.dao.AudioDao;
import com.utfpr.audiomanager.model.Audio;
import com.utfpr.audiomanager.model.Usuario;
import com.utfpr.audiomanager.util.AppUtil;
import com.utfpr.audiomanager.util.HorarioUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
        
        String regex = "/api/audio2/\\d+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(uri);
        boolean download = m.matches();
        
        response.setContentType("application/json;charset=UTF-8");
        
        try(ServletOutputStream os = response.getOutputStream()) {
            if(download) {
                long id = Long.parseLong(Arrays
                        .asList(uri.split("/"))
                        .get(uri.split("/").length - 1));

                Audio audio = new AudioDao().getAudioByUsuarioAndId(user, id);
                String fileName = audio.getCaminho();

                if(audio == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                
                String uploadPath = request.getServletContext().getRealPath("")
                    + File.separator + "uploads";
                System.out.println(uploadPath + File.separator + fileName);
                File file = new File(uploadPath + File.separator + fileName);
                if (!file.exists()) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    throw new ServletException("File doesn't exists on server.");
                }
                ServletContext ctx = getServletContext();
                InputStream fis = new FileInputStream(file);
                String mimeType = ctx.getMimeType(file.getAbsolutePath());
                response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "attachment; filename=\"" + audio.getTitulo() + "\"");

                byte[] bufferData = new byte[1024];
                int read = 0;
                while ((read = fis.read(bufferData)) != -1) {
                    os.write(bufferData, 0, read);
                }
                os.flush();
                fis.close();
                
            } else {
                List<Audio> audios = new AudioDao().getAudiosByUsuario(user);
                os.print(new Gson().toJson(audios));
            }              
        } catch(ServletException se) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario currentUser =  (Usuario) request.getAttribute("usuario");
        
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
            response.setStatus(HttpServletResponse.SC_CREATED);
            
        } catch (FileUploadException e ) {
            Logger.getLogger(AudioController.class.getName()).log(Level.SEVERE, null, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            Logger.getLogger(AudioController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Usuario user =  (Usuario) request.getAttribute("usuario");
        
        String uri = request.getRequestURI();     
                
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        
        //System.out.println(uriList);
        long id = Long.parseLong(Arrays
                    .asList(uri.split("/"))
                    .get(uri.split("/").length - 1));
        
        Audio audio = new AudioDao().getAudioByUsuarioAndId(user, id);
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
