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
public class AutorDTOResponse {
    //Atributos
    private Long id_autor;
    private String nombres_autor;
    private String apellidos_autor;
    private Long id_pais;
    private Date createdAt;
    private Date updatedAt;

}
