package com.complejo.educacional.luis.durand.durand.services.implementsServices;
//Import necesarias para la interface.

import java.util.List;

import com.complejo.educacional.luis.durand.durand.dto.editorial.EditorialDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.editorial.EditorialDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.editorial.EditorialDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * @author Pablo
 */

public interface IEditorialServices {
    /**
     * Método que guarda un nuevo editorial en la base de datos.
     *
     * @param editorialDTORequest el objeto DTO que contiene la información del editorial a guardar.
     * @return el objeto DTO del editorial guardado.
     * @throws Exception si ocurre algún error durante el proceso de guardado.
     */
    public EditorialDTORequest saveEditorial(EditorialDTORequest editorialDTORequest) throws Exception;

    /**
     * Método que actualiza la información de un editorial en la base de datos.
     *
     * @param id                        el identificador del editorial a actualizar.
     * @param editorialDTORequestUpdate el objeto DTO que contiene la información actualizada del editorial.
     * @return el objeto DTO del editorial actualizado.
     * @throws Exception si ocurre algún error durante el proceso de actualización.
     */
    public EditorialDTOResponse updateEditorial(Long id, EditorialDTORequestUpdate editorialDTORequestUpdate) throws Exception;

    /**
     * Método que encuentra y devuelve una lista de editoriales ordenada según el criterio especificado.
     *
     * @param sort el criterio de ordenamiento a aplicar a la lista de editoriales.
     * @return una lista de objetos DTO de editoriales ordenada según el criterio especificado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda y ordenamiento.
     */
    public List<EditorialDTOResponse> findAllEditorialSort(Sort sort) throws Exception;

    /**
     * Método que encuentra y devuelve una página de objetos DTO de editoriales, aplicando paginación y ordenamiento según
     * el objeto Pageable proporcionado.
     *
     * @param pageable el objeto Pageable que define la paginación y el ordenamiento de la lista de editoriales.
     * @return una página de objetos DTO de editoriales según el criterio de paginación y ordenamiento especificado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda, paginación u ordenamiento.
     */

    public Page<EditorialDTOResponse> findAllEditorialPage(Pageable pageable) throws Exception;

    /**
     * Método que encuentra y devuelve un objeto DTO de editorial según el identificador proporcionado.
     *
     * @param id el identificador del editorial a buscar.
     * @return el objeto DTO del editorial encontrado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda.
     */

    public EditorialDTOResponse findByIdEditorial(long id) throws Exception;

    /**
     * Método que elimina un editorial de la base de datos según el identificador proporcionado.
     *
     * @param id el identificador del editorial a eliminar.
     * @return true si el editorial se elimina con éxito, false si no se encuentra el editorial o no se puede eliminar.
     * @throws Exception si ocurre algún error durante el proceso de eliminación.
     */

    public boolean deleteEditorialById(Long id) throws Exception;

}
