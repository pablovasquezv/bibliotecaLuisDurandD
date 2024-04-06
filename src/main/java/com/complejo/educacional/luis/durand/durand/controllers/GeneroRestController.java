package com.complejo.educacional.luis.durand.durand.controllers;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IGeneroServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Pablo
 * @version 1.0
 * @create 30-03-2024 19:57
 * @project bibliotecaLuisDurandD
 */
@Slf4j
@RestController
@RequestMapping(value = "/biblioteca/v1/")
public class GeneroRestController {

    @Autowired
    private IGeneroServices iGeneroServices;


    /**
     * Método que crea un nuevo género basado en los datos proporcionados..
     *
     * @param generoDTORequest generoDTORequest El objeto DTO que contiene los detalles del género que se creará.
     * @param bindingResult    El resultado del proceso de validación para el objeto DTO.
     * @return Una ResponseEntity que contiene el resultado de la creación del género y el estado HTTP correspondiente.
     * @throws Exception Excepción si ocurre un error durante el proceso de creación del género.
     */
    @PostMapping(value = "genero/create")
    public ResponseEntity<Map<String, Object>> addNewGenero(@Valid @RequestBody GeneroDTORequest generoDTORequest,
                                                            BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        List<String> errores;
        if (bindingResult.hasErrors()) {
            errores = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage).
                    collect(Collectors.toList());
            responseAsMap.put("errores:", errores);
            return new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
        }
        try {
            GeneroDTORequest generoFromDB;
            generoFromDB = iGeneroServices.saveGenero(generoDTORequest);
            if (generoFromDB != null) {
                responseAsMap.put("Género", generoDTORequest);
                responseAsMap.put("Mensaje", "¡El Género se creó exitosamente!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje", "¡No se pudo crea el Género!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            responseAsMap.put("Mensaje", "¡No sé creo el Género!" + e.getMostSpecificCause().toString());
            return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un género según su ID.
     *
     * @param id                      El ID del género a actualizar.
     * @param generoDTOResponseUpdate La información actualizada del género.
     * @param bindingResult           El resultado del proceso de validación.
     * @return ResponseEntity con el resultado de la actualización y mensajes descriptivos.
     */
    @PutMapping(value = "genero/update/{id}")
    public ResponseEntity<Map<String, Object>> updateGenero(@PathVariable long id, @Valid @RequestBody GeneroDTOResponseUpdate generoDTOResponseUpdate,
                                                            BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            responseAsMap.put("errores", errores);
            return new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
        }

        try {
            GeneroDTOResponse updatedGenero = iGeneroServices.updateGenero(id, generoDTOResponseUpdate);
            if (updatedGenero != null && updatedGenero.getId_genero() != null) {
                responseAsMap.put("Genero", updatedGenero);
                responseAsMap.put("Mensaje", "¡Se actualizó correctamente el género con ID: " + updatedGenero.getId_genero() + "!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje", "¡No se pudo actualizar el género!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException dataAccessException) {
            responseAsMap.put("Mensaje", "¡No se pudo actualizar el género! " + dataAccessException.getMostSpecificCause().toString());
            return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Método que recupera una lista de géneros con paginación opcional.
     *
     * @param page El número de página para la paginación (opcional).
     * @param size El tamaño de página para la paginación (opcional).
     * @return Una ResponseEntity que contiene la lista de géneros y el estado HTTP correspondiente.
     */
    @GetMapping(value = "genero/get/all")
    public ResponseEntity<List<GeneroDTOResponse>> findAllGenero(@RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer size) {
        Sort sortByname = Sort.by("nombre_genero");
        List<GeneroDTOResponse> generoDTOResponseList = null;
        Pageable pageable = null;
        HttpStatus responseStatus;
        try {
            pageable = (page != null && size != null) ?
                    PageRequest.of(page, size, sortByname)
                    :
                    null;
            generoDTOResponseList = (page != null) ?
                    iGeneroServices.findAllGeneroPage(pageable).getContent() :
                    iGeneroServices.findAllGeneroSort(sortByname);
            responseStatus = generoDTOResponseList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return new ResponseEntity<>(generoDTOResponseList, responseStatus);

        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage().toString() : "Error desconocido";
            log.error("Un error a occurrido en la listar todos los Géneros por ID " + generoDTOResponseList + ": " + errorMessage);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que recupera un Género por su ID.
     *
     * @param id El ID del género a recuperar.
     * @return Una ResponseEntity que contiene la información del género y el estado HTTP correspondiente.
     */

    @GetMapping(value = "genero/{id}")
    public ResponseEntity<GeneroDTOResponse> findByIdGenero(@PathVariable long id) {
        GeneroDTOResponse generoDTOResponse;
        HttpStatus responseStatus;
        try {
            generoDTOResponse = iGeneroServices.findByIdGenero(id);
            responseStatus = (generoDTOResponse != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
            return new ResponseEntity<>(generoDTOResponse, responseStatus);
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage().toString() : "Error desconocido";
            log.error("Un error a occurrido en la listar todos los Géneros por ID " + id + ": " + errorMessage);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Méetodo que elimina un género según su ID.
     *
     * @param id El ID del género a eliminar.
     * @return ResponseEntity con un valor booleano que indica el resultado de la eliminación.
     */
    @DeleteMapping(value = "genero/delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        try {
            GeneroDTOResponse generoDTOResponse = iGeneroServices.findByIdGenero(id);
            return (generoDTOResponse != null) ?
                    (iGeneroServices.deleteGeneroById(id) ?
                            new ResponseEntity<>(HttpStatus.OK) :
                            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)) :
                    new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage().toString() : "Error desconocido";
            log.error("Un error a occurrido en la eliminar un Géneros por ID " + id + ": " + errorMessage);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
