package com.complejo.educacional.luis.durand.durand.services;
//Import necesarios para la clase.

import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.models.Genero;
import com.complejo.educacional.luis.durand.durand.repositories.IGeneroRepository;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IGeneroServices;
import com.complejo.educacional.luis.durand.durand.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Pablo
 * @version 1.0
 * @create 30-03-2024 19:27
 * @project bibliotecaLuisDurandD
 */
@Slf4j
@Service
public class GeneroServicesImpl implements IGeneroServices {
    @Autowired
    private IGeneroRepository iGeneroRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Utils utils;

    /**
     * Método que guarda un nuevo género con la información proporcionada en generoDTORequest.
     *
     * @param generoDTORequest La información del nuevo género a guardar.
     * @return Un objeto GeneroDTORequest con la información del género guardado.
     * @throws Exception Si ocurre un error durante el proceso de guardado del género.
     */
    @Override
    @Transactional(readOnly = false)
    public GeneroDTORequest saveGenero(GeneroDTORequest generoDTORequest) throws Exception {
        try {
            Genero creatGenero = new Genero(
                    null,
                    generoDTORequest.getNombre_genero().toUpperCase(),
                    generoDTORequest.getDescripcion_genero()
            );
            log.info("---Incio de la Creación del Género---" + objectMapper.writeValueAsString(creatGenero));
            creatGenero = iGeneroRepository.save(creatGenero);
            log.info("Json de Salida ==>" + objectMapper.writeValueAsString(creatGenero));
            log.info("---Fin de la Creación del Género---");
            return new GeneroDTORequest(
                    creatGenero.getNombre_genero(),
                    creatGenero.getDescripcion_genero()
            );
        } catch (Exception e) {
            log.error("Ocurrió un error al guardar el Género: " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al guardar el Género!");
        }
    }

    /**
     * Método que actualiza un género según su ID con la información proporcionada en generoDTOResponseUpdate.
     *
     * @param id El ID del género a actualizar.
     * @param generoDTOResponseUpdate La información actualizada del género.
     * @return Un objeto GeneroDTOResponseUpdate con la información actualizada del género.
     * @throws Exception Si ocurre un error durante el proceso de actualización del género.
     */
    @Transactional(readOnly = false)
    @Override
    public GeneroDTOResponse updateGenero(Long id, GeneroDTOResponseUpdate generoDTOResponseUpdate) throws Exception {
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

            genero.setNombre_genero(generoDTOResponseUpdate.getNombre_genero().toUpperCase());
            genero.setDescripcion_genero(generoDTOResponseUpdate.getDescripcion_genero());
            updateGenero = iGeneroRepository.save(genero);

            log.info("Json de Salida ==>" + objectMapper.writeValueAsString(updateGenero));

            return new GeneroDTOResponse(
                    updateGenero.getId_genero(),
                    updateGenero.getNombre_genero(),
                    updateGenero.getDescripcion_genero()
            );
        } catch (Exception e) {
            log.error("Ocurrió un error al guardar el Género: " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al guardar el Género!");
        }
    }

    /**
     * Método que recupera una lista de objetos GeneroDTOResponse ordenados según el criterio especificado.
     *
     * @param sort El criterio de ordenación.
     * @return Una lista de objetos GeneroDTOResponse ordenados según el criterio especificado.
     * @throws Exception Si ocurre un error al recuperar la lista de géneros ordenados.
     */
    @Override
    @Transactional(readOnly = true)
    public List<GeneroDTOResponse> findAllGeneroSort(Sort sort) throws Exception {
        try {
            /**
             * Utilicé el método stream() y map() para convertir la lista de allGeneroSort en una lista de
             * GeneroDTOResponse de forma más concisa.
             */
            List<Genero> allGeneroSort = iGeneroRepository.findAllGeneroSort(sort);
            return allGeneroSort.stream().map(
                    genero -> new GeneroDTOResponse(
                            genero.getId_genero(),
                            genero.getNombre_genero(),
                            genero.getDescripcion_genero()
                    )
            ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Ocurrió un error al listar todos los Géneros " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al listar todos los Géneros!");
        }
    }

    /**
     * Método que recupera una página de objetos GeneroDTOResponse utilizando paginación y ordenación.
     *
     * @param pageable La información de paginación y ordenación.
     * @return Una página de objetos GeneroDTOResponse.
     * @throws Exception Si ocurre un error al recuperar la página de géneros.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GeneroDTOResponse> findAllGeneroPage(Pageable pageable) throws Exception {
        try {
            Page<Genero> generos = iGeneroRepository.findAllGeneroPage(pageable);
            /**
             * Utilicé el método stream() y map() para convertir la lista de Autor en una lista
             * de AutorDTOResponse de forma más concisa.
             */
            List<GeneroDTOResponse> generoDTOResponseList = generos.stream().
                    map(genero -> new GeneroDTOResponse(
                                    genero.getId_genero(),
                                    genero.getNombre_genero(),
                                    genero.getDescripcion_genero()
                            )

                    ).collect(Collectors.toList());
            /**
             * Utilicé el método collect() junto con Collectors.toList() para recopilar los elementos mapeados
             * en una lista.
             */
            return new PageImpl<>(generoDTOResponseList, pageable, generos.getTotalElements());
            /**
             * Creé un nuevo objeto PageImpl para devolver una página de resultados con la lista de GeneroDTOResponse,
             * el objeto Pageable original y el número total de elementos.
             */
        } catch (Exception e) {
            log.error("Ocurrió un error al listar todos los Géneros " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al listar todos los Géneros!");
        }
    }

    /**
     * Método que qusca un género por su ID y devuelve su información en un objeto GeneroDTOResponse.
     *
     * @param id El ID del género a buscar.
     * @return Un objeto GeneroDTOResponse con la información del género.
     * @throws Exception Si el género no se encuentra o si ocurre un error durante la búsqueda.
     */
    @Override
    @Transactional(readOnly = true)
    public GeneroDTOResponse findByIdGenero(long id) throws Exception {
        try {
            Genero genero = iGeneroRepository.findByIdGenero(id);
            if (genero == null) {
                throw new Exception("¡Género no encontrado!");
            }
            return new GeneroDTOResponse(
                    genero.getId_genero(),
                    genero.getNombre_genero(),
                    genero.getDescripcion_genero()
            );
        } catch (Exception e) {
            log.error("Ocurrió un error al buscar el Género con ID " + id + ": " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al buscar el Género!");
        }
    }

    /**
     * Método para eliminar un género según su ID.
     *
     * @param id El ID del género a eliminar.
     * @return true si se elimina el género con éxito.
     * @throws Exception Si ocurre un error durante el proceso de eliminación del género.
     */
    @Override
    @Transactional(readOnly = false)
    public boolean deleteGeneroById(Long id) throws Exception {
        try {
            if (iGeneroRepository.existsById(id)) {
                log.info("Elimininado Género con ID: " + id);
                iGeneroRepository.deleteById(id);
                return true;
            } else {
                log.error("No existe el ID del Género!");
                throw new Exception("¡No existe el ID del Género!");
            }
        } catch (Exception e) {
            log.error("Ocurrió un error al eliminar el Género con ID " + id + ": " + e.getCause().toString());
            throw new Exception("Ocurrió un error al eliminar el Género!");
        }
    }

}
