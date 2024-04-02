package com.complejo.educacional.luis.durand.durand.interfaces;

/**
 * @author Pablo
 * @version 1.0
 * @create 31-03-2024 2:05
 * @project bibliotecaLuisDurandD
 */
public interface IGestionGeneros {
    public void agregarNombreGeneroPorConsola(String nuevoNombreGenero);
    public boolean nombreGeneroYaExisteEnBaseDeDatos(String nombreGenero);

}
