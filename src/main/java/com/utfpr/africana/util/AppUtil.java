/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.africana.util;

import java.util.Base64;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author josevictor
 */
public class AppUtil {
    
    public static String encodeString(String value)
    {
        return Base64.getUrlEncoder().encodeToString(value.getBytes());
    }
    
    public static String getJsonRequest(HttpServletRequest request) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        return sb.toString();
    }
}
