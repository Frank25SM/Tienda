
package com.Tienda.service.impl;

import com.Tienda.domain.Rol;
import com.Tienda.dao.UsuarioDao;
import com.Tienda.domain.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.Tienda.service.UsuarioDetailsService;



@Service("UserDetailsService")
public class UsuarioDetailsServiceImpl implements UsuarioDetailsService, UserDetailsService{

    @Autowired
    private UsuarioDao usuarioDao;
    
    @Autowired
    private HttpSession session;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      //Buscar el usuario por el username en la BD
      Usuario usuario = usuarioDao.findByUsername(username);
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
        return usuarioDao.findByUsername(username);
    }
    
}
