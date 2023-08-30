/**
 *
 */
package com.complejo.educacional.luis.durand.durand.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.complejo.educacional.luis.durand.durand.dto.PaisDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.PaisDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.PaisDTOResponse;
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
     * @param paisDTORequest
     * @return paisDTORequest
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public PaisDTORequest createPais(PaisDTORequest paisDTORequest) throws Exception {
        // TODO Auto-generated method stub
        try {
            Pais createPais = new Pais(
                    null,
                    paisDTORequest.getNombre_pais(),
                    paisDTORequest.getAutores(),
                    paisDTORequest.getCreatedAt(),
                    paisDTORequest.getUpdatedAt()
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
     * @param id
     * @param paisDTORequestUpdate
     * @return paisDTORequestUpdate
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public PaisDTOResponse updatePais(Long id, PaisDTORequestUpdate paisDTORequestUpdate) throws Exception {
        // TODO Auto-generated method stub
        Optional<Pais> paisOptional = null;
        PaisDTOResponse paisDTOResponse = null;
        Pais paisUpdate = new Pais(
                paisDTORequestUpdate.getId_pais(),
                paisDTORequestUpdate.getNombre_pais(),
                paisDTORequestUpdate.getAutores(),
                paisDTORequestUpdate.getCreatedAt(),
                paisDTORequestUpdate.getUpdatedAt()
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
                    paisUpdate.getNombre_pais(),
                    paisUpdate.getCreatedAt(),
                    paisUpdate.getUpdatedAt()
            );

            return paisDTOResponse;
        } catch (Exception e) {
            // TODO: handle exception
            log.error("¡Error al Actualizar el país!", e.getCause().toString());
        }
        return paisDTOResponse;
    }

    /**
     * @param sort
     * @return
     * @throws Exception
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
                            pais.getNombre_pais(),
                            pais.getCreatedAt(),
                            pais.getUpdatedAt()
                    )
            );
        }
        return paisDTOResponses;
    }

    /**
     * @param pageable
     * @return
     * @throws Exception
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
                            pais.getNombre_pais(),
                            pais.getCreatedAt(),
                            pais.getUpdatedAt()
                    )
            );
        }
        return (Page<PaisDTOResponse>) paisDTOResponses;
    }

    /**
     * @param id
     * @return PaisDTOResponse
     * @throws Exception
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
                pais.getNombre_pais(),
                pais.getCreatedAt(),
                pais.getUpdatedAt()
        );
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public void deletePaisById(long id) throws Exception {
        // TODO Auto-generated method stub
        try {
            if (iPaisRepository.existsById(id)) {
                iPaisRepository.deleteById(id);
            } else {
                log.error("¡No exite el Id del País!");
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception(e.getCause());
        }
    }

}
