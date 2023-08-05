package com.complejo.educacional.luis.durand.durand.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
/**
 * @author Pablo
 * @Entity: para decir a JPA Y HIBERANTE que esta será una entidad y se tiene
 *          que guardar como tal en la BD
 * @Table: Para indicar que está será una tabla en la BD.
 * @Data: Para crear los gett y sett
 * @AllArgsConstructor: Constructor con parámetros
 * @NoArgsConstructor:Constructor sin parámetros
 */
@Entity
@Data
@Table(name = "categoria")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Categoria implements Serializable {
    /**
     * Serializable:para hacer la persistencia del objeto y convertilo en una
     * secuencia de Bytes para poder almacenarlo en algún medio de almacenamiento en
     * esta caso una BD.
     *
     * @GeneratedValue genera automaticamente el id.
     * @Column Personalización para las columnas. unique = true(no se repita el
     * valor ingresado)
     * @Size: Solo para String o Char.
     * @NotEmpty: Campo obligatorío.
     * @Min: validación del valor mínimo del campo.
     * @Max: validación del valor máximo del campo.
     * @PrePersist: Ejecuta el método justo antes que el objeto sea creado.
     * @PreUpdate: Ejecuta el método cuando el objeto es modificado.
     * @NotNull: que nunca debe ser null.
     * @JoinColumn: el campo que unirá las tablas
     * @ManyToOne: relación uni direccional. fetch = FetchType.LAZY= no carga todos
     * apoderados solo trae el alumno (no carga objetos en memoría).
     * cascade = CascadeType.PERSIST:
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id_categoria;

    @NotEmpty(message = "¡El nombre de la Categoría no debe ser vacío!")
    @Size(min = 4, max = 50, message = "¡El campo nombre categoría debe contener 4 carácters como minimo y 50 como máximo!")
    @Column(name = "nombre_categoria")
    private String nombre_categoria;

    @NotEmpty(message = "¡El nombre de la Categoría no debe ser vacío!")
    @Size(min = 4, max = 50, message = "¡El campo nombre categoría debe contener 4 carácters como minimo y 50 como máximo!")
    @Column(name = "descripcion_categoria")
    private String descripcion_categoria;

    @Column(updatable = false)
    private Date createAt;
    @Column(name = "updateAt")
    private Date udpdateAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.udpdateAt= new Date();
    }

}
