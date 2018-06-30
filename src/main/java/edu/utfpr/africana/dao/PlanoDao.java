package edu.utfpr.africana.dao;

import edu.utfpr.africana.model.Plano;
import edu.utfpr.africana.model.Usuario;
import java.util.List;
import org.hibernate.criterion.Restrictions;

public class PlanoDao extends GenericDao<Plano, Long> {
    
    public PlanoDao() {
        super(Plano.class);
    }
    
    public List<Plano> getPlanosByUsuario(Usuario user) {
        return session.createCriteria(Plano.class)
            .add(Restrictions.eq("usuario", user))
            .list();
    }
}