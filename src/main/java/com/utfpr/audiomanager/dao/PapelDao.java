/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.dao;

import com.utfpr.audiomanager.model.Papel;

/**
 *
 * @author paulo
 */

public class PapelDao extends GenericDao<Papel, Long> {
    
    public PapelDao() {
        super(Papel.class);
    }
}
