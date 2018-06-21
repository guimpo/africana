/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.dao;

import com.utfpr.audiomanager.model.Audio;
import com.utfpr.audiomanager.model.Usuario;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author josevictor
 */
public class AudioDao extends GenericDao<Audio, Long>{
    
    public AudioDao() {
        super(Audio.class);
    }
    
    public Audio getAudioById(Long id) {
        return (Audio) session.get(Audio.class, id);
    }
    
    public List<Audio> getAudiosByUsuario(Usuario user) {
        List audios = session.createCriteria(Audio.class)
                .add(Restrictions.eq("usuario", user))
                .list();
        return audios;
    }
    
    public List<Audio> getAudiosByUsuarioAndTitulo(Usuario user, String titulo) {
        List audios = session.createCriteria(Audio.class)
                .add(Restrictions.eq("usuario", user))
                .add(Restrictions.like("titulo", "%" + titulo + "%"))
                .list();
        return audios;
    }
}
