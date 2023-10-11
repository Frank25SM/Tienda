
package com.Tienda.domain;

/** si queremos importar todas las librerías de Jakarta escribimos el comando
import jakarta.persistence.*
 */

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author fsanchezm
 */
@Data //Sirve para la creación automática de los SET y GET de los atributos
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private Long idCategoria; //Esto lo transforma en id_categoria por el CameleCase pues después de cada palabra el CameleCase pone un guión bajo después de cada palabra.. 
    private String descripcion;
    private String rutaImagen;
    private boolean activo;
    
    
    public Categoria(){} 

    public Categoria(String descripcion, String rutaImagen, boolean activo) {
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.activo = activo;
    }
    
    
}
