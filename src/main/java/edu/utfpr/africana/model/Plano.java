package edu.utfpr.africana.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Plano implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_generator")
    @SequenceGenerator(name="plan_generator", sequenceName = "plan_seq", allocationSize=50)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    @ManyToOne
    private Usuario usuario;
    
    private String componenteCurricular;
    
    private String tema;
    
    private String objetivos;
    
    private int duracao;
    
    private String conhecimentosPrevios;
    
    private String recursos;
    
    private String descricao;
    
    private String avaliacao;
    
    private String referencias;

  public String getComponenteCurricular() {
    return componenteCurricular;
  }

  public void setComponenteCurricular(String componenteCurricular) {
    this.componenteCurricular = componenteCurricular;
  }

  public String getTema() {
    return tema;
  }

  public void setTema(String tema) {
    this.tema = tema;
  }

  public String getObjetivos() {
    return objetivos;
  }

  public void setObjetivos(String objetivos) {
    this.objetivos = objetivos;
  }

  public int getDuracao() {
    return duracao;
  }

  public void setDuracao(int duracao) {
    this.duracao = duracao;
  }

  public String getConhecimentosPrevios() {
    return conhecimentosPrevios;
  }

  public void setConhecimentosPrevios(String conhecimentosPrevios) {
    this.conhecimentosPrevios = conhecimentosPrevios;
  }

  public String getRecursos() {
    return recursos;
  }

  public void setRecursos(String recursos) {
    this.recursos = recursos;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getAvaliacao() {
    return avaliacao;
  }

  public void setAvaliacao(String avaliacao) {
    this.avaliacao = avaliacao;
  }

  public String getReferencias() {
    return referencias;
  }

  public void setReferencias(String referencias) {
    this.referencias = referencias;
  }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Plano)) {
            return false;
        }
        Plano other = (Plano) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "id = " + this.id + "\n" +
            "tema = " + this.tema + "\n" +
            "duracao = " + this.duracao + "\n" +
            "componenteCurricular = " + this.componenteCurricular + "\n";
    }
    
}
