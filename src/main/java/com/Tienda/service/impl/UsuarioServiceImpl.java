
package com.Tienda.service.impl;

import com.Tienda.dao.UsuarioDao;
import com.Tienda.domain.Rol;
import com.Tienda.domain.Usuario;
import com.Tienda.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserDetailsService")
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService{

    @Autowired
    private UsuarioDao usuarioDao;
    
    @Autowired
    private HttpSession session;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      //Buscar el usuario por el username en la BD
      Usuario usuario = usuarioDao.finByUsername(username);
      //Sino existe el usuario lanza una excepcion
      if (usuario == null){
          throw new UsernameNotFoundException(username);
      }
      // Si llegó acá es por que el usuario existe en la BD
      //Remover atributos de la sesión
      session.removeAttribute("usuarioImagen");
      session.setAttribute("usuarioImagen", usuario.getRutaImagen());
      //Transforma roles a GrantedAuthority
      var roles = new ArrayList<GrantedAuthority>();
      for (Rol item : usuario.getRoles()){
          roles.add(new SimpleGrantedAuthority(item.getNombre()));          
      }
      //Se retorna el User(Clase UserDetails)
      return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }

    @Override
    public Usuario getUsuarioPorUsername(String username) {
        return usuarioDao.finByUsername(username);
    }
    
}
