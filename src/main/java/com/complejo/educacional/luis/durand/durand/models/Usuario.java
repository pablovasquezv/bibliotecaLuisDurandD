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
    protected Long id;
}


