package com.complejo.educacional.luis.durand.durand.models;
//Import necesarias para la clase.

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

import com.complejo.educacional.luis.durand.durand.interfaces.IGestionGeneros;
import com.complejo.educacional.luis.durand.durand.repositories.IGeneroRepository;

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
@Table(name = "genero")
@NoArgsConstructor
@AllArgsConstructor
@ToString
/**
 * @author Pablo
 * @version 1.0
 * @create 29-03-2024 23:24
 * @project bibliotecaLuisDurandD
 */
public class Genero implements Serializable, IGestionGeneros {
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
     * la entidad principal.Se aplica al campo género para indicar que un libro puede tener muchas géneros.
     * El parámetro mappedBy especifica el nombre del campo en la entidad Libro que mapea esta relación.
     * fetch = FetchType.LAZY= no carga todas géneros solo trae la género (no carga objetos en memoría).
     * cascade = CascadeType.PERSIST: En caso de eliminar un libro se elimina esté no el género.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Long id_genero;

    @NotEmpty(message = "¡El nombre del género no debe ser vacío!")
    @Size(min = 4, max = 50, message = "¡El campo género debe tener 4 carácteres minimo y 50 máximo !")
    @Column(name = "nombre_genero", unique = true)
    private String nombre_genero;

    @NotEmpty(message = "¡El campo descripción del género no debe ser vacío!")
    @Size(min = 4, max = 150, message = "¡El campo descripcion_genero debe tener 4 carácteres y 50 máximo !")
    @Column(name = "descripcion_genero")
    private String descripcion_genero;

    @JsonIgnore
    @OneToMany(mappedBy = "genero", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Libro> libros;

    // This will not allow the createdAt column to be updated after creation
    @Column(name = "createdAt",updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Date createdAt;

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date updatedAt;

    /**
     * Constructor con párametros.
     *
     * @param id_genero
     * @param nombre_genero
     * @param descripcion_genero
     */
    public Genero(Long id_genero, String nombre_genero, String descripcion_genero) {
        this.id_genero = id_genero;
        this.nombre_genero = nombre_genero;
        this.descripcion_genero = descripcion_genero;
    }

    // Método para verificar si el nombre ya existe en la base de datos
    public boolean nombreGeneroYaExisteEnBaseDeDatos(String nombreGenero) {
        IGeneroRepository iGeneroRepository = null; // Asigna el repositorio correspondiente

        // Lógica para verificar si el nombre ya existe en la base de datos
        boolean existe = iGeneroRepository.nombreGeneroYaExisteEnBaseDeDatos(nombreGenero);

        return existe; // Retorna true si el nombre ya existe, de lo contrario retorna false
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
