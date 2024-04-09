package com.complejo.educacional.luis.durand.durand.controllers;
//Import necesarios para la clase.

import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.ILibroServices;
import com.complejo.educacional.luis.durand.durand.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private Utils utils;

    /**
     * Este método maneja la creación de una nueva entrada de libro a través de una solicitud POST. Utiliza la
     * informaciónproporcionada en el objeto LibroDTORequest y el resultado de validación obtenido a través de
     * BindingResult.
     * Comienza manejando los errores de validación utilizando el método handleErrors de la clase utils y retorna una
     * respuesta de error si es necesario.
     * Luego, intenta guardar el libro utilizando el método saveLibro de iLibroServices y construye la respuesta en
     * función del resultado.
     * Si el libro se guarda exitosamente, se crea un mapa de respuesta con el libro y un mensaje de éxito, y se retorna
     * una respuesta con estado OK.
     * En caso de que no se pueda crear el libro exitosamente, se genera un mensaje de error correspondiente y se
     * retorna una respuesta con estado INTERNAL_SERVER_ERROR.
     * En caso de excepciones de acceso a datos (DataAccessException), se maneja el error y se retorna una respuesta con
     * estado INTERNAL_SERVER_ERROR junto con un mensaje descriptivo.
     *
     * @param libroDTORequest El objeto LibroDTORequest que contiene la información para la nueva entrada de libro.
     * @param bindingResult   El resultado de la validación de la solicitud.
     * @return Un ResponseEntity que contiene un mapa con la respuesta de la creación del libro y el estado HTTP
     * correspondiente.
     * @throws Exception Si ocurre un error durante el proceso de creación del libro.
     */
    @PostMapping(value = "libro/create")
    public ResponseEntity<Map<String, Object>> addNewLibro(@Valid @RequestBody LibroDTORequest libroDTORequest,
                                                           BindingResult bindingResult) throws Exception {
        Map<String, Object> responseASMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> errorResponse = utils.handleErrors(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        try {
            LibroDTORequest libroFromDB = iLibroServices.saveLibro(libroDTORequest);
            if (libroFromDB != null) {
                responseASMap.put("Libro", libroDTORequest);
                responseASMap.put("Mensaje: ", "¡El Libro se creó exitosamente!");
                return new ResponseEntity<>(responseASMap, HttpStatus.OK);
            } else {
                responseASMap.put("Mensaje: ", "¡No sé creó el Libro exitosamente!");
                return new ResponseEntity<>(responseASMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            responseASMap.put("Mensaje: ", "¡No sé creó el Libro!" + e.getMostSpecificCause().getMessage().toString());
            return new ResponseEntity<>(responseASMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Este método maneja la solicitud de actualización de un libro identificado por su ID. Utiliza la información
     * proporcionada en el objeto LibroDTOResponseUpdate y el resultado de la validación de la solicitud.
     * Si la actualización es exitosa, devuelve una respuesta ResponseEntity con un mapa que contiene la información
     * actualizada del libro y un mensaje de éxito. En caso de que ocurra un error durante la actualización, se devuelve
     * una respuesta ResponseEntity con un mensaje de error y el estado correspondiente.
     *
     * @param id                     El ID del libro que se va a actualizar.
     * @param libroDTOResponseUpdate El objeto LibroDTOResponseUpdate que contiene la información actualizada del libro.
     * @param bindingResult          El resultado de la validación de la solicitud.
     * @return Un ResponseEntity que contiene un mapa con la información actualizada del libro y un mensaje, o un
     * mensaje de error en caso de fallo en la actualización.
     * @throws Exception Si ocurre un error durante el proceso de actualización del libro.
     */
    @PutMapping(value = "libro/upadate/{id}")
    public ResponseEntity<Map<String, Object>> updateLibro(@PathVariable long id, @Valid @RequestBody
                                                            LibroDTOResponseUpdate libroDTOResponseUpdate,
                                                           BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> errorResponse = utils.handleErrors(bindingResult);
        if (errorResponse != null) return errorResponse;
        try {
            LibroDTOResponse libroFromDB = iLibroServices.updateLibro(id, libroDTOResponseUpdate);
            if (libroFromDB != null && libroFromDB.getId_libro() != null) {
                responseAsMap.put("Libro", libroDTOResponseUpdate);
                responseAsMap.put("Mensaje: ", "¡Se actualizó correctamente el Autor con ID: " +
                        libroDTOResponseUpdate.getId_libro());
                return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje: ", "¡Se actualizó correctamente el Libro con ID: " +
                        libroDTOResponseUpdate.getId_libro());
                return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            responseAsMap.put("Mensaje: ", "¡No se actualizó correctamente el Libro con ID: " +
                    libroDTOResponseUpdate.getId_libro());
            return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
        }
    }

}
