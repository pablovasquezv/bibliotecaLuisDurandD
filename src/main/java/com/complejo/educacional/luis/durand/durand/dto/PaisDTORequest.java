package com.complejo.educacional.luis.durand.durand.dto;

import com.complejo.educacional.luis.durand.durand.models.Autor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ToString
public class PaisDTORequest {
    //Atributos
    private String nombre_pais;
    private List<Autor> autores = new ArrayList<>();
    private Date createdAt;
    private Date updatedAt;
}
