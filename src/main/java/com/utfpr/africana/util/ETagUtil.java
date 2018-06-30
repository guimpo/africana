package com.utfpr.africana.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                System.err.println(ex.getMessage());
            }
        }
        
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] sha1 = messageDigest.digest(Files.readAllBytes(path));
            return DatatypeConverter.printHexBinary(sha1);
        } catch (NoSuchAlgorithmException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        return "errado" + path.toString();
  }
}
