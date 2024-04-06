package com.complejo.educacional.luis.durand.durand.services.implementsServices;
//Import necesarias para la interface.

import java.util.List;

import com.complejo.educacional.luis.durand.durand.dto.pais.PaisDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.pais.PaisDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.pais.PaisDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Pablo
 */
public interface IPaisServices {
    /**
     * Método que guarda un nuevo país en la base de datos.
     *
     * @param paisDTORequest el objeto DTO que contiene la información del país a guardar.
     * @return el objeto DTO del país guardado.
     * @throws Exception si ocurre algún error durante el proceso de guardado.
     */
    public PaisDTORequest createPais(PaisDTORequest paisDTORequest) throws Exception;

    /**
     * Método que actualiza la información de un autor en la base de datos.
     *
     * @param id                   el identificador del país a actualizar.
     * @param paisDTORequestUpdate el objeto DTO que contiene la información actualizada del país.
     * @return el objeto DTO del país actualizado.
     * @throws Exception si ocurre algún error durante el proceso de actualización.
     */

    public PaisDTOResponse updatePais(Long id, PaisDTORequestUpdate paisDTORequestUpdate) throws Exception;

    /**
     * Método que encuentra y devuelve una lista de paises ordenada según el criterio especificado.
     *
     * @param sort el criterio de ordenamiento a aplicar a la lista de paises.
     * @return una lista de objetos DTO de países ordenada según el criterio especificado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda y ordenamiento.
     */

    public List<PaisDTOResponse> findAllPaisSort(Sort sort) throws Exception;

    /**
     * Método que encuentra y devuelve una página de objetos DTO de países, aplicando paginación y ordenamiento según
     * el objeto Pageable proporcionado.
     *
     * @param pageable el objeto Pageable que define la paginación y el ordenamiento de la lista de países.
     * @return una página de objetos DTO de países según el criterio de paginación y ordenamiento especificado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda, paginación u ordenamiento.
     */


    public Page<PaisDTOResponse> findAllPaisPageable(Pageable pageable) throws Exception;

    /**
     * Método que encuentra y devuelve un objeto DTO de país según el identificador proporcionado.
     *
     * @param id el identificador del país a buscar.
     * @return el objeto DTO del país encontrado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda.
     */

    public PaisDTOResponse findById(long id) throws Exception;

    /**
     * Método que elimina un país de la base de datos según el identificador proporcionado.
     *
     * @param id el identificador del país a eliminar.
     * @return true si el país se elimina con éxito, false si no se encuentra el país o no se puede eliminar.
     * @throws Exception si ocurre algún error durante el proceso de eliminación.
     */


    public boolean deletePaisById(long id) throws Exception;

}
