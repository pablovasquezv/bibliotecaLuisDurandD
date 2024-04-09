package com.complejo.educacional.luis.durand.durand.controllers;
//Import necesarias para la clase.

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.stream.Collectors;

import javax.validation.Valid;

import com.complejo.educacional.luis.durand.durand.dto.autor.AutorDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.autor.AutorDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.autor.AutorDTOResponseUpdate;
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
     * Método que crea un nuevo autor basado en los datos proporcionados..
     *
     * @param autorDTORequest El objeto DTO que contiene los detalles del autor que se creará.
     * @param bindingResult   El resultado del proceso de validación para el objeto DTO.
     * @return Una ResponseEntity que contiene el resultado de la creación del autor y el estado HTTP correspondiente.
     * @throws Exception Excepción si ocurre un error durante el proceso de creación del autor.
     */
    @PostMapping(value = "autor/create")
    private ResponseEntity<Map<String, Object>> addNewAutor(@Valid @RequestBody AutorDTORequest autorDTORequest,
                                                            BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        List<String> errores;

        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, construye la respuesta con los errores y un código de estado BAD_REQUEST.
            errores = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            responseAsMap.put("errores", errores);
            return new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
        }

        try {
            // Intenta guardar el nuevo autor y obtiene el resultado.
            AutorDTORequest autorFromDB = iAutorServices.saveAutor(autorDTORequest);
            if (autorFromDB != null) {
                // Si la creación fue exitosa, construye la respuesta con el nuevo autor y un mensaje de éxito.
                responseAsMap.put("Autor", autorDTORequest);
                responseAsMap.put("Mensaje:", "El Autor ¡Se creó exitosamente!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                // Si la creación no fue exitosa, construye la respuesta con un mensaje de error.
                responseAsMap.put("Mensaje: ", "¡No se creó el Autor!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            // Maneja cualquier excepción de acceso a datos y construye la respuesta con un mensaje de error.
            responseAsMap.put("Mensaje: ", "¡No se creó el Autor!" + e.getMostSpecificCause().toString());
            return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Método que actualiza un autor según su ID.
     *
     * @param id                     El ID del autor a actualizar.
     * @param autorDTOResponseUpdate La información actualizada del autor.
     * @param bindingResult          El resultado del proceso de validación.
     * @return ResponseEntity con el resultado de la actualización y mensajes descriptivos.
     */
    @PutMapping(value = "autor/update/{id}")
    private ResponseEntity<Map<String, Object>> updateAutor(@PathVariable long id, @Valid
    @RequestBody AutorDTOResponseUpdate autorDTOResponseUpdate,
                                                            BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity;

        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, construye la respuesta con los errores y un código de estado BAD_REQUEST.
            List<String> errores = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            responseAsMap.put("Errores", errores);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }

        try {
            // Intenta actualizar el autor y obtiene el resultado.
            AutorDTOResponse autorFromDB = iAutorServices.updateAutor(id, autorDTOResponseUpdate);

            if (autorFromDB != null && autorFromDB.getId_autor() != null) {
                // Si la actualización fue exitosa, construye la respuesta con el autor actualizado y un mensaje.
                responseAsMap.put("Autor", autorDTOResponseUpdate);
                responseAsMap.put("Mensaje:", "¡Se actualizó correctamente el Autor con ID: " +
                        autorDTOResponseUpdate.getId_autor() + "!");
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                // Si la actualización no fue exitosa, construye la respuesta con un mensaje de error.
                responseAsMap.put("Mensaje", "¡No se pudo actualizar el Autor!");
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException dataAccessException) {
            // Maneja cualquier excepción de acceso a datos y construye la respuesta con un mensaje de error.
            responseAsMap.put("Mensaje", "¡No se pudo actualizar el Autor! " +
                    dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * Método que recupera una lista de autores con paginación opcional.
     *
     * @param page El número de página para la paginación (opcional).
     * @param size El tamaño de página para la paginación (opcional).
     * @return Una ResponseEntity que contiene la lista de autores y el estado HTTP correspondiente.
     */
    @GetMapping(value = "autor/get/all")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<List<AutorDTOResponse>> getPagedAuthor(@RequestParam(required = false) Integer page,
                                                                  @RequestParam(required = false) Integer size) {
        // Se define el criterio de ordenamiento por el campo "nombres_autor".
        Sort sortByName = Sort.by("nombres_autor");
        // Declaración de variables para almacenar los resultados de la búsqueda.
        List<AutorDTOResponse> autores;
        Pageable pageable = null;

        try {
            // Verifica si se proporcionan parámetros de paginación y crea el objeto Pageable correspondiente.
            if (page != null && size != null) {
                pageable = PageRequest.of(page, size, sortByName);
                autores = iAutorServices.findAllAutorPage(pageable).getContent();
            } else {
                // Si no se proporcionan parámetros de paginación, se recuperan todos los autores ordenados.
                autores = iAutorServices.findAllAutorSort(sortByName);
            }

            // Determina el código de estado HTTP en función de si la lista de autores está vacía o no.
            HttpStatus responseStatus = autores.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;

            // Devuelve una respuesta con la lista de AutorDTOResponse y el código de estado HTTP correspondiente.
            return new ResponseEntity<>(autores, responseStatus);
        } catch (Exception e) {
            /**
             * Maneja cualquier excepción que ocurra durante la ejecución del método y devuelve un código de estado de
             * error interno del servidor
             */
            log.error("Ocurrió un error al listar todos los Autores! " + e.getCause().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que recupera un autor por su ID.
     *
     * @param id El ID del autor a recuperar.
     * @return Una ResponseEntity que contiene la información del autor y el estado HTTP correspondiente.
     */

    @GetMapping(value = "autor/{id}")
    private ResponseEntity<AutorDTOResponse> getAuthorById(@PathVariable int id) {
        AutorDTOResponse autor;
        try {
            // Busca el autor por su ID y asigna el resultado a la variable 'autor'.
            autor = iAutorServices.findByIdAutor(id);

            // Determina el código de estado HTTP en función de si se encontró el autor o no.
            HttpStatus responseStatus = (autor != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT;

            // Devuelve una respuesta con el AutorDTOResponse y el código de estado HTTP correspondiente.
            return new ResponseEntity<>(autor, responseStatus);
        } catch (Exception e) {
            /**
             * Maneja cualquier excepción que ocurra durante la ejecución del método y devuelve un código de estado de
             * error interno del servidor
             */
            log.error("Ocurrió un error: " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Método que elimina un autor según su ID.
     *
     * @param id El ID del autor a eliminar.
     * @return ResponseEntity con un valor booleano que indica el resultado de la eliminación.
     */

    @DeleteMapping(value = "autor/delete/{id}")
    private ResponseEntity<Void> deleteAuthorById(@PathVariable Long id) {
        try {
            // Busca el autor por su ID.
            AutorDTOResponse autor = iAutorServices.findByIdAutor(id);

            if (autor != null) {
                // Si se encuentra el autor, se procede con la eliminación y se devuelve un código de estado OK.
                iAutorServices.deleteAutorById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // Si el autor no existe, se devuelve un código de estado NO_CONTENT.
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            /**
             * Maneja cualquier excepción que ocurra durante la ejecución del método y devuelve un código de estado de
             * error interno del servidor
             */
            log.error("Ocurrió un error al eliminar un Autor: " + e.getCause().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
