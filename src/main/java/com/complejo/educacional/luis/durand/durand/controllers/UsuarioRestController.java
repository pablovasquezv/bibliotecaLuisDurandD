package com.complejo.educacional.luis.durand.durand.controllers;

import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTOResponse;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IUsuarioServices;
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
 * @version 1.0
 * @autor Pablo
 * @creates 10-05-2025 20:09
 * @proyect bibliotecaLuisDurandD
 */
@Slf4j
@RestController
@RequestMapping(value = "/biblioteca/v1/")
public class UsuarioRestController {
    @Autowired
    private IUsuarioServices iUsuarioServices;

    @PostMapping(value = "create/user")
    private ResponseEntity<Map<String, Object>> addNewUser(@Valid @RequestBody UsuarioDTORequest usuarioDTORequest,
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
            UsuarioDTORequest usuarioDTORequestFromDB = iUsuarioServices.suaveUsuario(usuarioDTORequest);
            if (usuarioDTORequestFromDB != null) {
                responseAsMap.put("Usuario", usuarioDTORequestFromDB);
                responseAsMap.put("Mensaje: ", "El usuario se creó correctamente");
                return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje: ", "El usuario No se creó correctamente");
                return new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException e) {
            responseAsMap.put("Mensaje: ", "¡No se creó el Libro!" + e.getMostSpecificCause().toString());
            return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "usuario/get/all/")
    private ResponseEntity<List<UsuarioDTOResponse>> getAllUser(@RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) Integer size) {
        Sort sortByName = Sort.by("nombres_usuario");
        List<UsuarioDTOResponse> usuarioDTOResponseList;
        Pageable pageable = null;
        try {
            if (page != null && size != null) {
                pageable = PageRequest.of(page, size, sortByName);
                usuarioDTOResponseList = iUsuarioServices.findAllUsuarioPage(pageable).getContent();
            } else {
                usuarioDTOResponseList = iUsuarioServices.findAllUsuarioSort(sortByName);
            }

            HttpStatus httpStatus = usuarioDTOResponseList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return new ResponseEntity<>(usuarioDTOResponseList, httpStatus);
        } catch (Exception e) {
            log.error("Ocurrió un error al listar todos los Usuarios! " + e.getCause().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
