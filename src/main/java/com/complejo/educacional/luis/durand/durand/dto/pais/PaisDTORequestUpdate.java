package com.complejo.educacional.luis.durand.durand.dto.pais;

import com.complejo.educacional.luis.durand.durand.models.Autor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

import java.util.ArrayList;
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
@ToString(exclude = "autoresIds") // Si quieres evitar imprimir la lista completa en logs
@Builder
public class PaisDTORequestUpdate {
    //Atributos
    private Long id_pais;
    private String nombre_pais;
    private List<Long> autoresIds;
    private Date createdAt;
    private Date updatedAt;
}
