package com.complejo.educacional.luis.durand.durand.models;
//Import necesarias para la clase.

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.complejo.educacional.luis.durand.durand.utils.enums.EstadoPrestamo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * @version 1.0
 * @autor Pablo
 * @creates 10-05-2025 20:45
 * @proyect bibliotecaLuisDurandD
 */
@Entity
@Data
@Table(name = "prestamo")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"usuario", "libro"}) // Excluye relaciones
public class Prestamo implements Serializable{
    /**
     * Serializable:para hacer la persistencia del objeto y convertilo en una secuencia de Bytes para poder almacenarlo
     * en algún medio de almacenamiento en esta caso una BD.
     * @Column(name = "created_date", updatable = false): Esta anotación se utiliza para mapear el campo de la clase a
     * una columna en la base de datos. En este caso, el campo "createdDate" se mapeará a una columna llamada
     * "created_date", y el atributo "updatable = false" indica que este campo no se actualizará en la base de datos.
     *
     * @Temporal(TemporalType.TIMESTAMP): Esta anotación se utiliza para especificar el tipo de temporalidad de un
     * campo de fecha. En este caso, se indica que el campo "createdDate" se mapeará a un tipo de dato de fecha y hora
     * en la base de datos.
     *
     * @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s"): Esta anotación es parte de la biblioteca Jackson y
     * se utiliza para controlar el formato de serialización de fechas en JSON. En este caso, se especifica que el campo
     * "createdDate" se serializará como un número, utilizando el patrón "s".
     * @GeneratedValue genera automaticamente el id.
     * @Column Personalización para las columnas. unique = true(no se repita elvalor ingresado)
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
    @Column(name = "id_prestamo")
    private Long id_prestamo;

    // Muchos prestamos pueden pertenecer a un usuario
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotNull(message = "¡El campo usuario_id del prestamo no debe ser vacío!")
    @JoinColumn(name = "usuario_id",referencedColumnName = "id_usuario")
    @JsonBackReference
    private Usuario usuario;

    // Muchos prestamos pueden pertenecer a un libro
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotNull(message = "¡El campo libro_id del prestamo no debe ser vacío!")
    @JoinColumn(name = "libro_id",referencedColumnName = "id_libro")
    @JsonBackReference
    private Libro libro;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_devolucion_prevista", nullable = false)
    private LocalDate fechaDevolucionPrevista;

    @Column(name = "fecha_devolucion_real")
    private LocalDate fechaDevolucionReal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPrestamo estado;

    @Column(name = "multa")
    private BigDecimal multa;

    @NotEmpty(message = "¡Las observaciones del prestamo no debe ser vacío!")
    @Size(min = 4, max = 100, message = "¡El campo observaciones del prestamo debe tener 4 carácteres y 100 máximo !")
    @Column(name = "observaciones")
    private String observaciones;

    // This will not allow the createdAt column to be updated after creation
    @Column(name = "createdAt",updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Date createdAt;

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date updatedAt;
    // other getters and setters removed for brevitycopy
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

}
