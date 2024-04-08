package com.complejo.educacional.luis.durand.durand.controllers;
//Import necesarios para la clase.

import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTORequest;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.ILibroServices;
import com.complejo.educacional.luis.durand.durand.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * Este método maneja la creación de una nueva entrada de libro a través de una solicitud POST. Utiliza la información
     * proporcionada en el objeto LibroDTORequest y el resultado de validación obtenido a través de BindingResult.
     * Comienza manejando los errores de validación utilizando el método handleErrors de la clase utils y retorna una
     * respuesta de error si es necesario.
     * Luego, intenta guardar el libro utilizando el método saveLibro de iLibroServices y construye la respuesta en
     * función del resultado.
     * Si el libro se guarda exitosamente, se crea un mapa de respuesta con el libro y un mensaje de éxito, y se retorna
     * una respuesta con estado OK.
     * En caso de que no se pueda crear el libro exitosamente, se genera un mensaje de error correspondiente y se retorna
     * una respuesta con estado INTERNAL_SERVER_ERROR.
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

}
