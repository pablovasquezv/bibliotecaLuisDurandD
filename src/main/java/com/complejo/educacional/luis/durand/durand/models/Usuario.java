package com.complejo.educacional.luis.durand.durand.models;
//Import necesarios para la clase.

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author Pablo
 * @version 1.0
 * @create 08-04-2024 23:56
 * @project bibliotecaLuisDurandD
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "usuario")

public class Usuario  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    protected Long id_usuario;

    @Column(name = "nombres_usuario")
    protected String nombres;

    @Column(name = "apellido_paterno_usuario")
    protected String apellidoPaterno;

    @Column(name = "apellido_materno_usuario")
    protected String apellidoMaterno;

    @Column(name = "telefono_usuario")
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
    /**
     * Constructor con parámetros de la clase Usuario.
     *
     * @param nombresUsuario        El nombre del usuario.
     * @param apellidoPaternoUsuario        El apellido paterno del usuario.
     * @param apellidoMaternoUsuario        El apellido materno del usuario.
     * @param telefonoUsuario        El número de teléfono del usuario.
     * @param emailUsuario        El correo electrónico del usuario.
     */
    public Usuario(String nombresUsuario, String apellidoPaternoUsuario, String apellidoMaternoUsuario,
                   String telefonoUsuario, String emailUsuario) {
        this.nombres = nombresUsuario;
        this.apellidoPaterno = apellidoPaternoUsuario;
        this.apellidoMaterno = apellidoMaternoUsuario;
        this.telefono = telefonoUsuario;
        this.email = emailUsuario;
    }


    /**
     * Constructor de la clase Usuario con identificador.
     *
     * @param id_usuario             El identificador del usuario.
     * @param nombresUsuario         El nombre del usuario.
     * @param apellidoPaternoUsuario El apellido paterno del usuario.
     * @param apellidoMaternoUsuario El apellido materno del usuario.
     * @param telefonoUsuario        El número de teléfono del usuario.
     * @param emailUsuario           El correo electrónico del usuario.
     */
    public Usuario(Long id_usuario, String nombresUsuario, String apellidoPaternoUsuario, String apellidoMaternoUsuario,
                   String telefonoUsuario, String emailUsuario) {
        this.id_usuario = id_usuario;
        this.nombres = nombresUsuario;
        this.apellidoPaterno = apellidoPaternoUsuario;
        this.apellidoMaterno = apellidoMaternoUsuario;
        this.telefono = telefonoUsuario;
        this.email = emailUsuario;
    }

}


