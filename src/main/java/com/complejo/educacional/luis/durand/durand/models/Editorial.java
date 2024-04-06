package com.complejo.educacional.luis.durand.durand.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
@Getter
@Setter
@Table(name = "editorial")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Editorial implements Serializable {
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
     * @OneToMany  Esta relación es unidireccional, lo que significa que la entidad secundaria no tiene conocimiento de
     * la entidad principal.Se aplica al campo editorial para indicar que un libro puede tener muchas editoriales.
     * El parámetro mappedBy especifica el nombre del campo en la entidad Libro que mapea esta relación.
     * fetch = FetchType.LAZY= no carga todas editorial solo trae la editorial (no carga objetos en memoría).
     * cascade = CascadeType.PERSIST: En caso de eliminar un  libro se elimina esté no la editorial.
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_editorial")
    private Long id_editorial;

    @NotEmpty(message = "¡El nombre del autor no debe ser vacío!")
    @Size(min = 3, max = 50, message = "¡El campo nombre categoría debe contener 4 carácters como minimo y 50 como máximo!")
    @Column(name = "nombre_editorial")
    private String nombre_editorial;

    @NotEmpty(message = "¡La descripción no debe ser vacía!")
    @Size(min = 4, max = 50, message = "¡La descripción debe contener 4 carácteres y 50 como máximo!")
    @Column(name = "descripcion_editorial")
    private String descripcion_editorial;

    @NotEmpty(message = "¡La Direccion de la Editorial no debe ser vacía!")
    @Size(min = 4, max = 100, message = "¡La dirección de la Editorial debe contener 4 carácteres y 50 como máximo!")
    private String direccion_editorial;
    @NotEmpty(message = "¡EL teléfono de la Editorial no debe ser vacío!")
    @Size(min = 4, max = 20, message = "¡El teléfono  debe contener 4 carácteres y 50 como máximo!")
    private String telefono_editorial;

    @NotEmpty(message = "¡El correo de la Editorial no debe ser vacío!")
    @Size(min = 4, max = 50, message = "¡El teléfono  debe contener 4 carácteres y 50 como máximo!")
    private String correoElectronico_editorial;

    @JsonIgnore
    @OneToMany(mappedBy = "editorial", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Libro> libros;

    // This will not allow the createdAt column to be updated after creation
    @Column(name = "createdAt", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Date createdAt;

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date updatedAt;

    /**
     * Constructor con parámetros.
     *
     * @param id_editorial
     * @param nombre_editorial
     * @param descripcion_editorial
     * @param direccion_editorial
     * @param telefono_editorial
     * @param correoElectronico_editorial
     */
    public Editorial(Long id_editorial, String nombre_editorial, String descripcion_editorial, String direccion_editorial,
                     String telefono_editorial, String correoElectronico_editorial) {
        this.id_editorial = id_editorial;
        this.nombre_editorial = nombre_editorial;
        this.descripcion_editorial = descripcion_editorial;
        this.direccion_editorial = direccion_editorial;
        this.telefono_editorial = telefono_editorial;
        this.correoElectronico_editorial = correoElectronico_editorial;
    }

    /**
     * Método de callback para establecer la fecha de creación antes de la persistencia.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    /**
     * Método de callback para actualizar la fecha de modificación antes de una actualización.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
