
package com.Tienda.controller;

import com.Tienda.domain.Producto;
import com.Tienda.domain.Categoria;
import com.Tienda.service.CategoriaService;
import com.Tienda.service.ProductoService;
import com.Tienda.service.impl.FirebaseStorageServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/producto")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService; //Con esto se puede obetener los metodos de services de consultar datos de BD
    
    //Esto es por si tiene un elemento de imagen dde donde se requiere accesar al repositorio
    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    
    @GetMapping("/listado")
    public String inicio(Model model) {       
          List<Producto> listadoProductos = productoService.getProductos(false);   
          List<Categoria> categorias = categoriaService.getCategorias(true);
          model.addAttribute("productos", listadoProductos);
          model.addAttribute("totalProductos", listadoProductos.size());
          model.addAttribute("categorias", categorias);
        return "/producto/listado";
    }
    
    @GetMapping("/nuevo")
    public String productoNuevo(Producto producto) {
        return "/producto/modifica";
    }

    
    @PostMapping("/guardar")
    public String productoGuardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile) {
        if (!imagenFile.isEmpty()) {
            productoService.save(producto);
            producto.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile,
                            "producto",
                            producto.getIdProducto()));
        }
        productoService.save(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/eliminar/{idProducto}")
    public String productoEliminar(Producto producto) {
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String productoModificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        List<Categoria> categorias = categoriaService.getCategorias(true);                
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);
        return "/producto/modifica";
    }
    
    @PostMapping("/query3")
    public String consultaQuery3(@RequestParam(value = "precioInf") double precioInf,
            @RequestParam(value = "precioSup") double precioSup, Model model) {
        var productos = productoService.metodoNativo(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }
    
}