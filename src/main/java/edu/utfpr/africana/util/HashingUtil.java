/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utfpr.africana.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author josevictor
 */
public class HashingUtil {
    
    public static String generateHashedPassword(String password) {
        String generaSecuredPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return generaSecuredPassword;
    }
    
    public static boolean validateHashedPassword(String originalPassword, String storedPassword) {
        boolean matched = BCrypt.checkpw(originalPassword, storedPassword);
        return matched;
    }
}
