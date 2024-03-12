/**
 *
 */
package com.complejo.educacional.luis.durand.durand.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.complejo.educacional.luis.durand.durand.dto.AutorDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.AutorDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.AutorDTOResponseUpdate;
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
 *
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
     *
     * @param autorDTORequest
     * @return
     * @throws Exception
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
                    pais,
                    autorDTORequest.getCreatedAt(),
                    autorDTORequest.getUpdatedAt()
            );
            log.info("---Inicio de creción Autor----" + objectMapper.writeValueAsString(autorDTORequest));
            createAutor = iAutorRepository.save(createAutor);
            log.info("Json de Salida =>" + objectMapper.writeValueAsString(createAutor));
            log.info("----Fin de método Creación Autor----");
            return new AutorDTORequest(
                    createAutor.getNombres_autor(),
                    createAutor.getApellidos_autor(),
                    createAutor.getPais().getId_pais(),
                    createAutor.getCreatedAt(),
                    createAutor.getUpdatedAt()
            );
        } catch (Exception e) {
            log.error("Ocurrió un error al guardar el Autor: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al guardar el Autor");
        }
    }


    /**
     *
     * @param id
     * @param autorDTOResponseUpdate
     * @return
     * @throws Exception
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
                autor.setUpdatedAt(autorDTOResponseUpdate.getUpdatedAt());
                Autor updatedAutor = iAutorRepository.save(autor);
                log.info("Json de Salida =>" + utils.imprimirLogSalida(updatedAutor));
                return new AutorDTOResponse(
                        updatedAutor.getId_autor(),
                        updatedAutor.getNombres_autor(),
                        updatedAutor.getApellidos_autor(),
                        updatedAutor.getPais().getId_pais(),
                        updatedAutor.getCreatedAt(),
                        updatedAutor.getUpdatedAt()
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
     *
     * @param sort
     * @return
     * @throws Exception
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
                            autor.getPais().getId_pais(),
                            autor.getCreatedAt(),
                            autor.getUpdatedAt()
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
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AutorDTOResponse> findAllAutorPage(Pageable pageable) throws Exception {
        try {
            Page<Autor> autores = iAutorRepository.findAllAutorPage(pageable);
            /**
             * Utilicé el método stream() y map() para convertir la lista de Autor en una lista
             * de AutorDTOResponse de forma más concisa.
             */
            List<AutorDTOResponse> autorDTOResponses = autores.stream()
                    .map(autor -> new AutorDTOResponse(
                            autor.getId_autor(),
                            autor.getNombres_autor(),
                            autor.getApellidos_autor(),
                            autor.getPais().getId_pais(),
                            autor.getCreatedAt(),
                            autor.getUpdatedAt()
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
     *
     * @param id
     * @return
     * @throws Exception
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
                    autor.getPais().getId_pais(),
                    autor.getCreatedAt(),
                    autor.getUpdatedAt()
            );
        } catch (Exception e) {
            log.error("Ocurrió un error al buscar el Autor con ID " + id + ": " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al buscar el Autor!");
        }
    }


    /**
     *
     * @param id
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteAutorById(long id) throws Exception {
        try {
            if (iAutorRepository.existsById(id)) {
                log.info("¡Eliminar Autor con ID: " + id);
                iAutorRepository.deleteById(id);
            } else {
                log.error("No existe el ID del Autor!");
                throw new Exception("No existe el ID del Autor");
            }
        } catch (Exception e) {
            log.error("Ocurrió un error al eliminar el Autor con ID " + id + ": " + e.getCause().toString());
            throw new Exception("Ocurrió un error al eliminar el Autor!");
        }
    }


}
