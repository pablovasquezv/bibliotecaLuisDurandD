package com.complejo.educacional.luis.durand.durand.models;
//Import necesarios para la clase.

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


/**
 * @author Pablo
 * @version 1.0
 * @create 08-04-2024 23:44
 * @project bibliotecaLuisDurandD
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "nombres")
    @NotBlank
    protected String nombres;

    @Column(name = "apellido_materno")
    @NotBlank
    protected String apellidoMaterno;

    @Column(name = "apellido_paterno")
    @NotBlank
    protected String apellidoPaterno;

    @Column(name = "telefono")
    @Pattern(regexp = "^[0-9]+$")
    protected String telefono;

    @Column(name = "email")
    @Email
    protected String email;

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
