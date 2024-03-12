/**
 *
 */
package com.complejo.educacional.luis.durand.durand.controllers;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.stream.Collectors;

import javax.validation.Valid;

import com.complejo.educacional.luis.durand.durand.dto.AutorDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.AutorDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.AutorDTOResponseUpdate;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import com.complejo.educacional.luis.durand.durand.services.implementsServices.IAutorServices;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Pablo
 */
@Slf4j
@RestController
@RequestMapping(value = "/biblioteca/v1/")
public class AutorRestController {
    @Autowired
    private IAutorServices iAutorServices;

    /**
     * @param autorDTORequest
     * @param bindingResult
     * @return responseEntity
     * @throws Exception
     */
    @PostMapping(value = "autor/create")
    private ResponseEntity<Map<String, Object>> addNewAutor(@Valid @RequestBody AutorDTORequest autorDTORequest,
                                                            BindingResult bindingResult) throws Exception {
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
            AutorDTORequest autorFromDB = iAutorServices.saveAutor(autorDTORequest);
            if (autorFromDB != null) {
                responseAsMap.put("Autor", autorDTORequest);
                responseAsMap.put("Mensaje:", "El Autor ¡Se creó exitosamente!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje: ", "¡No se creó el Autor!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            responseAsMap.put("Mensaje: ", "¡No se creó el Autor!" + e.getMostSpecificCause().toString());
            return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id
     * @param autorDTOResponseUpdate
     * @param bindingResult
     * @return responseEntity
     * @throws Exception
     */
    @PutMapping(value = "autor/update/{id}")
    private ResponseEntity<Map<String, Object>> updateAutor(@PathVariable long id, @Valid @RequestBody AutorDTOResponseUpdate autorDTOResponseUpdate, BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity;

        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            responseAsMap.put("Errores", errores);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }

        try {
            AutorDTOResponse autorFromDB = iAutorServices.updateAutor(id, autorDTOResponseUpdate);

            if (autorFromDB != null && autorFromDB.getId_autor() != null) {
                responseAsMap.put("Autor", autorDTOResponseUpdate);
                responseAsMap.put("Mensaje:", "¡Se actualizó correctamente el Autor con ID: " + autorDTOResponseUpdate.getId_autor() + "!");
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje", "¡No se pudo actualizar el Autor!");
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException dataAccessException) {
            responseAsMap.put("Mensaje", "¡No se pudo actualizar el Autor! " + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    /**
     * @param page
     * @param size
     * @return responseEntity
     */
    @GetMapping(value = "autor/get/all")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<List<AutorDTOResponse>> findAllAutor(@RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) Integer size) {
        Sort sortByName = Sort.by("nombres_autor");
        List<AutorDTOResponse> autores;
        Pageable pageable = null;

        try {
            if (page != null && size != null) {
                pageable = PageRequest.of(page, size, sortByName);
                autores = iAutorServices.findAllAutorPage(pageable).getContent();
            } else {
                autores = iAutorServices.findAllAutorSort(sortByName);
            }

            HttpStatus responseStatus = autores.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return new ResponseEntity<>(autores, responseStatus);
        } catch (Exception e) {
            log.error("Ocurrió un error al listar todos los Autores! " + e.getCause().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * @param id
     * @return
     */
    @GetMapping(value = "autor/{id}")
    private ResponseEntity<AutorDTOResponse> findById(@PathVariable int id) {
        AutorDTOResponse autor;
        try {
            autor = iAutorServices.findByIdAutor(id);
            HttpStatus responseStatus = (autor != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT;
            return new ResponseEntity<>(autor, responseStatus);
        } catch (Exception e) {
            log.error("Ocurrió un error: " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * @param id
     * @return responseEntity
     * @throws Exception
     */
    @DeleteMapping(value = "autor/delete/{id}")
    private ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            AutorDTOResponse autor = iAutorServices.findByIdAutor(id);
            if (autor != null) {
                iAutorServices.deleteAutorById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.error("Ocurrió un error al eliminar un Autor: " + e.getCause().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
