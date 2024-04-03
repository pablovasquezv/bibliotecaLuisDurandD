package com.complejo.educacional.luis.durand.durand.services.implementsServices;
//Import necesarios para la clase.
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponseUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Pablo
 * @version 1.0
 * @create 30-03-2024 1:25
 * @project bibliotecaLuisDurandD
 */
public interface IGeneroServices {

    /**
     * Método que guarda un nuevo género en la base de datos.
     *
     * @param generoDTORequest el objeto DTO que contiene la información del género a guardar.
     * @return el objeto DTO del género guardado.
     * @throws Exception si ocurre algún error durante el proceso de guardado.
     */
    public GeneroDTORequest saveGenero(GeneroDTORequest generoDTORequest) throws Exception;

    /**
     * Método que actualiza la información de un género en la base de datos.
     *
     * @param id el identificador del género a actualizar.
     * @param generoDTOResponseUpdate el objeto DTO que contiene la información actualizada del género.
     * @return el objeto DTO del género actualizado.
     * @throws Exception si ocurre algún error durante el proceso de actualización.
     */
    public GeneroDTOResponseUpdate updateGenero(Long id, GeneroDTOResponseUpdate generoDTOResponseUpdate) throws Exception;

    /**
     * Método que encuentra y devuelve una lista de géneros ordenada según el criterio especificado.
     *
     * @param sort el criterio de ordenamiento a aplicar a la lista de géneros.
     * @return una lista de objetos DTO de géneros ordenada según el criterio especificado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda y ordenamiento.
     */
    public List<GeneroDTOResponse> findAllGeneroSort(Sort sort) throws Exception;

    /**
     * Método que encuentra y devuelve una página de objetos DTO de géneros, aplicando paginación y ordenamiento según
     * el objeto Pageable proporcionado.
     *
     * @param pageable el objeto Pageable que define la paginación y el ordenamiento de la lista de géneros.
     * @return una página de objetos DTO de géneros según el criterio de paginación y ordenamiento especificado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda, paginación u ordenamiento.
     */
    public Page<GeneroDTOResponse> findAllGeneroPage(Pageable pageable) throws Exception;

    /**
     * Método que encuentra y devuelve un objeto DTO de género según el identificador proporcionado.
     *
     * @param id el identificador del género a buscar.
     * @return el objeto DTO del género encontrado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda.
     */
    public GeneroDTOResponse findByIdGenero(long id) throws Exception;

    /**
     * Método que elimina un género de la base de datos según el identificador proporcionado.
     *
     * @param id el identificador del género a eliminar.
     * @return true si el género se elimina con éxito, false si no se encuentra el género o no se puede eliminar.
     * @throws Exception si ocurre algún error durante el proceso de eliminación.
     */
    public boolean deleteGeneroById(Long id) throws Exception;

}
