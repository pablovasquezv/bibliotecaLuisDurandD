package com.complejo.educacional.luis.durand.durand.dto.editorial;
//Import necesarias para la clase.

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @Setter: Para crear los métodos sett.
 * @Getter: Para crear los métodos gett.
 * @AllArgsConstructor: Constructor con parámetros.
 * @NoArgsConstructor:Constructor sin parámetros.
 * @ToString para obtener los datos.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

/**
 * @author Pablo
 * @version 1.0
 * @create 30-03-2024 0:42
 * @project bibliotecaLuisDurandD
 */

public class EditorialDTOResponse {
    //Atributos
    private Long id_editorial;
    private String nombre_editorial;
    private String descripcion_editorial;
    private String direccion_editorial;
    private String telefono_editorial;
    private String correoElectronico_editorial;
}
