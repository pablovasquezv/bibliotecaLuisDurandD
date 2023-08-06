package com.complejo.educacional.luis.durand.durand.controllers;

import com.complejo.educacional.luis.durand.durand.implementsServices.IEditorialImplements;
import com.complejo.educacional.luis.durand.durand.models.Editorial;

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
public class EditorialRestController {
    @Autowired
    private IEditorialImplements iEditorialImplements;

    /**
     * @param editorial
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping(value = "editorial/create")
    private ResponseEntity<Map<String, Object>> addNewEditorial(@Valid @RequestBody Editorial editorial, BindingResult bindingResult)
            throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        List<String> errores = null;
        if (bindingResult.hasErrors()) {
            errores = new ArrayList<String>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("Errores", errores);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        try {
            Editorial editorialFromDb = iEditorialImplements.saveEditorial(editorial);
            if (editorialFromDb != null) {
                responseAsMap.put("Editorial", editorial);
                responseAsMap.put("¡Mensaje", "La Editorial con ID: " + editorial.getId_editorial() + " se creo correctamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
                return responseEntity;
            } else {
                responseAsMap.put("Mensaje", "No se creó la Editorial");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        } catch (DataAccessException dataAccessException) {
            responseAsMap.put("Mensaje", "No sé creó la Editorial" + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * @param id
     * @param editorial
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PutMapping(value = "editorial/update/{id}")
    private ResponseEntity<Map<String, Object>> updateEditorial(@PathVariable long id, @Valid @RequestBody Editorial editorial,
                                                                BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        List<String> errores = null;
        if (bindingResult.hasErrors()) {
            errores = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("errores", errores);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        try {
            Editorial editorialFromDB = iEditorialImplements.updateEditorial(id, editorial);
            if (editorialFromDB != null) {
                responseAsMap.put("Editorial", editorial);
                responseAsMap.put("Mensaje", "¡La Editorial con ID" + editorial.getId_editorial() + "Sé actualizo correctamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
                return responseEntity;
            } else {
                responseAsMap.put("Mensaje", "¡La Editorial No se actualizo!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        } catch (DataAccessException dataAccessException) {
            responseAsMap.put("Mensaje: ", "¡No se actualizó la Editorial!" + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "editorial/get/all")
    private ResponseEntity<List<Editorial>> findAllEditoriales(@RequestParam(required = false) Integer page,
                                                               @RequestParam(required = false) Integer size) {
        Sort sortByName = Sort.by("nombre_editorial");
        ResponseEntity<List<Editorial>> responseEntity = null;
        List<Editorial> editorialList = null;
        Pageable pageable = null;
        try {
            if (page != null & size != null) {
                pageable = PageRequest.of(page, size, sortByName);
                editorialList = iEditorialImplements.findAllEditorialPage(pageable).getContent();
            } else {
                editorialList = iEditorialImplements.findAllEditorialSort(sortByName);
            }

            // Validación sí tiene Editorial la lista
            responseEntity = (editorialList.size() > 0) ?
                    new ResponseEntity<List<Editorial>>(editorialList, HttpStatus.OK)
                    :
                    new ResponseEntity<List<Editorial>>(editorialList, HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            log.error("Ocurrio un error al Obtener todas las Editoriales =>", e.getCause().toString());
            responseEntity = new ResponseEntity<List<Editorial>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "editorial/delete/{id}")
    private ResponseEntity<Editorial> deleteById(@PathVariable long id) throws Exception {
        Editorial editorial = null;
        ResponseEntity<Editorial> responseEntity = null;
        try {
            editorial = iEditorialImplements.findByIdEditorial(id);
            if (editorial != null) {
                iEditorialImplements.deleteEditorialById(id);
                responseEntity = new ResponseEntity<Editorial>(HttpStatus.OK);
                return responseEntity;
            } else {
                responseEntity = new ResponseEntity<Editorial>(HttpStatus.NO_CONTENT);
                return responseEntity;
            }
        } catch (DataAccessException dataAccessException) {
            log.error("Ocurrio un error: " + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Editorial>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}












