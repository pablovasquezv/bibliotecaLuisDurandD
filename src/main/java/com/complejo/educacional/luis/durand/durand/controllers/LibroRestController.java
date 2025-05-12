package com.complejo.educacional.luis.durand.durand.controllers;

import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponse;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.ILibroServices;
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
 * @version 1.0
 * @autor Pablo
 * @creates 10-05-2025 1:41
 * @proyect bibliotecaLuisDurandD
 */
@Slf4j
@RestController
@RequestMapping(value = "/biblioteca/v1/")
public class LibroRestController {
    @Autowired
    private ILibroServices iLibroServices;
    @PostMapping(value = "libro/create")
    private ResponseEntity<Map<String,Object>> addNewLibro(@Valid @RequestBody LibroDTORequest libroDTORequest,
                                                          BindingResult bindingResult)throws Exception{
        Map<String, Object> responseAsMap = new HashMap<>();
        List<String> errores;

        if (bindingResult.hasErrors()) {
            errores = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            responseAsMap.put("errores", errores);
            return new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
        }
        try {
            LibroDTORequest libroFromDB= iLibroServices.saveLibro(libroDTORequest);
            if (libroFromDB != null) {
                responseAsMap.put("Libro", libroFromDB);
                responseAsMap.put("Mensaje:", "El Libro ¡Se creó exitosamente!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje: ", "¡No se creó el Libro!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (DataAccessException e){
            responseAsMap.put("Mensaje: ", "¡No se creó el Libro!" + e.getMostSpecificCause().toString());
            return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "libro/get/all")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<List<LibroDTOResponse>> getAllAutor(@RequestParam(required = false)Integer page,
                                                               @RequestParam(required = false)Integer size){
        Sort sortByName = Sort.by("titulo_libro");
        List<LibroDTOResponse> libros;
        Pageable pageable= null;
        try {
            if (page != null && size != null) {
                pageable = PageRequest.of(page, size, sortByName);
                libros = iLibroServices.findAllLibroPage(pageable).getContent();
            } else {
                libros = iLibroServices.findAllLibroSort(sortByName);
            }

            HttpStatus responseStatus = libros.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return new ResponseEntity<>(libros, responseStatus);
        }catch (Exception e){
            log.error("Ocurrió un error al listar todos los Libros! " + e.getCause().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
