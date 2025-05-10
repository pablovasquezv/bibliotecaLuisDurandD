package com.complejo.educacional.luis.durand.durand.services.implementsServices;

import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;

/**
 * @version 1.0
 * @autor Pablo
 * @creates 10-05-2025 18:33
 * @proyect bibliotecaLuisDurandD
 */
public interface IUsuarioServices {

    /**
     * @param usuarioDTORequest
     * @return
     * @throws Exception
     */
    UsuarioDTORequest suaveUsuario(UsuarioDTORequest usuarioDTORequest) throws Exception;

    /**
     * @param id
     * @param update
     * @return
     * @throws Exception
     */
    UsuarioDTOResponse updateUsuario(Long id, UsuarioDTORequestUpdate update) throws Exception;

    /**
     * @param sort
     * @return
     * @throws Exception
     */
    List<UsuarioDTOResponse> findAllUsuarioSort(Sort sort) throws Exception;

    /**
     * @param pageable
     * @return
     * @throws Exception
     */
    Page<UsuarioDTOResponse> findAllUsuarioPage(Pageable pageable) throws Exception;

    /**
     * @param id
     * @return
     * @throws Exception
     */
    UsuarioDTOResponse findByIdUsuario(long id) throws Exception;

    /**
     * @param id
     * @throws Exception
     */
    void deleteByIdUsuario(long id) throws Exception;

}
