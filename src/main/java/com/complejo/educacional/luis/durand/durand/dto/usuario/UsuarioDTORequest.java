package com.complejo.educacional.luis.durand.durand.dto.usuario;
//Import necesarios para la clase.

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
 * @autor Pablo
 * @create 12-04-2024 20:43
 * @project bibliotecaLuisDurandD
 * @Version 1.0
 */
public class UsuarioDTORequest {

    protected String nombres_usuario;
    protected String apellido_materno_usuario;
    protected String apellido_paterno_usuario;
    protected String telefono_usuario;
    protected String email_usuario;
}
