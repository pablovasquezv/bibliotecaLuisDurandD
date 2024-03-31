package com.complejo.educacional.luis.durand.durand.controllers;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.dto.genero.GeneroDTORequest;
import com.complejo.educacional.luis.durand.durand.repositories.IAutorRepository;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IGeneroServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     *
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
            generoFromDB= iGeneroServices.saveGenero(generoDTORequest);
            if (generoFromDB!=null){
                responseAsMap.put("Género",generoDTORequest);
                responseAsMap.put("Mensaje","¡El Género se creó exitosamente!");
                return new ResponseEntity<>(responseAsMap,HttpStatus.OK);
            }else {
                responseAsMap.put("Mensaje", "¡No se pudo crea el Género!");
                return new  ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (DataAccessException e) {
            responseAsMap.put("Mensaje", "¡No sé creo el Género!" + e.getMostSpecificCause().toString());
            return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
