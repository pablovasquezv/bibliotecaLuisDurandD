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
 * @ToString: para obtener todos los datos
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaisDTORequest {
    //Atributos
    private String nombre_pais;
    private Date createdAt;
    private Date updatedAt;
}
