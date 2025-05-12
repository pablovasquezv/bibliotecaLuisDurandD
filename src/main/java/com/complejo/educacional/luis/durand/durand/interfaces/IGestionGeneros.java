package com.complejo.educacional.luis.durand.durand.interfaces;

/**
 * @author Pablo
 * @version 1.0
 * @create 31-03-2024 2:05
 * @project bibliotecaLuisDurandD
 */
public interface IGestionGeneros {

    /**
     * Método que verifica si el nombre de género ya existe en la base de datos.
     *
     * @param nombreGenero El nombre del género a verificar.
     * @return true si el nombre de género ya existe en la base de datos, de lo contrario false.
     */
    public boolean nombreGeneroYaExisteEnBaseDeDatos(String nombreGenero);

}
