package com.complejo.educacional.luis.durand.durand.services;
//Import necesarios para la clase.

import com.complejo.educacional.luis.durand.durand.dto.editorial.EditorialDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.editorial.EditorialDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.editorial.EditorialDTOResponse;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IEditorialServices;
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
public class EditorialServicesImpl implements IEditorialServices {
    @Autowired
    private IEditorialRepository iEditorialRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Método que guarda una nueva editorial con la información proporcionada en editorialDTORequest.
     *
     * @param editorialDTORequest La información de la nueva editorial a guardar.
     * @return Un objeto editorialDTORequest con la información de la editorial guardado.
     * @throws Exception Si ocurre un error durante el proceso de guardado de la editorial.
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
                    editorialDTORequest.getCorreoElectronico_editorial()
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
     * Método que actualiza una editorial según su ID con la información proporcionada en editorialDTORequestUpdate.
     *
     * @param id                     El ID de la editorial a actualizar.
     * @param editorialDTORequestUpdate La información actualizada de la editorial.
     * @return Un objeto EditorialDTOResponse con la información actualizada de la editorial.
     * @throws Exception Si ocurre un error durante el proceso de actualización de la editorial.
     */
    @Override
    @Transactional(readOnly = false)
    public EditorialDTOResponse updateEditorial(Long id, EditorialDTORequestUpdate editorialDTORequestUpdate) throws Exception {
        Optional<Editorial> optionalEditorial = null;
        EditorialDTOResponse editorialDTOResponse = new EditorialDTOResponse();
        Editorial editorialUpdate = new Editorial(
                editorialDTORequestUpdate.getId_editorial(),
                editorialDTORequestUpdate.getNombre_editorial(),
                editorialDTORequestUpdate.getDescripcion_editorial(),
                editorialDTORequestUpdate.getDireccion_editorial(),
                editorialDTORequestUpdate.getTelefono_editorial(),
                editorialDTORequestUpdate.getCorreoElectronico_editorial()
        );

        try {
            optionalEditorial = iEditorialRepository.findById(id);
            if (optionalEditorial.isPresent()) {
                log.info("¡Editorial  optionalEditorial! " + objectMapper.writeValueAsString(iEditorialRepository.findById(id)));
                // editorialUpdate = optionalEditorial.get();
                log.info("¡Editorial  editorialUpdate ! " + objectMapper.writeValueAsString(optionalEditorial.get()));
                editorialUpdate = iEditorialRepository.save(editorialUpdate);
                log.info("¡Editorial Actualizada!" + objectMapper.writeValueAsString(iEditorialRepository.save(editorialUpdate)));
            } else {
                log.error("Falló la actualización de la Editorial =>");
            }
            editorialDTOResponse = new EditorialDTOResponse(
                    editorialUpdate.getId_editorial(),
                    editorialUpdate.getNombre_editorial(),
                    editorialUpdate.getDescripcion_editorial(),
                    editorialUpdate.getDireccion_editorial(),
                    editorialUpdate.getTelefono_editorial(),
                    editorialUpdate.getCorreoElectronico_editorial()
            );
            return editorialDTOResponse;
        } catch (Exception e) {
            log.error("Falló la actualización de la Editorial =>", e.getCause().toString());
        }

        return editorialDTOResponse;
    }

    /**
     * Método que recupera una lista de objetos EditorialDTOResponse ordenados según el criterio especificado.
     *
     * @param sort El criterio de ordenación.
     * @return Una lista de objetos EditorialDTOResponse ordenados según el criterio especificado.
     * @throws Exception Si ocurre un error al recuperar la lista de las editoriales ordenados.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EditorialDTOResponse> findAllEditorialSort(Sort sort) throws Exception {
        List<EditorialDTOResponse> editorialDTOResponses = new ArrayList<EditorialDTOResponse>();
        for (Editorial editorial : iEditorialRepository.findAllEditorialSort(sort)) {
            editorialDTOResponses.add(
                    new EditorialDTOResponse(
                            editorial.getId_editorial(),
                            editorial.getNombre_editorial(),
                            editorial.getDescripcion_editorial(),
                            editorial.getDireccion_editorial(),
                            editorial.getTelefono_editorial(),
                            editorial.getCorreoElectronico_editorial()
                    )
            );
        }
        return editorialDTOResponses;
    }

    /**
     * Método que recupera una página de objetos EditorialDTOResponse utilizando paginación y ordenación.
     *
     * @param pageable La información de paginación y ordenación.
     * @return Una página de objetos EditorialDTOResponse.
     * @throws Exception Si ocurre un error al recuperar la página de las editoriales.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EditorialDTOResponse> findAllEditorialPage(Pageable pageable) throws Exception {
        List<EditorialDTOResponse> editorialDTOResponses = new ArrayList<EditorialDTOResponse>();
        for (Editorial editorial : iEditorialRepository.findAllEditorialPage(pageable)) {
            editorialDTOResponses.add(
                    new EditorialDTOResponse(
                            editorial.getId_editorial(),
                            editorial.getNombre_editorial(),
                            editorial.getDescripcion_editorial(),
                            editorial.getDireccion_editorial(),
                            editorial.getTelefono_editorial(),
                            editorial.getCorreoElectronico_editorial()
                    )
            );
        }
        return (Page<EditorialDTOResponse>) editorialDTOResponses;
    }

    /**
     * Método que busca un editorial por su ID y devuelve su información en un objeto CategoriaDTOResponse.
     *
     * @param id El ID de la editorial a buscar.
     * @return Un objeto EditorialDTOResponse con la información de la editorial.
     * @throws Exception Si la editorial no se encuentra o si ocurre un error durante la búsqueda.
     */
    @Override
    @Transactional(readOnly = true)
    public EditorialDTOResponse findByIdEditorial(long id) throws Exception {
        Editorial editorial = iEditorialRepository.findByIdEditorial(id);
        if (editorial == null) {
            throw new Exception("Editorial not found!");
        }
        return new EditorialDTOResponse(
                editorial.getId_editorial(),
                editorial.getNombre_editorial(),
                editorial.getDescripcion_editorial(),
                editorial.getDireccion_editorial(),
                editorial.getTelefono_editorial(),
                editorial.getCorreoElectronico_editorial()
        );
    }

    /**
     * Método para eliminar una editorial según su ID.
     *
     * @param id El ID del editorial a eliminar.
     * @return true si se elimina la editorial con éxito.
     * @throws Exception Si ocurre un error durante el proceso de eliminación de la editorial.
     */
    @Override
    @Transactional(readOnly = false)
    public boolean deleteEditorialById(Long id) throws Exception {
        try {
            if (iEditorialRepository.existsById(id)) {
                log.info("Eliminar Editorial con ID: " + id);
                iEditorialRepository.deleteById(id);
                return true;
            }else {
                log.error("¡No existe el ID de la Editorial!");
                throw new Exception("¡No existe el ID de la Editorial!");

            }
        } catch (Exception e) {
            log.error("Falló la Eliminación de la Editorial =>", e.getCause().toString());
            throw new Exception("¡No existe el ID de la Editorial!");
        }
    }
}
