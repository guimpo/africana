package com.utfpr.audiomanager.util;

import com.utfpr.audiomanager.controller.Index;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

public class ETagUtil {
    
    public static String get (HttpServletRequest request, String fileName) {
        String filePath = request.getServletContext().getRealPath("")
                    + File.separator + fileName;
        Path path = Paths.get(filePath);

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                throw new ServletException("File doesn't exists on server.");
            } catch (ServletException ex) {
                Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] sha1 = messageDigest.digest(Files.readAllBytes(path));
            return DatatypeConverter.printHexBinary(sha1);
        } catch (NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "errado" + path.toString();
  }
}
