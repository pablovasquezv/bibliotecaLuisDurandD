package com.complejo.educacional.luis.durand.durand.models;
//Import necesarios para la clase.

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.AttributeOverride;
import javax.persistence.Table;

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
@AllArgsConstructor
@ToString
@Entity
@Table(name = "usuario")
@PrimaryKeyJoinColumn(name = "id_usuario")
@AttributeOverride(name = "nombres", column = @Column(name = "nombres_usuario"))
@AttributeOverride(name = "apellidoMaterno", column = @Column(name = "apellido_materno_usuario"))
@AttributeOverride(name = "apellidoPaterno", column = @Column(name = "apellido_paterno_usuario"))
@AttributeOverride(name = "telefono", column = @Column(name = "telefono_usuario"))
@AttributeOverride(name = "email", column = @Column(name = "email_usuario"))
public class Usuario extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    protected Long id_usuario;


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


