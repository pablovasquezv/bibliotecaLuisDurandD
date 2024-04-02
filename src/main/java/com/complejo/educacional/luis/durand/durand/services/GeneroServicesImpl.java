package com.complejo.educacional.luis.durand.durand.services;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.models.Genero;
import com.complejo.educacional.luis.durand.durand.repositories.IGeneroRepository;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IGeneroServices;
import com.complejo.educacional.luis.durand.durand.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.text.MessageFormat;

/**
 * @author Pablo
 * @version 1.0
 * @create 30-03-2024 19:27
 * @project bibliotecaLuisDurandD
 */
@Slf4j
@Service
public class GeneroServicesImpl implements IGeneroServices {
    @Value ("${constantes.mensajeCreatGenero}")
    private String mensajeCreatGenero;

    @Value ("${constantes.mensajeNoCreatGenero}")
    private String mensajeNoCreatGenero;

    private IGeneroRepository iGeneroRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Utils utils;
    @Autowired
    public GeneroServicesImpl(IGeneroRepository iGeneroRepository) {
        this.iGeneroRepository = iGeneroRepository;
    }
    @Override
    @Transactional(readOnly = false)
    public GeneroDTORequest saveGenero(GeneroDTORequest generoDTORequest) throws Exception {
        // Verificar si el nombre de género ya existe en la base de datos
        Genero nombreExiste2 = new Genero();
        boolean nombreExiste = nombreExiste2.nombreGeneroYaExisteEnBaseDeDatos(generoDTORequest.getNombre_genero());

        if (nombreExiste) {
            // Manejar la lógica correspondiente si el nombre ya existe
            System.out.println("¡El nombre del género ya existe en la base de datos!");
        };
        try {
                Genero creatGenero = new Genero(
                        null,
                        generoDTORequest.getNombre_genero().toUpperCase(),
                        generoDTORequest.getDescripcion_genero(),
                        generoDTORequest.getCreatedAt(),
                        generoDTORequest.getUpdatedAt()
                );
                creatGenero.nombreGeneroYaExisteEnBaseDeDatos(generoDTORequest.getNombre_genero());
                log.info("---Incio de la Creación del Género---" + objectMapper.writeValueAsString(creatGenero));
                creatGenero = iGeneroRepository.save(creatGenero);
                log.info("Json de Salida ==>" + objectMapper.writeValueAsString(creatGenero));
                log.info("---Incio de la Creación del Género---");
                return new GeneroDTORequest(
                        creatGenero.getNombre_genero(),
                        creatGenero.getDescripcion_genero(),
                        creatGenero.getCreatedAt(),
                        creatGenero.getUpdatedAt()
                );

        } catch (Exception e) {
            log.error("Ocurrió un error al guardar el Género: " +e.getCause(),MessageFormat.format(mensajeCreatGenero,mensajeNoCreatGenero), e.getCause().getMessage());

            throw new Exception("¡Ocurrió un error al guardar el Género!"+e.getCause().getMessage());
        }
    }

    /***
     *
     * @param id
     * @param generoDTOResponseUpdate
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    @Override
    public GeneroDTOResponseUpdate updatGenero(Long id, GeneroDTOResponseUpdate generoDTOResponseUpdate) throws Exception {
        try {
            Optional<Genero> generoOptional;
            Genero genero;
            Genero updateGenero;
            generoOptional = iGeneroRepository.findById(id);
            log.info("---Inicio de la Actualización del Género---" + objectMapper.writeValueAsString(generoOptional));

            genero = generoOptional.orElseThrow(() -> {
                log.error("Ocurrió un error en la actualización del Género: ");
                return new Exception("¡Ocurrió un error en la actualización del Género!");
            });

            genero.setNombre_genero(generoDTOResponseUpdate.getNombre_genero());
            genero.setDescripcion_genero(generoDTOResponseUpdate.getDescripcion_genero());
            genero.setCreatedAt(generoDTOResponseUpdate.getCreatedAt());
            genero.setUpdatedAt(generoDTOResponseUpdate.getUpdatedAt());
            updateGenero = iGeneroRepository.save(genero);

            log.info("Json de Salida ==>" + objectMapper.writeValueAsString(updateGenero));

            return new GeneroDTOResponseUpdate(
                    updateGenero.getId_genero(),
                    updateGenero.getNombre_genero(),
                    updateGenero.getDescripcion_genero(),
                    updateGenero.getCreatedAt(),
                    updateGenero.getUpdatedAt()
            );
        } catch (Exception e) {
            log.error("Ocurrió un error al guardar el Género: " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al guardar el Género!");
        }
    }


    @Override
    public List<GeneroDTOResponse> findAllGeneroSort(Sort sort) throws Exception {
        return null;
    }

    @Override
    public Page<GeneroDTOResponse> findAllGeneroPage(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public GeneroDTOResponse findByIdGenero(long id) throws Exception {
        return null;
    }

    @Override
    public Object deleteGeneroById(Long id) throws Exception {
        return null;
    }
}
