package com.complejo.educacional.luis.durand.durand.services;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.models.Genero;
import com.complejo.educacional.luis.durand.durand.repositories.IGeneroRepository;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IGeneroServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Pablo
 * @version 1.0
 * @create 30-03-2024 19:27
 * @project bibliotecaLuisDurandD
 */
@Slf4j
@Service
public class GeneroServicesImpl implements IGeneroServices {
    @Autowired
    private  IGeneroRepository iGeneroRepository;

    @Autowired
    private  ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = false)
    public GeneroDTORequest saveGenero(GeneroDTORequest generoDTORequest) throws Exception {
        try {
            Genero creatGenero = new Genero(
                    null,
                    generoDTORequest.getNombre_genero(),
                    generoDTORequest.getDescripcion_genero(),
                    generoDTORequest.getCreatedAt(),
                    generoDTORequest.getUpdatedAt()
            );
            log.info("---Incio de la Creación del Género---"+objectMapper.writeValueAsString(creatGenero));
            creatGenero= iGeneroRepository.save(creatGenero);
            log.info("Json de Salida ==>"+objectMapper.writeValueAsString(creatGenero));
            log.info("---Incio de la Creación del Género---");
            return  new GeneroDTORequest(
                    creatGenero.getNombre_genero(),
                    creatGenero.getDescripcion_genero(),
                    creatGenero.getCreatedAt(),
                    creatGenero.getUpdatedAt()
            );
        }catch (Exception e){
            log.error("Ocurrió un error al guardar el Autor: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al guardar el Autor");
        }

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
