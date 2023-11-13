
package com.Tienda.service;

import com.Tienda.domain.Categoria;
import java.util.List;


public interface CategoriaService {
    //Los que se detallan a siguiente son firmas de metodos, porque no incluye la logica del metodo"son como medotos falsos"
    
    //Retorna una lista de categorias(active o todas)
    public List<Categoria> getCategorias(boolean activos);
    
    //Retorna una catergoria por ID
    public Categoria getCategoria(Categoria categoria);
    
    //Se inserta un nuveo registro si el ID de la categoria esta vacio
    //Se actualiza el registro si el ID de la categoria No esta vacio
    public void save(Categoria categoria);
    
    //Se elimina el registro de categoría que incluya el ID
    public void delete(Categoria categoria);
}
