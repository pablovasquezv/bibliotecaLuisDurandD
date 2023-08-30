package com.complejo.educacional.luis.durand.durand.services;

import com.complejo.educacional.luis.durand.durand.dto.CategoriaDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.CategoriaDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.CategoriaRequestUpdate;
import com.complejo.educacional.luis.durand.durand.repositories.ICategoriaRepository;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.ICategoriaServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoriaServicesImpl implements ICategoriaServices {
    @Autowired
    private ICategoriaRepository iCategoriaRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public CategoriaDTORequest saveCategoria(CategoriaDTORequest categoriaDTORequest) throws Exception {
        return null;
    }

    @Override
    public CategoriaDTOResponse updateCategoria(CategoriaRequestUpdate categoriaRequestUpdate) throws Exception {
        return null;
    }

    @Override
    public List<CategoriaDTOResponse> findAllCategoriaSort(Sort sort) throws Exception {
        return null;
    }

    @Override
    public Page<CategoriaDTOResponse> findAllCategoriaPage(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public CategoriaDTOResponse findByIdCategoria(long id) throws Exception {
        return null;
    }

    @Override
    public Object deleteCategoriaById(Long id) throws Exception {
        return null;
    }
}
