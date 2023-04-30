/**
 * 
 */
package com.complejo.educacional.luis.durand.durand.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @author Pablo
 *
 */
@Component
@Slf4j
public class Utils <E>{
	@Autowired
    private ObjectMapper objectMapper;
	
	public String imprimirLogEntrada(E e) {
        String jsonResponse = null;
        try {
            jsonResponse = objectMapper.writeValueAsString(e);
        } catch (JsonProcessingException ex) {
            log.error("Ocurrio un Error =>" + e);
        }
        return jsonResponse;
    }

    public String imprimirLogSalida(E e) {
        String jsonResponse = null;
        try {
            jsonResponse = objectMapper.writeValueAsString(e);
        } catch (JsonProcessingException ex) {
            log.error("Ocurrio un Error =>" + e);
        }
        return jsonResponse;
    }

}
