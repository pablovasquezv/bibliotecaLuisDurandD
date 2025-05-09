package com.complejo.educacional.luis.durand.durand.models;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @autor Pablo
 * @creates 03-01-2025 23:18
 * @proyect bibliotecaLuisDurandD
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@MappedSuperclass
public class Usuario extends Persona implements Serializable {
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
    @Column(name = "id_usuario")
    private Long id_usuario;

    @NotEmpty(message = "¡El nombre del usuario no debe ser vacío!")
    @Size(min = 4, max = 50, message = "¡El campo no debe tener menos de 4 caracteres y más de 50!")
    @Column(name = "nombres_usuario")
    protected String nombres_usuario;

    @NotEmpty(message = "¡El campo del Apellido Paterno no debe ser vacío!")
    @Size(min = 4, max = 20, message = "El Apellido Paterno debe tener entre 4 y 20 caracteres!")
    @Column(name = "apellido_paterno_usuario")
    protected String apellido_paterno_usuario;

    @NotEmpty(message = "¡El campo del Apellido Materno no debe ser vacío!")
    @Size(min = 4, max = 20, message = "¡El Apellido Materno debe tener entre 4 y 20 caracteres!")
    @Column(name = "apellido_materno_usuario")
    protected String apellido_materno_usuario;

    @Column(name = "fecha_nacimiento_usuario")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date fecha_nacimiento_usuario;

    @NotEmpty(message = "¡El campo Género no puede ser vacío!")
    @Size(min = 2, max = 20, message = "El campo Género debe tener entre 1 y 20 caracteres!")
    @Column(name = "genero_usuario")
    protected String genero_usuario;

    @Min(value = 0, message = "¡La edad debe ser mayor a 0 años!")
    @Max(value = 120, message = "¡La edad no debe ser mayor a 120 años!")
    @Column(name = "edad_usuario")
    protected Integer edad_usuario;



    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

}
