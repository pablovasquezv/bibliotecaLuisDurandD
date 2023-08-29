package com.complejo.educacional.luis.durand.durand.services;

import com.complejo.educacional.luis.durand.durand.dto.EditorialDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.EditorialDTOResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Pablo
 */
@Slf4j
@Service
public class EditorialServices implements IEditorialImplements {
    @Autowired
    private IEditorialRepository iEditorialRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /***
     *
     * @param editorialDTORequest
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public EditorialDTORequest saveEditorial(EditorialDTORequest editorialDTORequest) throws Exception {

        try {
            Editorial editorialCreate = new Editorial(
                    null,
                    editorialDTORequest.getNombre_editorial(),
                    editorialDTORequest.getDescripcion_editorial(),
                    editorialDTORequest.getDireccion_editorial(),
                    editorialDTORequest.getTelefono_editorial(),
                    editorialDTORequest.getCorreoElectronico_editorial(),
                    editorialDTORequest.getCreateAt(),
                    editorialDTORequest.getUpdateAt()
            );
            log.info("¡Creación de Editorial");
            iEditorialRepository.save(editorialCreate);
            log.info("¡Editorial creada!" + objectMapper.writeValueAsString(editorialDTORequest));
            return editorialDTORequest;
        } catch (Exception e) {
            log.error("Falló la creación de la Editorial =>", e.getCause().toString());
        }
        return editorialDTORequest;
    }

    /**
     *
     * @param id
     * @param editorialDTOResponse
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public EditorialDTOResponse updateEditorial(Long id, EditorialDTOResponse editorialDTOResponse) throws Exception {
        Optional<Editorial> optionalEditorial = null;
        Editorial editorialUpdate = null;
        try {
            optionalEditorial = iEditorialRepository.findById(id);
            editorialUpdate = optionalEditorial.get();
            iEditorialRepository.save(editorialUpdate);
            log.info("¡Editorial Actualizada!" + objectMapper.writeValueAsString(iEditorialRepository.save(editorialUpdate)));
            return editorialDTOResponse ;
        } catch (Exception e) {
            log.error("Falló la actualización de la Editorial =>", e.getCause().toString());
        }

        return editorialDTOResponse;
    }

    /**
     * @param sort
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public List<EditorialDTOResponse> findAllEditorialSort(Sort sort) throws Exception {
        List<EditorialDTOResponse> editorialDTOResponses = new ArrayList<EditorialDTOResponse>();
        for (Editorial editorial: iEditorialRepository.findAllEditorialSort(sort)){
            editorialDTOResponses.add(
                    new EditorialDTOResponse(
                            editorial.getId_editorial(),
                            editorial.getNombre_editorial(),
                            editorial.getDescripcion_editorial(),
                            editorial.getDireccion_editorial(),
                            editorial.getTelefono_editorial(),
                            editorial.getCorreoElectronico_editorial(),
                            editorial.getCreateAt(),
                            editorial.getUpdateAt()
                    )
            );
        }
        return editorialDTOResponses;
    }

    /**
     * @param pageable
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EditorialDTOResponse> findAllEditorialPage(Pageable pageable) throws Exception {
        List<EditorialDTOResponse> editorialDTOResponses = new ArrayList<EditorialDTOResponse>();
        for (Editorial editorial: iEditorialRepository.findAllEditorialPage(pageable)){
            editorialDTOResponses.add(
                    new EditorialDTOResponse(
                            editorial.getId_editorial(),
                            editorial.getNombre_editorial(),
                            editorial.getDescripcion_editorial(),
                            editorial.getDireccion_editorial(),
                            editorial.getTelefono_editorial(),
                            editorial.getCorreoElectronico_editorial(),
                            editorial.getCreateAt(),
                            editorial.getUpdateAt()
                    )
            );
        }
        return (Page<EditorialDTOResponse>) editorialDTOResponses;
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public Editorial findByIdEditorial(long id) throws Exception {
        return iEditorialRepository.findByIdEditorial(id);
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public Object deleteEditorialById(Long id) throws Exception {
        Object respuesta = null;
        try {
            log.info("¡Eliminar Editorial!");
            iEditorialRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Falló la Eliminación de la Editorial =>", e.getCause().toString());
        }
        return null;
    }
}
