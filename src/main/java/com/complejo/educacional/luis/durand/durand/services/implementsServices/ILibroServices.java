package com.complejo.educacional.luis.durand.durand.services.implementsServices;

import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponseUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @version 1.0
 * @autor Pablo
 * @creates 10-05-2025 0:47
 * @proyect bibliotecaLuisDurandD
 */
public interface ILibroServices {
    /**
     * @param libroDTORequest
     * @return
     * @throws Exception
     */
    public LibroDTORequest saveLibro(LibroDTORequest libroDTORequest) throws Exception;

    /**
     * @param id
     * @param libroDTOResponseUpdate
     * @return
     * @throws Exception
     */
    public LibroDTOResponse updateLibro(Long id, LibroDTOResponseUpdate libroDTOResponseUpdate) throws Exception;

    /**
     * @param sort
     * @return
     * @throws Exception
     */
    public List<LibroDTOResponse> findAllLibroSort(Sort sort) throws Exception;

    /**
     * @param pageable
     * @return
     * @throws Exception
     */
    public Page<LibroDTOResponse> findAllLibroPage(Pageable pageable) throws Exception;

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public LibroDTOResponse findByIdAutor(long id) throws Exception;

    /**
     * @param id
     * @throws Exception
     */
    public void deleteAutorById(long id) throws Exception;

}
