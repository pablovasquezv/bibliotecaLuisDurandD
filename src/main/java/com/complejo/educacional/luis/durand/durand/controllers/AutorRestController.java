/**
 *
 */
package com.complejo.educacional.luis.durand.durand.controllers;

import java.util.*;

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
import org.springframework.web.bind.annotation.*;

import com.complejo.educacional.luis.durand.durand.services.implementsServices.IAutorServices;
import com.complejo.educacional.luis.durand.durand.models.Autor;


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
     *
     * @param autorDTORequest
     * @param bindingResult
     * @return responseEntity
     * @throws Exception
     */
    @PostMapping(value = "autor/create")
    private ResponseEntity<Map<String, Object>> addNewAutor(@Valid @RequestBody AutorDTORequest autorDTORequest,
                                                            BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<String, Object>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        List<String> errores = null;
        if (bindingResult.hasErrors()) {
            errores = new ArrayList<String>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("errores", errores);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        try {
            AutorDTORequest autorFromDB = iAutorServices.saveAutor(autorDTORequest);
            if (autorFromDB != null) {
                responseAsMap.put("Autor", autorDTORequest);
                responseAsMap.put("Mensaje:", "El Autor ¡Sé creó exitosamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje: ", "¡No sé creó el País!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            responseAsMap.put("Mensaje: ", "¡No sé creo el País!" + e.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     *
     * @param id
     * @param autorDTOResponseUpdate
     * @param bindingResult
     * @return responseEntity
     * @throws Exception
     */
    @PutMapping(value = "autor/update/{id}")
    private ResponseEntity<Map<String, Object>> updateAutor(@PathVariable long id, @Valid @RequestBody AutorDTOResponseUpdate
                                                            autorDTOResponseUpdate, BindingResult bindingResult)
                                                            throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        List<String> errores = null;
        if (bindingResult.hasErrors()) {
            errores = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("Errores", errores);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        try {
            AutorDTOResponse autorFromDB = iAutorServices.updateAutor(id, autorDTOResponseUpdate);
            if (autorFromDB != null) {
                responseAsMap.put("Autor", autorDTOResponseUpdate);
                responseAsMap.put("Mensaje:", "¡Se actualizó correctamente el Autor con ID: " + autorDTOResponseUpdate
                        .getId_autor() + "!");
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje", "¡No se pudo actualizar el Autor!");
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException dataAccessException) {
            responseAsMap.put("Mensaje", "¡No se pudo actualizar el Autor! " +
                    dataAccessException.getMostSpecificCause().toString());
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
        // Para ordenar la búsqueda
        Sort sortByName = Sort.by("nombres_autor");
        ResponseEntity<List<AutorDTOResponse>> responseEntity = null;
        List<AutorDTOResponse> autores = null;
        Pageable pageable = null;
        try {
            if (page != null & size != null) {
                pageable = PageRequest.of(page, size, sortByName);
                autores = iAutorServices.findAllAutorPage(pageable).getContent();
            } else {
                autores = iAutorServices.findAllAutorSort(sortByName);
            }
            // Validación sí tiene Autores la lista
            responseEntity = (autores.size() > 0) ?
                    new ResponseEntity<List<AutorDTOResponse>>(autores, HttpStatus.OK)
                    :
                    new ResponseEntity<List<AutorDTOResponse>>(autores, HttpStatus.NO_CONTENT);
            /**
             if (autores.size() > 0) {
             responseEntity = new ResponseEntity<List<Autor>>(autores,HttpStatus.OK);
             } else {
             responseEntity = new ResponseEntity<List<Autor>>(autores,HttpStatus.NO_CONTENT);
             }*/
        } catch (Exception e) {
            // TODO: handle exception
            log.error("Ocurrio un error =>" + e);
        }

        return responseEntity;
    }

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "autor/{id}")
    private ResponseEntity<AutorDTOResponse> findById(@PathVariable int id) {
        AutorDTOResponse autor = null;
        ResponseEntity<AutorDTOResponse> responseEntity = null;
        try {
            autor = iAutorServices.findByIdAutor(id);
            if (autor != null) {
                responseEntity = new ResponseEntity<AutorDTOResponse>(autor, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<AutorDTOResponse>(autor, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error("Ocurrio un error =>" + e);
        }
        return responseEntity;
    }

    /**
     * @param id
     * @return responseEntity
     * @throws Exception
     */
    @DeleteMapping(value = "autor/delete/{id}")
    private ResponseEntity<AutorDTOResponse> deleteById(@PathVariable Long id) throws Exception {
        AutorDTOResponse autor = null;
        ResponseEntity<AutorDTOResponse> responseEntity = null;
        try {
            autor = iAutorServices.findByIdAutor(id);
            if (autor != null) {
                iAutorServices.deleteAutorById(id);
                responseEntity = new ResponseEntity<AutorDTOResponse>(HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<AutorDTOResponse>(HttpStatus.NO_CONTENT);
            }

        } catch (DataAccessException dataAccessException) {
            log.error("¡Ocurrio un erro al eliminar un Autor!" + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<AutorDTOResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


}
