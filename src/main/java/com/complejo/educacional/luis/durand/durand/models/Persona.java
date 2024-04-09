package com.complejo.educacional.luis.durand.durand.models;

import lombok.*;

import javax.persistence.*;

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
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "persona")
public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "nombres")
    protected String nombres;

    @Column(name = "apellido_materno")
    protected String apellidoMaterno;

    @Column(name = "apellido_paterno")
    protected String apellidoPaterno;

    @Column(name = "telefono")
    protected String telefono;

    @Column(name = "email")
    protected String email;
}
