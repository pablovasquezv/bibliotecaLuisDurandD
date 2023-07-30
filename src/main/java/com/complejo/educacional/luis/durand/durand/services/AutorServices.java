/**
 *
 */
package com.complejo.educacional.luis.durand.durand.services;

import java.util.List;
import java.util.Optional;

import com.complejo.educacional.luis.durand.durand.models.Pais;
import com.complejo.educacional.luis.durand.durand.repositories.IPaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complejo.educacional.luis.durand.durand.implementsServices.IAutorImplements;
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
public class AutorServices implements IAutorImplements {
    @Autowired
    private IAutorRepository iAutorRepository;

	@Autowired
	private IPaisRepository iPaisRepository;

    @Autowired
    private Utils utils;

    @Override
    @Transactional(readOnly = false)
    public Autor saveAutor(Autor autor) throws Exception {
        Autor respuesta = null;
        try {
            respuesta = new Autor();
			Pais pais=iPaisRepository.findById(autor.getPais().getId_pais()).orElse(null);
			autor.setPais(pais);
            log.info("---Inicio de creción Autor----" + utils.imprimirLogEntrada(autor));
            respuesta = iAutorRepository.save(autor);
            log.info("Json de Salida =>" + utils.imprimirLogSalida(autor));
            log.info("----Fin de método Creación Autor----");

        } catch (Exception e) {
            // TODO: handle exception
            log.error("Ocurrio un error =>" + e.getCause());
            throw new Exception(e.getCause());
        }
        return respuesta;
    }

    @Override
    @Transactional(readOnly = false)
    public Autor updateAutor(Long id, Autor autor) throws Exception {
        // TODO Auto-generated method stub
        try {
            Optional<Autor> autorOptional = iAutorRepository.findById(id);
            Autor autorUpdate = autorOptional.get();
            log.info("---Inicio de actualización Autor----" + utils.imprimirLogEntrada(autor));
            Pais pais = iPaisRepository.findById(autor.getPais().getId_pais()).orElse(null);
            autor.setPais(pais);
            autorUpdate = iAutorRepository.save(autor);
            log.info("Json de Salida =>" + utils.imprimirLogSalida(autorUpdate));
            log.info("----Fin de método de Actualización de un Autor----");
            return autorUpdate;
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception(e.getCause());
        }
    }

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

    @Override
    @Transactional(readOnly = true)
    public Autor findById(long id) throws Exception {
        // TODO Auto-generated method stub
        try {
            return iAutorRepository.findById(id);
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception(e.getCause());
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAutor(long id) throws Exception {
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
