package com.complejo.educacional.luis.durand.durand.services;
//Import necesarios para la clase.

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.complejo.educacional.luis.durand.durand.dto.pais.PaisDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.pais.PaisDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.pais.PaisDTOResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complejo.educacional.luis.durand.durand.services.implementsServices.IPaisServices;
import com.complejo.educacional.luis.durand.durand.models.Pais;
import com.complejo.educacional.luis.durand.durand.repositories.IPaisRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Pablo
 */
@Service
@Slf4j
public class PaisServicesImpl implements IPaisServices {

    @Autowired
    private IPaisRepository iPaisRepository;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Método que guarda un nuevo país con la información proporcionada en paisDTORequest.
     *
     * @param paisDTORequest La información del nuevo país a guardar.
     * @return Un objeto paisDTORequest con la información del país guardado.
     * @throws Exception Si ocurre un error durante el proceso de guardado del país.
     */
    @Override
    @Transactional(readOnly = false)
    public PaisDTORequest createPais(PaisDTORequest paisDTORequest) throws Exception {
        // TODO Auto-generated method stub
        try {
            Pais createPais = new Pais(
                    null,
                    paisDTORequest.getNombre_pais()
            );
            log.info("¡Creación del País");
            iPaisRepository.save(createPais);
            log.info("¡País Creado! ", objectMapper.writeValueAsString(createPais));
            return paisDTORequest;
        } catch (Exception e) {
            // TODO: handle exception
            log.error("¡Error al Crear el país!", e.getCause().toString());
        }
        return paisDTORequest;
    }

    /**
     * Método que actualiza un país según su ID con la información proporcionada en paisDTORequestUpdate.
     *
     * @param id El ID del país a actualizar.
     * @param paisDTORequestUpdate La información actualizada del país.
     * @return Un objeto PaisDTOResponse con la información actualizada del país.
     * @throws Exception Si ocurre un error durante el proceso de actualización del país.
     */
    @Override
    @Transactional(readOnly = false)
    public PaisDTOResponse updatePais(Long id, PaisDTORequestUpdate paisDTORequestUpdate) throws Exception {
        // TODO Auto-generated method stub
        Optional<Pais> paisOptional = null;
        PaisDTOResponse paisDTOResponse = null;
        Pais paisUpdate = new Pais(
                paisDTORequestUpdate.getId_pais(),
                paisDTORequestUpdate.getNombre_pais()
        );
        try {
            paisOptional = iPaisRepository.findById(id);
            if (paisOptional.isPresent()) {
                iPaisRepository.save(paisUpdate);
                log.info("¡País actulizado!", objectMapper.writeValueAsString(paisUpdate));
            } else {
                log.error("Falló la actualización del País =>");
            }
            paisDTOResponse = new PaisDTOResponse(
                    paisUpdate.getId_pais(),
                    paisUpdate.getNombre_pais()
            );

            return paisDTOResponse;
        } catch (Exception e) {
            // TODO: handle exception
            log.error("¡Error al Actualizar el país!", e.getCause().toString());
        }
        return paisDTOResponse;
    }

    /**
     * Método que recupera una lista de objetos PaisDTOResponse ordenados según el criterio especificado.
     *
     * @param sort El criterio de ordenación.
     * @return Una lista de objetos PaisDTOResponse ordenados según el criterio especificado.
     * @throws Exception Si ocurre un error al recuperar la lista de país ordenados.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaisDTOResponse> findAllPaisSort(Sort sort) throws Exception {
        // TODO Auto-generated method stub
        List<PaisDTOResponse> paisDTOResponses = new ArrayList<PaisDTOResponse>();
        for (Pais pais : iPaisRepository.findAllPaisSort(sort)) {
            paisDTOResponses.add(
                    new PaisDTOResponse(
                            pais.getId_pais(),
                            pais.getNombre_pais()
                    )
            );
        }
        return paisDTOResponses;
    }

    /**
     * Método que recupera una página de objetos PaisDTOResponse utilizando paginación y ordenación.
     *
     * @param pageable La información de paginación y ordenación.
     * @return Una página de objetos PaisDTOResponse.
     * @throws Exception Si ocurre un error al recuperar la página de países.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaisDTOResponse> findAllPaisPageable(Pageable pageable) throws Exception {
        // TODO Auto-generated method stub
        List<PaisDTOResponse> paisDTOResponses = new ArrayList<PaisDTOResponse>();
        for (Pais pais : iPaisRepository.findAllPaisPage(pageable)) {
            paisDTOResponses.add(
                    new PaisDTOResponse(
                            pais.getId_pais(),
                            pais.getNombre_pais()
                    )
            );
        }
        return (Page<PaisDTOResponse>) paisDTOResponses;
    }

    /**
     * Método que qusca un país por su ID y devuelve su información en un objeto PaisDTOResponse.
     *
     * @param id El ID del país a buscar.
     * @return Un objeto PaisDTOResponse con la información del género.
     * @throws Exception Si el país no se encuentra o si ocurre un error durante la búsqueda.
     */
    @Override
    @Transactional(readOnly = true)
    public PaisDTOResponse findById(long id) throws Exception {
        // TODO Auto-generated method stub
        Pais pais = iPaisRepository.findById(id);
        if (pais == null) {
            throw new Exception("¡País not found");
        }

        return new PaisDTOResponse(
                pais.getId_pais(),
                pais.getNombre_pais()
        );
    }

    /**
     * Método para eliminar un país según su ID.
     *
     * @param id El ID del país a eliminar.
     * @return true si se elimina el país con éxito.
     * @throws Exception Si ocurre un error durante el proceso de eliminación del país.
     */
    @Override
    @Transactional(readOnly = false)
    public boolean deletePaisById(long id) throws Exception {
        // TODO Auto-generated method stub
        try {
            if (iPaisRepository.existsById(id)) {
                log.info("Elimininado País con ID: " + id);
                iPaisRepository.deleteById(id);
                return true;
            } else {
                log.error("¡No exite el Id del País!");
                throw new Exception("¡No existe el ID del País!");
            }
        } catch (Exception e) {
            log.error("Ocurrió un error al eliminar el País con ID " + id + ": " + e.getCause().toString());
            throw new Exception("Ocurrió un error al eliminar el País!");
        }
    }

}
