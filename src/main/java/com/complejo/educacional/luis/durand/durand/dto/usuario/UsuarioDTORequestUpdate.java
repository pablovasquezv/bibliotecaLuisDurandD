package com.complejo.educacional.luis.durand.durand.dto.usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = "autoresIds") // Si quieres evitar imprimir la lista completa en logs
@Builder

/**
 * @version 1.0
 * @autor Pablo
 * @creates 10-05-2025 17:20
 * @proyect bibliotecaLuisDurandD
 */
public class UsuarioDTORequestUpdate {
    private String nombres_usuario;
    private String apellido_paterno_usuario;
    private String apellido_materno_usuario;
    private Date fecha_nacimiento_usuario;
    private String genero_usuario;
    private Integer edad_usuario;
    private Date updatedAt;
}
