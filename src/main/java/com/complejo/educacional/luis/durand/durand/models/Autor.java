/**
 * 
 */
package com.complejo.educacional.luis.durand.durand.models;

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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "autor")
@NoArgsConstructor
@AllArgsConstructor
public class Autor implements Serializable {
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
	
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@NotNull(message = "¡El campo id_pais no debe ser vacío!")
	@JoinColumn(name = "id_pais")
	private Pais pais;

	// This will not allow the createdAt column to be updated after creation
	@Column(updatable = false)
	private Date createdAt;
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
