/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

/**
 *
 * @author josevictor
 */
@WebServlet(name = "Localizacao", urlPatterns = {"/localizacao"})
public class Localizacao extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String local = (String) request.getParameter("local");
        Locale locale = new Locale(local);
        Config.set(request.getSession(), Config.FMT_LOCALE, locale);
        Config.set(request.getSession(), Config.FMT_FALLBACK_LOCALE, locale);
        response.sendRedirect(request.getHeader("Referer"));
    }
}
