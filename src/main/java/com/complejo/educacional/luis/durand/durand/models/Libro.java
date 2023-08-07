package com.complejo.educacional.luis.durand.durand.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Pablo
 * @Entity: para decir a JPA Y HIBERANTE que esta será una entidad y se tiene
 * que guardar como tal en la BD
 * @Table: Para indicar que está será una tabla en la BD.
 * @Data: Para crear los gett y sett
 * @AllArgsConstructor: Constructor con parámetros
 * @NoArgsConstructor:Constructor sin parámetros
 */
@Entity
@Data
@Table(name = "libro")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Libro implements Serializable {
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
    @Column(name = "id_libro")
    private Long id_libro;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotNull(message = "¡El campo id_autor no debe ser vacío!")
    @Column(name = "id_autor")
    private Autor autor;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotNull(message = "¡El campo id_categoria no debe ser vacío!")
    @Column(name = "id_categoria")
    private Categoria categoria;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotNull(message = "¡El campo id_editorial no debe ser vacío!")
    @Column (name = "id_editorial")
    private Editorial editorial;
    @NotEmpty(message = "¡El título del libro no debe ser vacío!")
    @Size(min = 4, max = 50, message = "¡El campo título del libro debe contener 4 carácters como minimo y 50 como máximo!")
    @Column(name = "titulo_libro", unique = true)
    private String titulo_libro;
    @NotEmpty(message = "¡La descripción del libro no debe ser vacía!")
    @Size(min = 4, max = 50, message = "¡El campo descripción del libro debe contener 4 carácters como minimo y 50 como máximo!")
    @Column(name = "descripcion_libro")
    private String descripcion_libro;

    @NotEmpty(message = "¡El número páginas del libro no debe ser vacío!")
    @Min(value = 1, message = "¡El campo número páginas libro deber se mayor a 1 página!")
    @Max(value = 1000, message = "¡El campo número páginas libro no deber mayor a 1000 páginas!")
    @Column(name = "numero_paginas_libro")
    private Integer numero_paginas_libro;

    @NotEmpty(message = "¡El cantida de jemplares del libro no debe ser vacío!")
    @Min(value = 1, message = "¡El campo cantidad_ejemplares libro deber se mayor a 1 !")
    @Max(value = 1000, message = "¡El campo número páginas libro no deber mayor a 1000 páginas!")
    @Column(name = "cantidad_ejemplares_libro")
    private Integer cantidad_ejemplares_libro;

    @Column(name = "anyo_publicacion_libro")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date anyo_publicacion_libro;

    @Column(updatable = false)
    private Date createAt;

    @Column(name = "updateAt")
    private Date updateAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = new Date();
    }

}
