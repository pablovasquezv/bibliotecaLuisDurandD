package com.complejo.educacional.luis.durand.durand.services.implementsServices;
//Import necesarias para la interface.

import java.util.List;

import com.complejo.educacional.luis.durand.durand.dto.autor.AutorDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.autor.AutorDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.autor.AutorDTOResponseUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * @author Pablo
 */
public interface IAutorServices {
    /**
     * Método que guarda un nuevo autor en la base de datos.
     *
     * @param autorDTORequest el objeto DTO que contiene la información del autor a guardar.
     * @return el objeto DTO del autor guardado.
     * @throws Exception si ocurre algún error durante el proceso de guardado.
     */
    public AutorDTORequest saveAutor(AutorDTORequest autorDTORequest) throws Exception;

    /**
     * Método que actualiza la información de un autor en la base de datos.
     *
     * @param id                     el identificador del autor a actualizar.
     * @param autorDTOResponseUpdate el objeto DTO que contiene la información actualizada del autor.
     * @return el objeto DTO del autor actualizado.
     * @throws Exception si ocurre algún error durante el proceso de actualización.
     */

    public AutorDTOResponse updateAutor(Long id, AutorDTOResponseUpdate autorDTOResponseUpdate) throws Exception;

    /**
     * Método que encuentra y devuelve una lista de autores ordenada según el criterio especificado.
     *
     * @param sort el criterio de ordenamiento a aplicar a la lista de autores.
     * @return una lista de objetos DTO de autores ordenada según el criterio especificado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda y ordenamiento.
     */

    public List<AutorDTOResponse> findAllAutorSort(Sort sort) throws Exception;

    /**
     * Método que encuentra y devuelve una página de objetos DTO de autores, aplicando paginación y ordenamiento según
     * el objeto Pageable proporcionado.
     *
     * @param pageable el objeto Pageable que define la paginación y el ordenamiento de la lista de géneros.
     * @return una página de objetos DTO de autores según el criterio de paginación y ordenamiento especificado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda, paginación u ordenamiento.
     */

    public Page<AutorDTOResponse> findAllAutorPage(Pageable pageable) throws Exception;

    /**
     * Método que encuentra y devuelve un objeto DTO de autor según el identificador proporcionado.
     *
     * @param id el identificador del autor a buscar.
     * @return el objeto DTO del autor encontrado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda.
     */

    public AutorDTOResponse findByIdAutor(long id) throws Exception;

    /**
     * Método que elimina un autor de la base de datos según el identificador proporcionado.
     *
     * @param id el identificador del autor a eliminar.
     * @return true si el autor se elimina con éxito, false si no se encuentra el autor o no se puede eliminar.
     * @throws Exception si ocurre algún error durante el proceso de eliminación.
     */

    public boolean deleteAutorById(long id) throws Exception;
}
