package com.complejo.educacional.luis.durand.durand.models;
//Import necesarias para la clase.

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@Data
@Table(name = "libro")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Libro implements Serializable{
    /**
     * Serializable:para hacer la persistencia del objeto y convertilo en una secuencia de Bytes para poder almacenarlo
     * en algún medio de almacenamiento en esta caso una BD.
     * @Column(name = "created_date", updatable = false): Esta anotación se utiliza para mapear el campo de la clase a
     * una columna en la base de datos. En este caso, el campo "createdDate" se mapeará a una columna llamada
     * "created_date", y el atributo "updatable = false" indica que este campo no se actualizará en la base de datos.
     *
     * @Temporal(TemporalType.TIMESTAMP): Esta anotación se utiliza para especificar el tipo de temporalidad de un
     * campo de fecha. En este caso, se indica que el campo "createdDate" se mapeará a un tipo de dato de fecha y hora
     * en la base de datos.
     *
     * @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s"): Esta anotación es parte de la biblioteca Jackson y
     * se utiliza para controlar el formato de serialización de fechas en JSON. En este caso, se especifica que el campo
     * "createdDate" se serializará como un número, utilizando el patrón "s".
     * @GeneratedValue genera automaticamente el id.
     * @Column Personalización para las columnas. unique = true(no se repita elvalor ingresado)
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
    @Column(name = "id_libro")
    private Long id_libro;

    @NotEmpty(message = "¡El título del libro no debe ser vacío!")
    @Size(min = 4, max = 50, message = "¡El campo titulo_libro debe tener 4 carácteres y 50 máximo !")
    @Column(name = "titulo_libro",unique = true)
    private String titulo_libro;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotNull(message = "¡El campo autor_id no debe ser vacío!")
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotNull(message = "¡El campo categoria_id no debe ser vacío!")
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotNull(message = "¡El campo editorial_id no debe ser vacío!")
    @JoinColumn(name = "editorial_id")
    private Editorial editorial;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotNull(message = "¡El campo genero_id no debe ser vacío!")
    @JoinColumn(name = "genero_id")
    private Genero genero;

    @NotEmpty(message = "¡La edición del libro no debe ser vacío!")
    @Min(value = 0, message = "¡La edición deber se mayor a 0 !")
    @Max(value = 120, message = "¡La edición no deber mayor a 120!")
    @Column(name = "edicion_libro")
    private Integer edicion_libro;

    @NotEmpty(message = "¡El número de páginas del libro no debe ser vacío!")
    @Min(value = 0, message = "¡El número de páginas debe ser mayor a 0 años!")
    @Max(value = 100000, message = "¡El número de páginas no deber mayor a 10000!")
    @Column(name = "paginas_libro")
    private int paginas_libro;

    // This will not allow the createdAt column to be updated after creation
    @Column(name = "createdAt",updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Date createdAt;

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date updatedAt;
    // other getters and setters removed for brevitycopy
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}

