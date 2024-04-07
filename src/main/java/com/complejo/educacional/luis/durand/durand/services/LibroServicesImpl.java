package com.complejo.educacional.luis.durand.durand.services;


import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.models.Autor;
import com.complejo.educacional.luis.durand.durand.models.Categoria;
import com.complejo.educacional.luis.durand.durand.models.Editorial;
import com.complejo.educacional.luis.durand.durand.models.Genero;
import com.complejo.educacional.luis.durand.durand.repositories.IAutorRepository;
import com.complejo.educacional.luis.durand.durand.repositories.ICategoriaRepository;
import com.complejo.educacional.luis.durand.durand.repositories.IEditorialRepository;
import com.complejo.educacional.luis.durand.durand.repositories.IGeneroRepository;
import com.complejo.educacional.luis.durand.durand.repositories.ILibroRepository;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.ILibroServices;
import com.complejo.educacional.luis.durand.durand.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Pablo
 * @version 1.0
 * @create 06-04-2024 23:49
 * @project bibliotecaLuisDurandD
 */
@Service
public class LibroServicesImpl implements ILibroServices {
    @Autowired
    private ILibroRepository iLibroRepository;
    @Autowired
    private IAutorRepository iAutorRepository;
    @Autowired
    private ICategoriaRepository iCategoriaRepository;
    @Autowired
    private IEditorialRepository iEditorialRepository;
    @Autowired
    private IGeneroRepository iGeneroRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional(readOnly = false)
    @Override
    public LibroDTORequest saveLibro(LibroDTORequest libroDTORequest) throws Exception {
        try {

        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public LibroDTOResponse updateLibro(LibroDTOResponseUpdate libroDTOResponseUpdate) throws Exception {
        return null;
    }

    @Override
    public LibroDTOResponse findAllLibroSort(Sort sort) throws Exception {
        return null;
    }

    @Override
    public LibroDTOResponse findAllLibroPage(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public LibroDTOResponse findByIdLibro(long id) throws Exception {
        return null;
    }

    @Override
    public boolean deleteLibroById(long id) throws Exception {
        return false;
    }
}
