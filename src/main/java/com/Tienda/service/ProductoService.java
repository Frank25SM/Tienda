
package com.Tienda.service;

import com.Tienda.domain.Producto;
import java.util.List;


public interface ProductoService {
    
    //Retorna una lista de productos en un List
    public List<Producto> getProductos(boolean activos);
    //Retorna una producto por ID
    
    public Producto getProducto(Producto producto);
    
    //Se inserta un nuveo registro si el ID del producto esta vacio
    //Se actualiza el registro si el ID de la producto No esta vacio
    public void save(Producto producto);
    
    //Se elimina el registro con el ID de la producto
    public void delete(Producto producto);
}
