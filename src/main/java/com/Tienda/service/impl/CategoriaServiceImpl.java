
package com.Tienda.service.impl;

import com.Tienda.dao.CategoriaDao;
import com.Tienda.domain.Categoria;
import com.Tienda.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service //Indica la implementación de Service.
public class CategoriaServiceImpl implements CategoriaService{

    @Autowired //Sirve para crear una inyección de dependencias del objeto que se crea en la siguiente linea.
    private CategoriaDao categoriaDao;
        
    //Se utiliza el Override para sobreescribir la funcionalidad un metodo que ya existe en otra clase.
    @Override
    //Una transaccion en Base de datos se utiliza para hacer ejecuciones. Donde ejecuta 
    //varias acciones para que la transacción se dé por finalizada, es de tipo Read para que 
    //las tablas nose bloqueen.
    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activos) {
        List<Categoria> categorias = categoriaDao.findAll();        
        if(activos){
            //Nos permite remover de la lista, algunos elementos que cumplan con algún criterio. En este caso, que Activo sea falso(diferente a IsActivo porque es booleano).
            categorias.removeIf(c -> !c.isActivo());
        }
        return categorias;
    }
    
    //Se utiliza el Override para sobreescribir la funcionalidad un metodo que ya existe en otra clase.
    @Override
    public Categoria getCategoria(Categoria categoria) {
        //El orElse, es como un condicional que si no encuentra el IdCategoría, que regrese null.
        return categoriaDao.findById(categoria.getIdCategoria()).orElse(null);
    }
    
    //Se utiliza el Override para sobreescribir la funcionalidad un metodo que ya existe en otra clase.
    @Override
    @Transactional //Bloquea la tabla para que se completen las acciones de la o las transacciones.
    public void save(Categoria categoria) {
        categoriaDao.save(categoria);
    }
    
    //Se utiliza el Override para sobreescribir la funcionalidad un metodo que ya existe en otra clase.
    @Override
    @Transactional
    public void delete(Categoria categoria) {
        categoriaDao.delete(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    //Fachada de un metodo
    public List<Categoria> getCategoriasPorDescripcion(String descripcion) {
        return categoriaDao.findByDescripcionContainingIgnoreCase(descripcion);
    }
}
