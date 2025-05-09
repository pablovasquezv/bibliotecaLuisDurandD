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
public abstract class Persona {
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
