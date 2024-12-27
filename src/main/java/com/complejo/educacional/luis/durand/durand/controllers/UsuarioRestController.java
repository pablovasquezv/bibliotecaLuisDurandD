package com.complejo.educacional.luis.durand.durand.controllers;

import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTOResponse;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IUsuarioServices;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @autor Pablo
 * @create 15-04-2024 12:58
 * @project bibliotecaLuisDurandD
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/biblioteca/v1/")
public class UsuarioRestController {

    @Autowired
    private IUsuarioServices iUsuarioServices;

    /**
     * Método que crea un nuevo usuario basado en los datos proporcionados..
     *
     * @param usuarioDTORequest El objeto DTO que contiene los detalles del usuario que se creará.
     * @param bindingResult     El resultado del proceso de validación para el objeto DTO.
     * @return Una ResponseEntity que contiene el resultado de la creación del usuario y el estado HTTP correspondiente.
     * @throws Exception Excepción si ocurre un error durante el proceso de creación del usuario.
     */
    @PostMapping(value = "usuario/create")
    public ResponseEntity<Map<String, Object>> addNewUsuario(@Valid @RequestBody UsuarioDTORequest usuarioDTORequest, BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        List<String> errores;
        if (bindingResult.hasErrors()) {
            errores = bindingResult.getAllErrors().stream().map(
                    ObjectError::getDefaultMessage
            ).collect(Collectors.toList());
            responseAsMap.put("errores", errores);
            return new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
        }
        try {
            UsuarioDTORequest usuarioFromDB;
            usuarioFromDB = iUsuarioServices.saveUsuario(usuarioDTORequest);
            if (usuarioFromDB != null) {
                responseAsMap.put("Usuario", usuarioDTORequest);
                responseAsMap.put("Mensaje", "¡El usuario se creó correctamente");
                return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje", "¡El usuario no sé creó correctamente!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            responseAsMap.put("Mensaje", "¡No sé creó el Usuario!" + e.getMostSpecificCause().getMessage().toString());
            return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que recupera una lista de géneros con paginación opcional.
     *
     * @param page El número de página para la paginación (opcional).
     * @param size El tamaño de página para la paginación (opcional).
     * @return Una ResponseEntity que contiene la lista de géneros y el estado HTTP correspondiente.
     */
    @GetMapping(value = "usuario/get/all")
    public ResponseEntity<List<UsuarioDTOResponse>> allPageUsuario(@RequestParam(required = false) Integer page,
                                                                   @RequestParam(required = false) Integer size)
            throws Exception {
        Sort sortByName = Sort.by("nombre_usuario");
        List<UsuarioDTOResponse> usuarioDTOResponseList = null;
        Pageable pageable = null;
        HttpStatus responseStatus;
        try {
            pageable = (page != null && size != null) ?
                    PageRequest.of(page, size, sortByName)
                    :
                    null;
            usuarioDTOResponseList = (page != null) ?
                    iUsuarioServices.findAllUsuarioPage(pageable).getContent()
                    :
                    iUsuarioServices.findAllUsuarioSort(sortByName);
            responseStatus= usuarioDTOResponseList.isEmpty()? HttpStatus.NO_CONTENT: HttpStatus.OK;
            return new ResponseEntity<>(usuarioDTOResponseList,responseStatus);
        } catch (Exception e) {
            String errorMesaje = (e.getCause()!=null)?e.getMessage().toString():"Error desconocido";
            log.error("Un error a ocurrido en la lista de todos los Usuarios"+usuarioDTOResponseList+":"+errorMesaje);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
