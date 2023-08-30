package com.complejo.educacional.luis.durand.durand.services.implementsServices;

import com.complejo.educacional.luis.durand.durand.dto.CategoriaDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.CategoriaDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.CategoriaRequestUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
/**
 * @author Pablo
 *
 */
public interface ICategoriaServices {
    /**
     *
     * @param categoriaDTORequest
     * @return
     * @throws Exception
     */
    public CategoriaDTORequest saveCategoria(CategoriaDTORequest categoriaDTORequest) throws Exception;

    /**
     *
     * @param categoriaRequestUpdate
     * @return
     * @throws Exception
     */

    public CategoriaDTOResponse updateCategoria(CategoriaRequestUpdate categoriaRequestUpdate) throws Exception;

    /**
     *
     * @param sort
     * @return
     * @throws Exception
     */
    public List<CategoriaDTOResponse> findAllCategoriaSort(Sort sort) throws Exception;

    /**
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    public Page<CategoriaDTOResponse> findAllCategoriaPage(Pageable pageable) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public CategoriaDTOResponse findByIdCategoria(long id) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Object deleteCategoriaById(Long id) throws Exception;
}
