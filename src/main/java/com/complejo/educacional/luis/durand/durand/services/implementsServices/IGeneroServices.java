package com.complejo.educacional.luis.durand.durand.services.implementsServices;
//Import necesarias para la clase.
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
     * @param generoDTORequest
     * @return
     * @throws Exception
     */
    public GeneroDTORequest saveGenero(GeneroDTORequest generoDTORequest) throws Exception;

    /**
     * @param id
     * @param generoDTOResponseUpdate
     * @return
     * @throws Exception
     */
    public GeneroDTOResponseUpdate updatGenero(Long id, GeneroDTOResponseUpdate generoDTOResponseUpdate) throws Exception;

    /**
     * @param sort
     * @return
     * @throws Exception
     */
    public List<GeneroDTOResponse> findAllGeneroSort(Sort sort) throws Exception;

    /**
     * @param pageable
     * @return
     * @throws Exception
     */
    public Page<GeneroDTOResponse> findAllGeneroPage(Pageable pageable) throws Exception;

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public GeneroDTOResponse findByIdGenero(long id) throws Exception;

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deleteGeneroById(Long id) throws Exception;

}
