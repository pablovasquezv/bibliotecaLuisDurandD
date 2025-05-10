package com.complejo.educacional.luis.durand.durand.dto.libro;
//Import necesarios para la clase.
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
/**
 * @version 1.0
 * @autor Pablo
 * @creates 10-05-2025 0:32
 * @proyect bibliotecaLuisDurandD
 */
public class LibroDTORequest {
    //Atributos
    private Long id_libro;
    private String titulo_libro;
    private String descripcion_libro;
    private Long id_autor;
    private Long id_categoria;
    private Long id_editorial;
    private Long id_genero;
    private Integer edicion_libro;
    private Integer paginas_libro;
    private Date createdAt;
    private Date updatedAt;
}
