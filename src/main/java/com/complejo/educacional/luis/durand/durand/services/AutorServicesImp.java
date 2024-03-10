/**
 *
 */
package com.complejo.educacional.luis.durand.durand.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.complejo.educacional.luis.durand.durand.dto.AutorDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.AutorDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.AutorDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.models.Pais;
import com.complejo.educacional.luis.durand.durand.repositories.IPaisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        Autor createAutor = null;
        try {

			Pais pais=iPaisRepository.getReferenceById(autorDTORequest.getId_pais());
            log.info("---Autor paisID----" + autorDTORequest.getId_pais());
            createAutor = new Autor(
                    null,
                    autorDTORequest.getNombres_autor(),
                    autorDTORequest.getApellidos_autor(),
                    pais,
                    autorDTORequest.getCreatedAt(),
                    autorDTORequest.getUpdatedAt()
            );
            log.info("---Inicio de creción Autor----" + objectMapper.writeValueAsString(autorDTORequest));
            iAutorRepository.save(createAutor);
            log.info("Json de Salida =>" + objectMapper.writeValueAsString(createAutor));
            log.info("----Fin de método Creación Autor----");

        } catch (Exception e) {
            // TODO: handle exception
            log.error("Ocurrio un error =>" + e.getCause().toString());
            throw new Exception(e.getCause().toString());
        }
        return autorDTORequest;
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
        AutorDTOResponse autorDTOResponse = null;
        Pais pais = iPaisRepository.getReferenceById(autorDTOResponseUpdate.getId_pais());
        Autor autorUpdate = new Autor(
                autorDTOResponseUpdate.getId_autor(),
                autorDTOResponseUpdate.getNombres_autor(),
                autorDTOResponseUpdate.getApellidos_autor(),
                pais,
                autorDTOResponseUpdate.getCreatedAt(),
                autorDTOResponseUpdate.getUpdatedAt()
        );
        try {
            Optional<Autor> autorOptional = iAutorRepository.findById(id);
            log.info("---Inicio de actualización Autor----" + objectMapper.writeValueAsString(autorOptional));
            if (autorOptional.isPresent()) {
                autorUpdate = iAutorRepository.save(autorUpdate);
                log.info("Json de Salida =>" + utils.imprimirLogSalida(autorUpdate));
                // Asignar los datos actualizados al objeto autorDTOResponse
                autorDTOResponse = new AutorDTOResponse(
                        autorUpdate.getId_autor(),
                        autorUpdate.getNombres_autor(),
                        autorUpdate.getApellidos_autor(),
                        autorUpdate.getPais().getId_pais(),
                        autorUpdate.getCreatedAt(),
                        autorUpdate.getUpdatedAt()
                );
            } else {
                log.error("¡Ocurrió un error en la actualización del Autor!");
            }

            log.info("----Fin de método de Actualización de un Autor----");
            return autorDTOResponse;
        } catch (Exception e) {
            log.error("¡Ocurrió un error en la actualización del Autor!" + autorUpdate.getId_autor() + "El error es: "
                    + e.getCause().toString());
        }
        return autorDTOResponse;
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
        // TODO Auto-generated method stub
        try {
            List<AutorDTOResponse>autorDTOResponses= new ArrayList<AutorDTOResponse>();
            for (Autor autor:iAutorRepository.findAllAutorSort(sort)) {
                autorDTOResponses.add(
                        new AutorDTOResponse(
                                autor.getId_autor(),
                                autor.getNombres_autor(),
                                autor.getApellidos_autor(),
                                autor.getPais().getId_pais(),
                                autor.getCreatedAt(),
                                autor.getUpdatedAt()
                        )
                );
            }
            return autorDTOResponses;
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception(e.getCause());
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
        // TODO Auto-generated method stub
        try {
            List<AutorDTOResponse>autorDTOResponses= new ArrayList<AutorDTOResponse>();
            for (Autor autor: iAutorRepository.findAllAutorPage(pageable)) {
                autorDTOResponses.add(
                        new AutorDTOResponse(
                                autor.getId_autor(),
                                autor.getNombres_autor(),
                                autor.getApellidos_autor(),
                                autor.getPais().getId_pais(),
                                autor.getCreatedAt(),
                                autor.getUpdatedAt()
                        )
                );
            }
            return (Page<AutorDTOResponse>) autorDTOResponses;
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception(e.getCause());
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
        // TODO Auto-generated method stub
        try {
           Autor autorId= iAutorRepository.findByIdAutor(id);
           if (autorId== null){
               throw new Exception("¡Categoría not found!");
           }
            return new AutorDTOResponse(
                    autorId.getId_autor(),
                    autorId.getNombres_autor(),
                    autorId.getApellidos_autor(),
                    autorId.getPais().getId_pais(),
                    autorId.getCreatedAt(),
                    autorId.getUpdatedAt()
            );
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception(e.getCause());
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
        // TODO Auto-generated method stub
        try {
            if (iAutorRepository.existsById(id)) {
                log.info("¡Eliminar Autor");
                iAutorRepository.deleteById(id);
            } else {
                log.error("¡No exite el Id del Autor!");
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception(e.getCause());
        }
    }

}
