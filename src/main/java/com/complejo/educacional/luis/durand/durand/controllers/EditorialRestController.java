package com.complejo.educacional.luis.durand.durand.controllers;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.dto.editorial.EditorialDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.editorial.EditorialDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.editorial.EditorialDTOResponse;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IEditorialServices;

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
    private IEditorialServices iEditorialServices;

    /**
     * Método que crea una nueva editorial basado en los datos proporcionados..
     *
     * @param editorialDTORequest El objeto DTO que contiene los detalles de la editorial que se creará.
     * @param bindingResult       El resultado del proceso de validación para el objeto DTO.
     * @return Una ResponseEntity que contiene el resultado de la creación de la editorial y el estado HTTP correspondiente.
     * @throws Exception Excepción si ocurre un error durante el proceso de creación de la categoría.
     */

    @PostMapping(value = "editorial/create")
    private ResponseEntity<Map<String, Object>> addNewEditorial(@Valid @RequestBody EditorialDTORequest editorialDTORequest,
                                                                BindingResult bindingResult)
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
            EditorialDTORequest editorialFromDb = iEditorialServices.saveEditorial(editorialDTORequest);
            if (editorialFromDb != null) {
                responseAsMap.put("Editorial", editorialDTORequest);
                responseAsMap.put("¡Mensaje", "La Editorial se creo correctamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje", "No se creó la Editorial");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException dataAccessException) {
            responseAsMap.put("Mensaje", "No sé creó la Editorial" + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * Actualiza una categoría según su ID.
     *
     * @param id                     El ID de la editorial a actualizar.
     * @param editorialDTORequestUpdate La información actualizada de la editorial.
     * @param bindingResult          El resultado del proceso de validación.
     * @return ResponseEntity con el resultado de la actualización y mensajes descriptivos.
     */
    @PutMapping(value = "editorial/update/{id}")
    private ResponseEntity<Map<String, Object>> updateEditorial(@PathVariable long id, @Valid @RequestBody
                                                                EditorialDTORequestUpdate editorialDTORequestUpdate,
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
            EditorialDTOResponse editorialFromDB = iEditorialServices.updateEditorial(id, editorialDTORequestUpdate);
            if (editorialFromDB != null) {
                responseAsMap.put("Editorial", editorialDTORequestUpdate);
                responseAsMap.put("Mensaje", "¡La Editorial con ID: " + editorialDTORequestUpdate.getId_editorial() + "Sé actualizo correctamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje", "¡La Editorial No se actualizo!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException dataAccessException) {
            responseAsMap.put("Mensaje: ", "¡No se actualizó la Editorial!" + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * Método que recupera una lista de editorial con paginación opcional.
     *
     * @param page El número de página para la paginación (opcional).
     * @param size El tamaño de página para la paginación (opcional).
     * @return Una ResponseEntity que contiene la lista de editorial y el estado HTTP correspondiente.
     */
    @GetMapping(value = "editorial/get/all")
    private ResponseEntity<List<EditorialDTOResponse>> findAllEditoriales(@RequestParam(required = false) Integer page,
                                                                          @RequestParam(required = false) Integer size) {
        Sort sortByName = Sort.by("nombre_editorial");
        ResponseEntity<List<EditorialDTOResponse>> responseEntity = null;
        List<EditorialDTOResponse> editorials = null;
        Pageable pageable = null;
        try {
            if (page != null & size != null) {
                pageable = PageRequest.of(page, size, sortByName);
                editorials = iEditorialServices.findAllEditorialPage(pageable).getContent();
            } else {
                editorials = iEditorialServices.findAllEditorialSort(sortByName);
            }

            // Validación sí tiene Editorial la lista
            responseEntity = (editorials.size() > 0) ?
                    new ResponseEntity<List<EditorialDTOResponse>>(editorials, HttpStatus.OK)
                    :
                    new ResponseEntity<List<EditorialDTOResponse>>(editorials, HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            log.error("Ocurrio un error al Obtener todas las Editoriales =>", e.getCause().toString());
            responseEntity = new ResponseEntity<List<EditorialDTOResponse>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * Método que recupera una Editorial por su ID.
     *
     * @param id El ID de la editorial a recuperar.
     * @return Una ResponseEntity que contiene la información de la editorial y el estado HTTP correspondiente.
     */

    @GetMapping(value = "editorial/{id}")
    private ResponseEntity<EditorialDTOResponse> findByIdEditorial(@PathVariable long id) {
        EditorialDTOResponse editorialDTOResponse = null;
        ResponseEntity<EditorialDTOResponse> responseEntity = null;
        try {
            editorialDTOResponse = iEditorialServices.findByIdEditorial(id);
            responseEntity = (editorialDTOResponse != null) ?
                    new ResponseEntity<EditorialDTOResponse>(editorialDTOResponse, HttpStatus.OK)
                    :
                    new ResponseEntity<EditorialDTOResponse>(editorialDTOResponse, HttpStatus.NO_CONTENT);
        } catch (DataAccessException dataAccessException) {
            log.error("Ocurrio un error: " + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<EditorialDTOResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return responseEntity;
    }

    /**
     * Método que elimina una editorial según su ID.
     *
     * @param id El ID de la editorial a eliminar.
     * @return ResponseEntity con un valor booleano que indica el resultado de la eliminación.
     */
    @DeleteMapping(value = "editorial/delete/{id}")
    private ResponseEntity<EditorialDTOResponse> deleteById(@PathVariable long id) throws Exception {
        EditorialDTOResponse editorial = null;
        ResponseEntity<EditorialDTOResponse> responseEntity = null;
        try {
            editorial = iEditorialServices.findByIdEditorial(id);
            if (editorial != null) {
                iEditorialServices.deleteEditorialById(id);
                responseEntity = new ResponseEntity<EditorialDTOResponse>(HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<EditorialDTOResponse>(HttpStatus.NO_CONTENT);
            }
        } catch (DataAccessException dataAccessException) {
            log.error("Ocurrio un error: " + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<EditorialDTOResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


}












