package com.complejo.educacional.luis.durand.durand.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;


import static com.complejo.educacional.luis.durand.durand.utils.ColumNames.USERSLOAN;

/**
 * @version 1.0
 * @autor Pablo
 * @creates 03-01-2025 23:18
 * @proyect bibliotecaLuisDurandD
 */
@Entity
@Data
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "prestamos")//Para evitar bucles en las relaciones.
public class Usuario extends Persona implements Serializable {
    /**
     * Serializable:para hacer la persistencia del objeto y convertilo en una
     * secuencia de Bytes para poder almacenarlo en algún medio de almacenamiento en
     * esta caso una BD.
     *
     * @GeneratedValue genera automaticamente el id.
     * @Column Personalización para las columnas. unique = true(no se repita el
     *         valor ingresado)
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id_usuario;

    @OneToMany(mappedBy = USERSLOAN, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Prestamo> prestamos= new ArrayList<>();
}
