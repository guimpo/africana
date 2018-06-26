/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller.usuario;

import com.google.gson.Gson;
import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Credencial;
import com.utfpr.audiomanager.model.Status;
import com.utfpr.audiomanager.model.Usuario;
import com.utfpr.audiomanager.util.AppUtil;
import com.utfpr.audiomanager.util.HashingUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;



/**
 *
 * @author josevictor
 */
@WebServlet(name = "SessaoController", urlPatterns = {"/webservice/sessao"})
public class SessaoController extends HttpServlet {

    private String FRASE_SEGREDO;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        response.setContentType("application/json;charset=UTF-8");
        try {
            String credenciaisJson = AppUtil.getJsonRequest(request);
            Credencial credencial = gson.fromJson(credenciaisJson, Credencial.class);
            validarCredenciais(credencial);
            String token = gerarToken(credencial.getEmail(), 1);
            Status status = new Status();
            status.setSucesso(true);
            status.setDescription(token);
            response.getOutputStream().print(gson.toJson(token));
            response.getOutputStream().flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            Status status = new Status();
            status.setSucesso(false);
            status.setDescription(ex.getMessage());
            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();
        }
    }

    private void validarCredenciais(Credencial credencial) throws Exception {
        try {
            Usuario resultUsuario = new UsuarioDao()
                .getUsuarioByEmail(credencial.getEmail());
            
            boolean isSenhaValid = HashingUtil
                    .validateHashedPassword(credencial.getSenha(),
                            resultUsuario.getSenha());
            if (!isSenhaValid)
                throw new Exception("Crendencias não válidas!");
        } catch (Exception e) {
            throw e;
        }
    }
    
    private String gerarToken(String email, Integer expiraEmDias) {
        SignatureAlgorithm algoritmoAssinatura = SignatureAlgorithm.HS512;
        Date agora = new Date();
        Calendar expira = Calendar.getInstance();
        expira.add(Calendar.DAY_OF_MONTH, expiraEmDias);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("ADHJ(d9ashd9he3h9daeujd90j3eq9dja0dajd49ha");
        SecretKeySpec key = new SecretKeySpec(apiKeySecretBytes, algoritmoAssinatura.getJcaName());
        JwtBuilder construtor = Jwts.builder()
                .setIssuedAt(agora)
                .setIssuer(email)
                .signWith(algoritmoAssinatura, key)
                .setExpiration(expira.getTime());
        return construtor.compact();
    }
    
    public Claims validaToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(FRASE_SEGREDO))
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims.getIssuer());
            return claims;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
