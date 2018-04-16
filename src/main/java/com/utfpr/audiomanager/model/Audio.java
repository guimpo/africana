/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author josevictor
 */
@Entity
public class Audio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titulo;
    private String caminho;
    @ManyToOne
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) throws Exception {
        if (titulo == null) {
            throw new Exception("Titulo nao pode ser vazio");
        }
        if (titulo.equals("")) {
            throw new Exception("Titulo nao pode ser vazio");
        }
        this.titulo = titulo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) throws Exception {
        if (caminho.equals("")) {
            throw new Exception("Caminho nao pode ser vazio");
        }
        this.caminho = caminho;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) throws Exception {
        if (usuario == null) {
            throw new Exception("Especifique o usuario");
        }
        this.usuario = usuario;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Audio)) {
            return false;
        }
        Audio other = (Audio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utfpr.audiomanager.model.Audio[ id=" + id + " ]";
    }
    
}
