package com.complejo.educacional.luis.durand.durand.dto.prestamo;

import com.complejo.educacional.luis.durand.durand.utils.enums.EstadoPrestamo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = "autoresIds") // Si quieres evitar imprimir la lista completa en logs
@Builder
/**
 * @version 1.0
 * @autor Pablo
 * @creates 10-05-2025 23:46
 * @proyect bibliotecaLuisDurandD
 */
public class PrestamoDTORequestUpdate {
    private Long id_prestamo;
    private Long id_usuario;
    private Long id_libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;
    private EstadoPrestamo estado;
    private BigDecimal multa;
    private String observaciones;
    private Date createdAt;
    private Date updatedAt;
}
