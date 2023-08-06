package com.complejo.educacional.luis.durand.durand.controllers;

import com.complejo.educacional.luis.durand.durand.implementsServices.ICategoriaImplements;
import com.complejo.educacional.luis.durand.durand.models.Categoria;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pablo
 */
@Slf4j
@RestController
@RequestMapping(value = "/biblioteca/v1/")
public class CategoriaRestController {
    @Autowired
    private ICategoriaImplements iCategoriaImplements;

    /**
     *
     * @param categoria
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping(value = "categoria/create")
    private ResponseEntity<Map<String, Object>> addNewCategoria(@Valid @RequestBody Categoria categoria, BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        List<String> errores = null;
        if (bindingResult.hasErrors()) {
            errores = new ArrayList<String>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("Errores", errores);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_GATEWAY);
            return responseEntity;
        }
        try {
            Categoria categoriaFromDB = iCategoriaImplements.saveCategoria(categoria);
            if (categoriaFromDB != null) {
                responseAsMap.put("Categoría", categoria);
                responseAsMap.put("¡Mensaje", "La Categoría con ID: " + categoria.getId_categoria() + " se creó correctamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
                return responseEntity;
            } else {
                responseAsMap.put("Mensaje: ", "¡No sé creó la Categoria!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        } catch (DataAccessException dataAccessException) {
            responseAsMap.put("Mensaje:", "No se creo la Categoria" + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


}
