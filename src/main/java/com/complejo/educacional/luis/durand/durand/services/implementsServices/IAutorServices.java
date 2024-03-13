/**
 *
 */
package com.complejo.educacional.luis.durand.durand.services.implementsServices;

import java.util.List;

import com.complejo.educacional.luis.durand.durand.dto.AutorDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.AutorDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.AutorDTOResponseUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * @author Pablo
 *
 */
public interface IAutorServices {
    /**
     *
     * @param autorDTORequest
     * @return
     * @throws Exception
     */
    public AutorDTORequest saveAutor(AutorDTORequest autorDTORequest) throws Exception;

    /**
     *
     * @param id
     * @param autorDTOResponseUpdate
     * @return
     * @throws Exception
     */

    public AutorDTOResponse updateAutor(Long id, AutorDTOResponseUpdate autorDTOResponseUpdate) throws Exception;

    /**
     *
     * @param sort
     * @return
     * @throws Exception
     */

    public List<AutorDTOResponse> findAllAutorSort(Sort sort) throws Exception;

    /**
     *
     * @param pageable
     * @return
     * @throws Exception
     */

    public Page<AutorDTOResponse> findAllAutorPage(Pageable pageable) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */

    public AutorDTOResponse findByIdAutor(long id) throws Exception;

    /**
     *
     * @param id
     * @throws Exception
     */

    public void deleteAutorById(long id) throws Exception;
}
