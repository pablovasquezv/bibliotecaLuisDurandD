package com.complejo.educacional.luis.durand.durand.dto.libro;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.models.Autor;
import com.complejo.educacional.luis.durand.durand.models.Categoria;
import com.complejo.educacional.luis.durand.durand.models.Editorial;
import com.complejo.educacional.luis.durand.durand.models.Genero;

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
 * @create 06-04-2024 14:24
 * @project bibliotecaLuisDurandD
 */
public class LibroDTOResponse {
    private Long id_libro;
    private String titulo_libro;
    private Long id_autor;
    private Long id_categoria;
    private Long id_editorial;
    private Long id_genero;
    private Integer edicion_libro;
    private Integer paginas_libro;
}
