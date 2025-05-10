package com.complejo.educacional.luis.durand.durand.dto.genero;
//Import necesarias para la clase.
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

import java.util.Date;

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
@Builder
/**
 * @author Pablo
 * @version 1.0
 * @create 30-03-2024 0:42
 * @project bibliotecaLuisDurandD
 */

public class GeneroDTORequest {
    //Atributos
    private String nombre_genero;
    private String descripcion_genero;
    private Date createdAt;
    private Date updatedAt;
}
