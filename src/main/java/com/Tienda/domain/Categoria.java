
package com.Tienda.domain;

/** si queremos importar todas las librerías de Jakarta escribimos el comando
import jakarta.persistence.*
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import lombok.Data;


@Data //Sirve para la creación automática de los SET y GET de los atributos
@Entity
@Table(name = "categoria") //Se especifica la tabla en BD a la que se va a conectar
public class Categoria implements Serializable{ //La interfaz Serializable permite que la entidad Categoria se transforme información que se pueda guardar en la BD 
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria; //Esto lo transforma en id_categoria por el CameleCase pues después de cada palabra el CameleCase pone un guión bajo después de cada palabra.. 
    private String descripcion;
    private String rutaImagen;
    private boolean activo;
    
    @OneToMany 
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false) //Esto no va a hacer insert ni update en la tabla Producto
    List<Producto> productos;//Lista de tipo Productos
    
    public Categoria(){} 

    public Categoria(String descripcion, String rutaImagen, boolean activo) {
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.activo = activo;
    }
    
    
}
