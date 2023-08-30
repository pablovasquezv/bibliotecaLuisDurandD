package com.complejo.educacional.luis.durand.durand.services.implementsServices;

import com.complejo.educacional.luis.durand.durand.dto.EditorialDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.EditorialDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.EditorialDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
/**
 * @author Pablo
 *
 */

public interface IEditorialServices {
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
     * @param editorialDTORequestUpdate
     * @return
     * @throws Exception
     */
    public EditorialDTOResponse updateEditorial(Long id, EditorialDTORequestUpdate editorialDTORequestUpdate) throws Exception;

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

    public EditorialDTOResponse findByIdEditorial(long id) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */

    public Object deleteEditorialById(Long id) throws Exception;

}