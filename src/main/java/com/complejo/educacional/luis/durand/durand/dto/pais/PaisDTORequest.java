package com.complejo.educacional.luis.durand.durand.dto.pais;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.Date;
import java.util.List;

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
@Builder
public class PaisDTORequest {
    //Atributos
    private String nombre_pais;
    private List<Long> autoresIds;
    private Date createdAt;
    private Date updatedAt;
}
