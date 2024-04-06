/**
 *
 */
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@Table(name = "autor")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Autor implements Serializable {
    /**
     * @Serializable: para hacer la persistencia del objeto y convertilo en una secuencia de Bytes para poder
     * almacenarlo en algún medio de almacenamiento en esta caso una BD.
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
     * @ManyToOne: relación uni direccional. fetch = FetchType.LAZY= no carga todos los obejetos de la lista, solo
     * trae el obejeto selecionado (no carga objetos en memoría).
     * cascade = CascadeType.PERSIST:En caso de eliminar un Autor se elimina esté no el País.
     * @OneToMany  Esta relación es unidireccional, lo que significa que la entidad secundaria no tiene conocimiento de
     * la entidad principal.Se aplica al campo autor para indicar que un libro puede tener muchas autores.
     * El parámetro mappedBy especifica el nombre del campo en la entidad Libro que mapea esta relación.
     * fetch = FetchType.LAZY= no carga todas editorial solo trae la editorial (no carga objetos en memoría).
     * cascade = CascadeType.PERSIST: En caso de eliminar un libro se elimina esté no la editorial.
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Long id_autor;

    @NotEmpty(message = "¡El nombre del autor no debe ser vacío!")
    @Size(min = 4, max = 50, message = "¡El campo no debe tener 4 carácteres y 50 máximo !")
    @Column(name = "nombres_autor")
    private String nombres_autor;

    @NotEmpty(message = "¡El apellido del autor no debe ser vacío!")
    @Size(min = 4, max = 50, message = "¡El campo no debe tener 4 carácteres y 50 máximo !")
    @Column(name = "apellidos_autor")
    private String apellidos_autor;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotNull(message = "¡El campo id_pais no debe ser vacío!")
    @JoinColumn(name = "id_pais", referencedColumnName = "id_pais")// Especificar la columna de unión en la tabla Autor
    private Pais pais;

    @JsonIgnore
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
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
     * @param id_autor
     * @param nombres_autor
     * @param apellidos_autor
     * @param pais
     */
    public Autor(Long id_autor, String nombres_autor, String apellidos_autor, Pais pais) {
        this.id_autor = id_autor;
        this.nombres_autor = nombres_autor;
        this.apellidos_autor = apellidos_autor;
        this.pais = pais;
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
