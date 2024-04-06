package com.complejo.educacional.luis.durand.durand.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Pablo
 * @Entity: para decir a JPA Y HIBERANTE que esta será una entidad y se tiene que guardar como tal en la BD.
 * @Table: Para indicar que está será una tabla en la BD.
 * @Data: Para crear los gett y sett
 * @AllArgsConstructor: Constructor con parámetros
 * @NoArgsConstructor:Constructor sin parámetros
 */
@Entity
@Getter
@Setter
@Table(name = "categoria")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Categoria implements Serializable {
    /**
     * Serializable:para hacer la persistencia del objeto y convertilo en una secuencia de Bytes para poder almacenarlo
     * en algún medio de almacenamiento en esta caso una BD.
     *
     * @GeneratedValue genera automaticamente el id.
     * @Column Personalización para las columnas. unique = true(no se repita el valor ingresado)
     * @Size: Solo para String o Char.
     * @NotEmpty: Campo obligatorío.
     * @Min: validación del valor mínimo del campo.
     * @Max: validación del valor máximo del campo.
     * @PrePersist: Ejecuta el método justo antes que el objeto sea creado.
     * @PreUpdate: Ejecuta el método cuando el objeto es modificado.
     * @NotNull: que nunca debe ser null.
     * @JoinColumn: el campo que unirá las tablas
     * @OneToMany  Esta relación es unidireccional, lo que significa que la entidad secundaria no tiene conocimiento de
     * la entidad principal.Se aplica al campo categoría para indicar que un libro puede tener muchas categorias.
     * El parámetro mappedBy especifica el nombre del campo en la entidad Libro que mapea esta relación.
     * fetch = FetchType.LAZY= no carga todas categorias solo trae la categoría (no carga objetos en memoría).
     * cascade = CascadeType.PERSIST: En caso de eliminar un libro se elimina esté no la categoría.
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

    @Size(min = 4, max = 50, message = "¡El campo nombre categoría debe contener 4 carácters como minimo y 50 como máximo!")
    @Column(name = "descripcion_categoria")
    private String descripcion_categoria;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Libro> libros;

    @Column(updatable = false)
    private Date createAt;
    @Column(name = "updateAt")
    private Date udpdateAt;

    /**
     * Constructor con parámetros.
     *
     * @param id_categoria
     * @param nombre_categoria
     * @param descripcion_categoria
     */
    public Categoria(Long id_categoria, String nombre_categoria, String descripcion_categoria) {
        this.id_categoria = id_categoria;
        this.nombre_categoria = nombre_categoria;
        this.descripcion_categoria = descripcion_categoria;
    }

    /**
     * Método de callback para establecer la fecha de creación antes de la persistencia.
     */
    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }

    /**
     * Método de callback para actualizar la fecha de modificación antes de una actualización.
     */
    @PreUpdate
    protected void onUpdate() {
        this.udpdateAt = new Date();
    }


}
