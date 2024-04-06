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
@Table(name = "pais")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pais implements Serializable {
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
     * la entidad principal.Se aplica al campo país para indicar que un Autor puede tener muchas paises.
     * El parámetro mappedBy especifica el nombre del campo en la entidad Autor que mapea esta relación.
     * fetch = FetchType.LAZY= no carga todas paises solo trae la país (no carga objetos en memoría).
     * cascade = CascadeType.PERSIST: En caso de eliminar un Autor se elimina esté no el país.
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pais")
    private Long id_pais;

    @NotEmpty(message = "¡El nombre del País no debe ser vacío!")
    @Size(min = 4, max = 20, message = "¡El campo no debe tener 4 carácteres y 50 máximo !")
    @Column(name = "nombre_pais")
    private String nombre_pais;

    @JsonIgnore
    @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    // Cambiado CascadeType.MERGE a CascadeType.ALL
    private List<Autor> autores;

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
     * Constructor con parámetros.
     *
     * @param id_pais
     * @param nombre_pais
     */
    public Pais(Long id_pais, String nombre_pais) {
        this.id_pais = id_pais;
        this.nombre_pais = nombre_pais;
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
