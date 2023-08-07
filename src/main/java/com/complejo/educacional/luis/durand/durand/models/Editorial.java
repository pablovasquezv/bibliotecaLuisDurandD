package com.complejo.educacional.luis.durand.durand.models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
     *         valor ingresado)
     * @Size: Solo para String o Char.
     * @NotEmpty: Campo obligatorío.
     * @Min: validación del valor mínimo del campo.
     * @Max: validación del valor máximo del campo.
     * @PrePersist: Ejecuta el método justo antes que el objeto sea creado.
     * @PreUpdate: Ejecuta el método cuando el objeto es modificado.
     * @NotNull: que nunca debe ser null.
     * @JoinColumn: el campo que unirá las tablas
     * @ManyToOne: relación uni direccional. fetch = FetchType.LAZY= no carga todos
     *             apoderados solo trae el alumno (no carga objetos en memoría).
     *             cascade = CascadeType.PERSIST:
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
    @Size(min = 4,max = 20, message = "¡El teléfono  debe contener 4 carácteres y 50 como máximo!")
    private String telefono_editorial;

    @NotEmpty(message = "¡El correo de la Editorial no debe ser vacío!")
    @Size(min = 4,max = 50, message = "¡El teléfono  debe contener 4 carácteres y 50 como máximo!")
    private String correoElectronico_editorial;

    /**
     * fetch = FetchType.LAZY: esto indica que la carga de la lista de libros se hará de manera "perezosa", es decir,
     * solo se cargarán los libros cuando se acceda a ellos explícitamente.
     * cascade = CascadeType.MERGE: esto indica que cuando se realice una operación de fusionar (merge) en la entidad
     * Autor, también se aplicará la operación a los libros asociados a ese autor. Esto permite sincronizar de
     * 	manera automática los cambios en la relación entre Autor y Libro.
     * 	@JsonIgnore se utiliza en la serialización de objetos en Java para indicar que una propiedad o campo debe ser
     * 	ignorado y no se incluirá en la representación JSON del objeto. Al agregar @JsonIgnore a la relación libros en
     * 	la clase Editorial, estás indicando que no quieres que la lista de libros se incluya en la representación JSON del
     * 	objeto Editorial.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "editorial", fetch = FetchType.LAZY, cascade =CascadeType.MERGE)
    private List<Libro> libros= new ArrayList<>();

    @Column(updatable = false)
    private Date createAt;

    private Date updateAt;

    @PrePersist
    protected void onCreate(){this.createAt=new Date();}

    @PreUpdate
    protected  void onUpdate(){this.updateAt = new Date();}


}
