package com.complejo.educacional.luis.durand.durand.services;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IGeneroServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Pablo
 * @version 1.0
 * @create 30-03-2024 19:27
 * @project bibliotecaLuisDurandD
 */
public class GeneroServicesImpl implements IGeneroServices {
    @Override
    public GeneroDTORequest saveGenero(GeneroDTORequest generoDTORequest) throws Exception {
        return null;
    }

    @Override
    public GeneroDTOResponseUpdate updatGenero(Long id, GeneroDTOResponseUpdate generoDTOResponseUpdate) throws Exception {
        return null;
    }

    @Override
    public List<GeneroDTOResponse> findAllGeneroSort(Sort sort) throws Exception {
        return null;
    }

    @Override
    public Page<GeneroDTOResponse> findAllGeneroPage(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public GeneroDTOResponse findByIdGenero(long id) throws Exception {
        return null;
    }

    @Override
    public Object deleteGeneroById(Long id) throws Exception {
        return null;
    }
}
