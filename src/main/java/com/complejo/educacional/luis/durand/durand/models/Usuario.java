package com.complejo.educacional.luis.durand.durand.models;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

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
@AttributeOverride(name = "nombres", column = @Column(name = "nombres_usuario"))
@AttributeOverride(name = "apellidoMaterno", column = @Column(name = "apellido_materno_usuario"))
@AttributeOverride(name = "apellidoPaterno", column = @Column(name = "apellido_paterno_usuario"))
@AttributeOverride(name = "telefono", column = @Column(name = "telefono_usuario"))
@AttributeOverride(name = "email", column = @Column(name = "email_usuario"))
public class Usuario extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    // No es necesario volver a definir los atributos de Persona, ya que se heredan de la clase base.

    // Puedes agregar atributos específicos para la clase Usuario, si es necesario

    // Puedes definir métodos adicionales específicos para la clase Usuario, si es necesario
}


