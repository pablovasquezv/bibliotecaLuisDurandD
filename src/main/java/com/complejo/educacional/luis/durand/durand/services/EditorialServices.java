package com.complejo.educacional.luis.durand.durand.services;

import com.complejo.educacional.luis.durand.durand.implementsServices.IEditorialImplements;
import com.complejo.educacional.luis.durand.durand.models.Editorial;
import com.complejo.educacional.luis.durand.durand.repositories.IEditorialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EditorialServices implements IEditorialImplements {
    @Autowired
    private IEditorialRepository iEditorialRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = false)
    public Editorial saveEditorial(Editorial editorial) throws Exception {
        Editorial respuest = null;
        try {
            respuest = new Editorial();
            log.info("¡Creación de Editorial");
            respuest = iEditorialRepository.save(editorial);
            log.info("¡Editorial creada!" + objectMapper.writeValueAsString(editorial));
        } catch (Exception exception) {
            log.error("Falló la creación de la Editorial =>", exception);
        }
        return respuest;
    }

    @Override
    @Transactional(readOnly = false)
    public Editorial updateEditorial(Long id, Editorial editorial) throws Exception {
        Optional<Editorial> optionalEditorial = null;
        Editorial editorialUpdate = null;
        try {
            optionalEditorial = iEditorialRepository.findById(id);
            editorialUpdate = optionalEditorial.get();
            editorialUpdate = iEditorialRepository.save(editorial);
            log.info("¡Editorial Actualizada!"+objectMapper.writeValueAsString(iEditorialRepository.save(editorial)));
        } catch (Exception e) {
            log.error("Falló la actualización de la Editorial =>", e);
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Editorial> findAllEditorialSort(Sort sort) throws Exception {
        return iEditorialRepository.findAllEditorialSort(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Editorial> findAllEditorialPage(Pageable pageable) throws Exception {
        return iEditorialRepository.findAllEditorialPage(pageable);
    }

    @Override
    public Editorial findByIdEditorial(long id) throws Exception {
        return iEditorialRepository.findByIdEditorial(id);
    }

    @Override
    public Object deleteEditorialById(Long id) throws Exception {
        Object respuesta= null;
        try {
            log.info("¡Eliminar Editorial!");
            iEditorialRepository.deleteById(id);
        }catch (Exception e){
            log.error("Falló la Eliminación de la Editorial =>", e);
        }
        return null;
    }
}
