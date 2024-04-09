package com.complejo.educacional.luis.durand.durand.services;
//Import necesarios para la clase.

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.complejo.educacional.luis.durand.durand.dto.autor.AutorDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.autor.AutorDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.autor.AutorDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.models.Pais;
import com.complejo.educacional.luis.durand.durand.repositories.IPaisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complejo.educacional.luis.durand.durand.services.implementsServices.IAutorServices;
import com.complejo.educacional.luis.durand.durand.models.Autor;
import com.complejo.educacional.luis.durand.durand.repositories.IAutorRepository;
import com.complejo.educacional.luis.durand.durand.utils.Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Pablo
 */
@Slf4j
@Service
public class AutorServicesImp implements IAutorServices {
    @Autowired
    private IAutorRepository iAutorRepository;

    @Autowired
    private IPaisRepository iPaisRepository;

    @Autowired
    private Utils utils;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Método que guarda un nuevo guardado con la información proporcionada en autorDTORequest.
     *
     * @param autorDTORequest La información del nuevo autor a guardar.
     * @return Un objeto AutorDTORequest con la información del autor guardado.
     * @throws Exception Si ocurre un error durante el proceso de guardado del autor.
     */
    @Override
    @Transactional(readOnly = false)
    public AutorDTORequest saveAutor(AutorDTORequest autorDTORequest) throws Exception {
        try {
            Pais pais = iPaisRepository.getReferenceById(autorDTORequest.getId_pais());
            Autor createAutor = new Autor(
                    null,
                    autorDTORequest.getNombres_autor(),
                    autorDTORequest.getApellidos_autor(),
                    pais
            );
            log.info("---Inicio de creción Autor----" + objectMapper.writeValueAsString(autorDTORequest));
            createAutor = iAutorRepository.save(createAutor);
            log.info("Json de Salida =>" + objectMapper.writeValueAsString(createAutor));
            log.info("----Fin de método Creación Autor----");
            return new AutorDTORequest(
                    createAutor.getNombres_autor(),
                    createAutor.getApellidos_autor(),
                    createAutor.getPais().getId_pais()
            );
        } catch (Exception e) {
            log.error("Ocurrió un error al guardar el Autor: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al guardar el Autor");
        }
    }

    /**
     * Método que actualiza un autor según su ID con la información proporcionada en autorDTOResponseUpdate.
     *
     * @param id                     El ID del autor a actualizar.
     * @param autorDTOResponseUpdate La información actualizada del autor.
     * @return Un objeto AutorDTOResponseUpdate con la información actualizada del autor.
     * @throws Exception Si ocurre un error durante el proceso de actualización del autor.
     */
    @Override
    @Transactional(readOnly = false)
    public AutorDTOResponse updateAutor(Long id, AutorDTOResponseUpdate autorDTOResponseUpdate) throws Exception {
        try {
            Optional<Autor> autorOptional = iAutorRepository.findById(id);
            log.info("---Inicio de actualización Autor----" + objectMapper.writeValueAsString(autorOptional));
            if (autorOptional.isPresent()) {
                Autor autor = autorOptional.get();
                Pais pais = iPaisRepository.getReferenceById(autorDTOResponseUpdate.getId_pais());
                autor.setNombres_autor(autorDTOResponseUpdate.getNombres_autor());
                autor.setApellidos_autor(autorDTOResponseUpdate.getApellidos_autor());
                autor.setPais(pais);
                Autor updatedAutor = iAutorRepository.save(autor);
                log.info("Json de Salida =>"  ,utils.imprimirLogSalida(updatedAutor));
                return new AutorDTOResponse(
                        updatedAutor.getId_autor(),
                        updatedAutor.getNombres_autor(),
                        updatedAutor.getApellidos_autor(),
                        updatedAutor.getPais().getId_pais()
                );
            } else {
                log.error("¡Ocurrió un error en la actualización del Autor!");
                throw new Exception("¡Ocurrió un error en la actualización del Autor!");
            }
        } catch (Exception e) {
            log.error("¡Ocurrió un error en la actualización del Autor con ID " + id + ": " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error en la actualización del Autor!");
        }
    }

    /**
     * Método que recupera una lista de objetos AutorDTOResponse ordenados según el criterio especificado.
     *
     * @param sort El criterio de ordenación.
     * @return Una lista de objetos AutorDTOResponse ordenados según el criterio especificado.
     * @throws Exception Si ocurre un error al recuperar la lista de autores ordenados.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AutorDTOResponse> findAllAutorSort(Sort sort) throws Exception {
        try {
            /**
             * Utilicé el método stream() y map() para convertir la lista de Autor en una lista de AutorDTOResponse
             * de forma más concisa.
             */
            List<Autor> autores = iAutorRepository.findAllAutorSort(sort);
            return autores.stream()
                    .map(autor -> new AutorDTOResponse(
                            autor.getId_autor(),
                            autor.getNombres_autor(),
                            autor.getApellidos_autor(),
                            autor.getPais().getId_pais()
                    ))
                    .collect(Collectors.toList());
            /**
             * Utilicé el método collect() junto con Collectors.toList() para recopilar los elementos mapeados
             * en una lista.
             */
        } catch (Exception e) {
            log.error("Ocurrió un error al listar todos los Autores: " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al listar todos los Autores!");
        }
    }

