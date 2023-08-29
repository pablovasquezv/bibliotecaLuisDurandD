package com.complejo.educacional.luis.durand.durand.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Pablo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EditorialDTORequest {
    //Atributos
    private String nombre_editorial;
    private String descripcion_editorial;
    private String direccion_editorial;
    private String telefono_editorial;
    private String correoElectronico_editorial;
}
