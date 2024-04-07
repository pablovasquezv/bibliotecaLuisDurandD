package com.complejo.educacional.luis.durand.durand.controllers;

import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTORequest;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.ILibroServices;
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
 * @create 07-04-2024 1:27
 * @project bibliotecaLuisDurandD
 */
@Slf4j
@RestController
@RequestMapping(value = "/biblioteca/v1/")
public class LibroRestController {
    @Autowired
    private ILibroServices iLibroServices;

    @PostMapping(value = "libro/create")
    public ResponseEntity<Map<String, Object>> addNewLibro(@Valid @RequestBody LibroDTORequest libroDTORequest,
                                                           BindingResult bindingResult) throws Exception {
        Map<String, Object> responseASMap = new HashMap<>();
        List<String> errores = null;
        if (bindingResult.hasErrors()) {
            errores = bindingResult.getAllErrors().stream().
                    map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            responseASMap.put("Errores:", errores);
            return new ResponseEntity<>(responseASMap, HttpStatus.BAD_REQUEST);
        }
        try {
            LibroDTORequest libroFromDB = iLibroServices.saveLibro(libroDTORequest);
            if (libroFromDB != null){
                responseASMap.put("Libro",libroDTORequest);
                responseASMap.put("Mensaje: ","¡El Libro se creó exitosamente!");
                return new ResponseEntity<>(responseASMap,HttpStatus.OK);
            }else {
                responseASMap.put("Mensaje: ","¡No sé creó el Libro exitosamente!");
                return new ResponseEntity<>(responseASMap,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (DataAccessException e){
            responseASMap.put("Mensaje: ","¡No sé creó el Libro!"+e.getMostSpecificCause().getMessage().toString());
            return new ResponseEntity<>(responseASMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
