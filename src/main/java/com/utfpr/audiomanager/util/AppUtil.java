/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.util;

import java.util.Base64;

/**
 *
 * @author josevictor
 */
public class AppUtil {
    
    public static String encodeString(String value)
    {
        return Base64.getUrlEncoder().encodeToString(value.getBytes());
    }
}
