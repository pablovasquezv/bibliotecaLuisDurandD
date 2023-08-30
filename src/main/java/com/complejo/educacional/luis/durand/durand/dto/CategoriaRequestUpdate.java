package com.complejo.educacional.luis.durand.durand.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Pablo
 * @Data: Para crear los gett y sett
 * @AllArgsConstructor: Constructor con parámetros
 * @NoArgsConstructor:Constructor sin parámetros
 * @ToString para obtener los datos
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoriaRequestUpdate {
    //Atributos
    private String nombre_categoria;
    private String descripcion_categoria;
    private Date createAt;
    private Date udpdateAt;
}