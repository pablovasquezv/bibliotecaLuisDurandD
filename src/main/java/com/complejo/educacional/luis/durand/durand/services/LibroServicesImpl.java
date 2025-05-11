package com.complejo.educacional.luis.durand.durand.services;

import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.models.*;
import com.complejo.educacional.luis.durand.durand.repositories.*;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.ILibroServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 * @autor Pablo
 * @creates 10-05-2025 0:57
 * @proyect bibliotecaLuisDurandD
 */
@Slf4j
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
    private ObjectMapper objectMapper;

    @Override
    public LibroDTORequest saveLibro(LibroDTORequest libroDTORequest) throws Exception {
        try {
            Autor autor_id = iAutorRepository.getReferenceById(libroDTORequest.getId_autor());
            Categoria categoria_id = iCategoriaRepository.getReferenceById(libroDTORequest.getId_categoria());
            Editorial editorial_id = iEditorialRepository.getReferenceById(libroDTORequest.getId_editorial());
            Genero genero_id = iGeneroRepository.getReferenceById(libroDTORequest.getId_genero());
            Libro libroCreate = new Libro();
            libroCreate.setTitulo_libro(libroDTORequest.getTitulo_libro());
            libroCreate.setDescripcion_libro(libroDTORequest.getDescripcion_libro());
            libroCreate.setAutor(autor_id);
            libroCreate.setCategoria(categoria_id);
            libroCreate.setEditorial(editorial_id);
            libroCreate.setGenero(genero_id);
            libroCreate.setEdicion_libro(libroDTORequest.getEdicion_libro());
            libroCreate.setPaginas_libro(libroDTORequest.getPaginas_libro());
            libroCreate.setCreatedAt(libroDTORequest.getCreatedAt());
            libroCreate.setUpdatedAt(libroDTORequest.getUpdatedAt());
            log.info("--Inicio de la Creación del Libro" + objectMapper.writeValueAsString(libroDTORequest));
            iLibroRepository.save(libroCreate);
            log.info("--Json del Libro => " + objectMapper.writeValueAsString(iLibroRepository.save(libroCreate)));
            log.info("----Fin de método Creación Libro----");
            return new LibroDTORequest(
                    libroDTORequest.getTitulo_libro(),
                    libroDTORequest.getDescripcion_libro(),
                    libroDTORequest.getId_autor(),
                    libroDTORequest.getId_categoria(),
                    libroDTORequest.getId_editorial(),
                    libroDTORequest.getId_genero(),
                    libroDTORequest.getEdicion_libro(),
                    libroDTORequest.getPaginas_libro(),
                    libroDTORequest.getCreatedAt(),
                    libroDTORequest.getUpdatedAt()
            );
        } catch (Exception e) {
            log.error("Ocurrió un error al guardar el Libro: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al guardar el Libro :(");
        }
    }

    @Override
    public LibroDTOResponse updateLibro(Long id, LibroDTOResponseUpdate libroDTOResponseUpdate) throws Exception {
        return null;
    }

    @Override
    public List<LibroDTOResponse> findAllLibroSort(Sort sort) throws Exception {
        try {
            List<Libro> libros = iLibroRepository.findAllLibroSort(sort);
            return libros.stream().map(
                    libro -> new LibroDTOResponse(
                            libro.getId_libro(),
                            libro.getTitulo_libro(),
                            libro.getDescripcion_libro(),
                            libro.getAutor().getId_autor(),
                            libro.getCategoria().getId_categoria(),
                            libro.getEditorial().getId_editorial(),
                            libro.getGenero().getId_genero(),
                            libro.getEdicion_libro(),
                            libro.getPaginas_libro(),
                            libro.getCreatedAt(),
                            libro.getUpdatedAt()
                    )
            ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Ocurrió un error al Listar todos los libros: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al listar los libros");
        }
    }

    @Override
    public Page<LibroDTOResponse> findAllLibroPage(Pageable pageable) throws Exception {
        try {
            Page<Libro> libros = iLibroRepository.findAllLibroPage(pageable);
            /**
             * Utilicé el método stream() y map() para convertir la lista de Libro en una lista
             * de libroDTOResponses de forma más concisa.
             */
            List<LibroDTOResponse> libroDTOResponses = libros.stream().map(
                    libro -> new LibroDTOResponse(
                            libro.getId_libro(),
                            libro.getTitulo_libro(),
                            libro.getDescripcion_libro(),
                            libro.getAutor().getId_autor(),
                            libro.getCategoria().getId_categoria(),
                            libro.getEditorial().getId_editorial(),
                            libro.getGenero().getId_genero(),
                            libro.getEdicion_libro(),
                            libro.getPaginas_libro(),
                            libro.getCreatedAt(),
                            libro.getUpdatedAt()
                    )
            ).collect(Collectors.toList());
            /**
             * Utilicé el método collect() junto con Collectors.toList() para recopilar los elementos mapeados
             * en una lista.
             */
            return new PageImpl<>(libroDTOResponses,pageable,libros.getTotalElements());
            /**
             * Creé un nuevo objeto PageImpl para devolver una página de resultados con la lista de AutorDTOResponse,
             * el objeto Pageable original y el número total de elementos.
             */
        } catch (Exception e) {
            log.error("Ocurrió un error al Listar todos los libros: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al listar los libros");
        }
    }

    @Override
    public LibroDTOResponse findByIdAutor(long id) throws Exception {
        return null;
    }

    @Override
    public void deleteAutorById(long id) throws Exception {

    }
}
