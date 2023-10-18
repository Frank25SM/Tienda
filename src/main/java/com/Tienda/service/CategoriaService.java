
package com.Tienda.service;

import com.Tienda.domain.Categoria;
import java.util.List;

/**
 *
 * @author fsanchezm
 */
public interface CategoriaService {
    //Retorna una lista de categorias(active o todas)
    public List<Categoria> getCategorias(boolean activos);
    //Retorna una catergoria por ID
    public Categoria getCategoria(Categoria categoria);
    
    //Se inserta un nuveo registro si el ID de la categoria esta vacio
    //Se actualiza el registro si el ID de la categoria No esta vacio
    public void save(Categoria categoria);
    
    public void delete(Categoria categoria);
}
