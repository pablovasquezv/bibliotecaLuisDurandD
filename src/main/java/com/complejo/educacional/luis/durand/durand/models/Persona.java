package com.complejo.educacional.luis.durand.durand.models;
//Import necesarios para la clase.

import javax.persistence.Inheritance;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.InheritanceType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