    /**
     * Método que recupera una página de objetos AutorDTOResponse utilizando paginación y ordenación.
     *
     * @param pageable La información de paginación y ordenación.
     * @return Una página de objetos AutorDTOResponse.
     * @throws Exception Si ocurre un error al recuperar la página de autores.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AutorDTOResponse> findAllAutorPage(Pageable pageable) throws Exception {
        try {
            Page<Autor> autores = iAutorRepository.findAllAutorPage(pageable);
            /**
             * Utilicé el método stream() y map() para convertir la lista de autor en una lista
             * de AutorDTOResponse de forma más concisa.
             */
            List<AutorDTOResponse> autorDTOResponses = autores.stream()
                    .map(autor -> new AutorDTOResponse(
                            autor.getId_autor(),
                            autor.getNombres_autor(),
                            autor.getApellidos_autor(),
                            autor.getPais().getId_pais()
                    ))
                    .collect(Collectors.toList());
            /**
             * Utilicé el método collect() junto con Collectors.toList() para recopilar los elementos mapeados
             * en una lista.
             */
            return new PageImpl<>(autorDTOResponses, pageable, autores.getTotalElements());
            /**
             * Creé un nuevo objeto PageImpl para devolver una página de resultados con la lista de AutorDTOResponse,
             * el objeto Pageable original y el número total de elementos.
             */
        } catch (Exception e) {
            log.error("Ocurrió un error al listar todos los Autores: " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al listar todos los Autores!");
        }
    }

    /**
     * Método que qusca un autor por su ID y devuelve su información en un objeto AutorDTOResponse.
     *
     * @param id El ID del autor a buscar.
     * @return Un objeto AutorDTOResponse con la información del autor.
     * @throws Exception Si el género no se encuentra o si ocurre un error durante la búsqueda.
     */
    @Override
    @Transactional(readOnly = true)
    public AutorDTOResponse findByIdAutor(long id) throws Exception {
        try {
            Autor autor = iAutorRepository.findByIdAutor(id);
            if (autor == null) {
                throw new Exception("¡Autor not found!");
            }
            return new AutorDTOResponse(
                    autor.getId_autor(),
                    autor.getNombres_autor(),
                    autor.getApellidos_autor(),
                    autor.getPais().getId_pais()
            );
        } catch (Exception e) {
            log.error("Ocurrió un error al buscar el Autor con ID " + id + ": " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al buscar el Autor!");
        }
    }

    /**
     * Método para eliminar un autor según su ID.
     *
     * @param id El ID del autor a eliminar.
     * @return true si se elimina el autor con éxito.
     * @throws Exception Si ocurre un error durante el proceso de eliminación del autor.
     */
    @Override
    @Transactional(readOnly = false)
    public boolean deleteAutorById(long id) throws Exception {
        try {
            if (iAutorRepository.existsById(id)) {
                log.info("Eliminar Autor con ID: " + id);
                iAutorRepository.deleteById(id);
                return true;
            } else {
                log.error("¡No existe el ID del Autor!");
                throw new Exception("No existe el ID del Autor");
            }
        } catch (Exception e) {
            log.error("Ocurrió un error al eliminar el Autor con ID " + id + ": " + e.getCause().toString());
            throw new Exception("Ocurrió un error al eliminar el Autor!");
        }
    }
}
