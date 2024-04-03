package com.complejo.educacional.luis.durand.durand.controllers;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTOResponse;
import com.complejo.educacional.luis.durand.durand.repositories.IAutorRepository;
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
import org.springframework.web.bind.annotation.*;

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
     * @param generoDTORequest
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping(value = "genero/create")
    public ResponseEntity<Map<String, Object>> addNewGenero(@Valid @RequestBody GeneroDTORequest generoDTORequest, BindingResult bindingResult) throws Exception {
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
     *
     * @param page
     * @param size
     * @return
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


    @GetMapping(value = "genero/{id}")
    public ResponseEntity<GeneroDTOResponse> findByIdGenero(@PathVariable long id){
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


}
