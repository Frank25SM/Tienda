
package com.Tienda.dao;

import com.Tienda.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

//Para que pueda utilizar metodos de Hibernate, debe extender de JpaRepository y éste recibe 2 valores(objeto generico o tabla que va a utilizar, tipo de dato de la entidad)
public interface CategoriaDao extends JpaRepository<Categoria, Long> {
    
}
