/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.dao;

import com.utfpr.audiomanager.model.Usuario;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

/**
 *
 * @author josevictor
 */
public class UsuarioDao extends GenericDao<Usuario, Long> {
    
    public UsuarioDao() {
        super(Usuario.class);
    }
    
    public Usuario getUsuarioByEmail(String email) {
        Usuario user = new Usuario();
        Usuario result = null;
        try {
            user.setEmail(email);
            result = findByObject(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
