package com.complejo.educacional.luis.durand.durand.utils;
//Import necesarios para la clase.

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Pablo
 */
@Component
@Slf4j
public class Utils<E> {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Este método se encarga de imprimir un registro de entrada, convirtiendo el objeto E a su representación JSON
     * utilizando el objeto objectMapper.
     * Si la conversión es exitosa, se devuelve la representación JSON del objeto. En caso de que ocurra una excepción
     * de JsonProcessingException durante la conversión, se registra un error y se devuelve null.
     *
     * @param e El objeto de tipo E que se va a imprimir como registro de entrada.
     * @return La representación JSON del objeto E, o null si ocurre una excepción durante la conversión.
     */
    public String imprimirLogEntrada(E e) {
        String jsonResponse = null;
        try {
            jsonResponse = objectMapper.writeValueAsString(e);
        } catch (JsonProcessingException ex) {
            log.error("Ocurrio un Error =>" + e);
        }
        return jsonResponse;
    }

    /**
     * Este método se encarga de imprimir un registro de salida, convirtiendo el objeto E a su representación JSON
     * utilizando el objeto objectMapper.
     * Si la conversión es exitosa, se devuelve la representación JSON del objeto. En caso de que ocurra una excepción
     * de JsonProcessingException durante la conversión, se registra un error y se devuelve null.
     *
     * @param e El objeto de tipo E que se va a imprimir como registro de salida.
     * @return La representación JSON del objeto E, o null si ocurre una excepción durante la conversión.
     */
    public String imprimirLogSalida(E e) {
        String jsonResponse = null;
        try {
            jsonResponse = objectMapper.writeValueAsString(e);
        } catch (JsonProcessingException ex) {
            log.error("Ocurrió un Error =>" + e);
        }
        return jsonResponse;
    }


    /**
     * Este método maneja los errores de validación y devuelve una respuesta ResponseEntity que contiene un mapa con
     * los errores, si existen, o null si no hay errores de validación.
     * Si se encuentran errores de validación en el BindingResult, se obtienen los mensajes de error y se agregan al
     * mapa de respuesta, el cual se devuelve con un estado HttpStatus.BAD_REQUEST.
     * En caso de que no haya errores de validación, se devuelve null.
     *
     * @param bindingResult El resultado de la validación de la solicitud.
     * @return Un ResponseEntity que contiene un mapa con los errores de validación, o null si no hay errores.
     */
    public ResponseEntity<Map<String, Object>> handleErrors(BindingResult bindingResult) {
        Map<String, Object> responseASMap = new HashMap<>();
        List<String> errores;
        if (bindingResult.hasErrors()) {
            errores = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            responseASMap.put("Errores:", errores);
            return new ResponseEntity<>(responseASMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
