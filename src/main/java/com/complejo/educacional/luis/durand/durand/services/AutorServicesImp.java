/**
 *
 */
package com.complejo.educacional.luis.durand.durand.services;

import java.util.List;
import java.util.Optional;

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
     * @param autor
     * @return
     * @throws Exception
     */

    @Override
    @Transactional(readOnly = false)
    public Autor saveAutor(Autor autor) throws Exception {
        Autor respuesta = null;
        try {
            respuesta = new Autor();
			Pais pais=iPaisRepository.findById(autor.getPais().getId_pais()).orElse(null);
			autor.setPais(pais);
            log.info("---Inicio de creción Autor----" + objectMapper.writeValueAsString(autor));
            respuesta = iAutorRepository.save(autor);
            log.info("Json de Salida =>" + objectMapper.writeValueAsString(autor));
            log.info("----Fin de método Creación Autor----");

        } catch (Exception e) {
            // TODO: handle exception
            log.error("Ocurrio un error =>" + e.getCause());
            throw new Exception(e.getCause());
        }
        return respuesta;
    }

    /**
     *
     * @param id
     * @param autor
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public Autor updateAutor(Long id, Autor autor) throws Exception {
        // TODO Auto-generated method stub
        Autor autorUpdate= null;
        try {
            Optional<Autor> autorOptional = iAutorRepository.findById(id);
            log.info("---Inicio de actualización Autor----" + objectMapper.writeValueAsString(autorOptional));
            autorUpdate = autorOptional.get();
            Pais pais = iPaisRepository.findById(autor.getPais().getId_pais()).orElse(null);
            autor.setPais(pais);
            autorUpdate = iAutorRepository.save(autor);
            log.info("Json de Salida =>" + utils.imprimirLogSalida(autorUpdate));
            log.info("----Fin de método de Actualización de un Autor----");
            return autorUpdate;
        } catch (Exception e) {
            // TODO: handle exception
            log.error("¡Ocurrio un error en la actualización del Autor!"+autorUpdate.getId_autor()+"El error es: "
                    +e.getCause().toString());
        }
        return autorUpdate;
    }

    /**
     *
     * @param sort
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public List<Autor> findAllAutorSort(Sort sort) throws Exception {
        // TODO Auto-generated method stub
        try {
            return iAutorRepository.findAllAutorSort(sort);
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
    public Page<Autor> findAllAutorPage(Pageable pageable) throws Exception {
        // TODO Auto-generated method stub
        try {
            return iAutorRepository.findAllAutorPage(pageable);
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
    public Autor findByIdAutor(long id) throws Exception {
        // TODO Auto-generated method stub
        try {
            return iAutorRepository.findById(id);
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
