
import com.utfpr.audiomanager.dao.UsuarioDao;
import com.utfpr.audiomanager.model.Usuario;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josevictor
 */
public class Teste {
    
    public static void main(String[] args) {
        UsuarioDao usuarioDao = new UsuarioDao();
        Usuario result = usuarioDao.getUsuarioByEmail("testando@testando.com");
        System.out.println(result);
    }
}
