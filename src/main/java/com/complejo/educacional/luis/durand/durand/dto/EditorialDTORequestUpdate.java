package com.complejo.educacional.luis.durand.durand.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
/**
 * @author Pablo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EditorialDTORequestUpdate {
    //Atributos
    private Long id_editorial;
    private String nombre_editorial;
    private String descripcion_editorial;
    private String direccion_editorial;
    private String telefono_editorial;
    private String correoElectronico_editorial;
    private Date createAt;
    private Date updateAt;
}
