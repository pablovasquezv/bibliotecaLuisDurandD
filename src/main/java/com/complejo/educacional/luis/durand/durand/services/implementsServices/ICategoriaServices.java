package com.complejo.educacional.luis.durand.durand.services.implementsServices;
//Import necesarias para la interface.

import com.complejo.educacional.luis.durand.durand.dto.categoria.CategoriaDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.categoria.CategoriaDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.categoria.CategoriaRequestUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Pablo
 */
public interface ICategoriaServices {
    /**
     * Método que guarda un nuevo categoría en la base de datos.
     *
     * @param categoriaDTORequest el objeto DTO que contiene la información del categoría a guardar.
     * @return el objeto DTO del categoría guardado.
     * @throws Exception si ocurre algún error durante el proceso de guardado.
     */
    public CategoriaDTORequest saveCategoria(CategoriaDTORequest categoriaDTORequest) throws Exception;

    /**
     * Método que actualiza la información de un categoría en la base de datos.
     *
     * @param id                     el identificador del categoría a actualizar.
     * @param categoriaRequestUpdate el objeto DTO que contiene la información actualizada del categoría.
     * @return el objeto DTO del categoría actualizado.
     * @throws Exception si ocurre algún error durante el proceso de actualización.
     */


    public CategoriaDTOResponse updateCategoria(Long id, CategoriaRequestUpdate categoriaRequestUpdate) throws Exception;

    /**
     * Método que encuentra y devuelve una lista de categorías ordenada según el criterio especificado.
     *
     * @param sort el criterio de ordenamiento a aplicar a la lista de categorías.
     * @return una lista de objetos DTO de categorías ordenada según el criterio especificado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda y ordenamiento.
     */
    public List<CategoriaDTOResponse> findAllCategoriaSort(Sort sort) throws Exception;

    /**
     * Método que encuentra y devuelve una página de objetos DTO de categorías, aplicando paginación y ordenamiento según
     * el objeto Pageable proporcionado.
     *
     * @param pageable el objeto Pageable que define la paginación y el ordenamiento de la lista de categorías.
     * @return una página de objetos DTO de categorías según el criterio de paginación y ordenamiento especificado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda, paginación u ordenamiento.
     */
    public Page<CategoriaDTOResponse> findAllCategoriaPage(Pageable pageable) throws Exception;

    /**
     * Método que encuentra y devuelve un objeto DTO de categoría según el identificador proporcionado.
     *
     * @param id el identificador del categoría a buscar.
     * @return el objeto DTO del categoría encontrado.
     * @throws Exception si ocurre algún error durante el proceso de búsqueda.
     */
    public CategoriaDTOResponse findByIdCategoria(long id) throws Exception;

    /**
     * Método que elimina un categoría de la base de datos según el identificador proporcionado.
     *
     * @param id el identificador del categoría a eliminar.
     * @return true si el categoría se elimina con éxito, false si no se encuentra el categoría o no se puede eliminar.
     * @throws Exception si ocurre algún error durante el proceso de eliminación.
     */
    public boolean deleteCategoriaById(Long id) throws Exception;
}
