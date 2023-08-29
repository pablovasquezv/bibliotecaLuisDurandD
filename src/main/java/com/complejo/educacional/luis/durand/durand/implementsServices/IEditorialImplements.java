package com.complejo.educacional.luis.durand.durand.implementsServices;

import com.complejo.educacional.luis.durand.durand.dto.EditorialDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.EditorialDTOResponse;
import com.complejo.educacional.luis.durand.durand.models.Editorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
/**
 * @author Pablo
 *
 */

public interface IEditorialImplements {
    /**
     *
     * @param editorialDTORequest
     * @return
     * @throws Exception
     */
    public EditorialDTORequest saveEditorial (EditorialDTORequest editorialDTORequest) throws Exception;

    /**
     *
     * @param id
     * @param editorialDTOResponse
     * @return
     * @throws Exception
     */
    public EditorialDTOResponse updateEditorial(Long id, EditorialDTOResponse editorialDTOResponse) throws Exception;

    /**
     *
     * @param sort
     * @return
     * @throws Exception
     */

    public List<EditorialDTOResponse> findAllEditorialSort(Sort sort) throws Exception;

    /**
     *
     * @param pageable
     * @return
     * @throws Exception
     */

    public Page<EditorialDTOResponse> findAllEditorialPage(Pageable pageable) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */

    public Editorial findByIdEditorial(long id) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */

    public Object deleteEditorialById(Long id) throws Exception;

}
