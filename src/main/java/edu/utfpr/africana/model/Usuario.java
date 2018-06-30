/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utfpr.africana.model;

import eduutfpr.africana.util.HashingUtil;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author josevictor
 */
@Entity
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name="user_generator", sequenceName = "user_seq", allocationSize=50)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    private String nome;
    
    @Column(unique=true)
    private String email;
    
    private String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if(nome.equalsIgnoreCase("")) {
            throw new Exception("nome em branco");
        }
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if(email.equalsIgnoreCase("")) {
            throw new Exception("email em branco");
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) throws Exception {
        if(senha.equalsIgnoreCase("")) {
            throw new Exception("senha em branco");
        } else if (senha.length() <= 6) {
            throw new Exception("senha muito pequena");
        }
        String hashedPass = HashingUtil.generateHashedPassword(senha);
        if (hashedPass == null) {
            throw new Exception("erro ao salvar senha");
        }
        this.senha = hashedPass;
    }

    public boolean isSenhaValid(String matchSenha) {
        return HashingUtil.validateHashedPassword(matchSenha, this.senha);
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utfpr.audiomanager.model.Usuario[ id=" + id + " ]";
    }
    
}
