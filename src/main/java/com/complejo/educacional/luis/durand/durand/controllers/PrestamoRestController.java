package com.complejo.educacional.luis.durand.durand.controllers;

import com.complejo.educacional.luis.durand.durand.dto.prestamo.PrestamoDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.prestamo.PrestamoDTOResponse;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IPrestamoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @autor Pablo
 * @creates 11-05-2025 21:33
 * @proyect bibliotecaLuisDurandD
 */
@Slf4j
@RestController
@RequestMapping(value = "/biblioteca/v1/")
public class PrestamoRestController {
    @Autowired
    private IPrestamoService iPrestamoService;

    @PostMapping(value = "prestamo/create")
    private ResponseEntity<Map<String, Object>> addNewPrestamo(@Valid @RequestBody PrestamoDTORequest prestamoDTORequest,
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
            PrestamoDTORequest prestamFromDB = iPrestamoService.savePrestamo(prestamoDTORequest);
            if (prestamFromDB != null) {
                responseAsMap.put("Prestamo", prestamFromDB);
                responseAsMap.put("Mensaje:", "El prestamo se creo correctamente");
                return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("Mensaje:", "El prestamo NO se creo correctamente");
                return new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException e) {
            responseAsMap.put("Mensaje: ", "¡No se creó el Libro!" + e.getMostSpecificCause().toString());
            return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "prestamo/get/all")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<List<PrestamoDTOResponse>> getAllPrestamo(@RequestParam(required = false) Integer page,
                                                                     @RequestParam(required = false) Integer size) {
        Sort sortByName = Sort.by("estado");
        List<PrestamoDTOResponse> prestamoDTOResponseList;
        Pageable pageable = null;
        try {
            if (page != null && size != null) {
                pageable = PageRequest.of(page, size, sortByName);
                prestamoDTOResponseList = iPrestamoService.findAllPrestamoPage(pageable).getContent();
            } else {
                prestamoDTOResponseList = iPrestamoService.findAllPrestamoSort(sortByName);
            }
            HttpStatus httpStatus = prestamoDTOResponseList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
            return new ResponseEntity<>(prestamoDTOResponseList, httpStatus);
        } catch (Exception e) {
            log.error("Ocurrió un error al listar todos los Prestamos! " + e.getCause().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/{id}/devolver")
    public ResponseEntity<String> devolverPrestamo(
            @PathVariable Long id,
            @RequestParam(value = "fechaDevolucion", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDevolucion) throws Exception {
        log.info("Fecha de devolución recibida: " + fechaDevolucion); // Agrega esta línea
        // Aquí deberías obtener el préstamo desde el repositorio
        PrestamoDTOResponse prestamo = /* obtener prestamo por id */ null;
        try {
            if (prestamo == null) {
                return ResponseEntity.notFound().build();
            }

            if (fechaDevolucion == null) {
                //Decides que hacer si la fecha es nula
                fechaDevolucion = LocalDate.now(); // Por ejemplo, usar la fecha actual
            }
            iPrestamoService.marcarComoDevuelto(id, fechaDevolucion);
            BigDecimal multa = iPrestamoService.calcularMulta(prestamo, new BigDecimal("1.50"));
            prestamo.setMulta(multa);
            // Guardar préstamo actualizado
            return ResponseEntity.ok("Préstamo devuelto. Multa: " + multa);
        } catch (Exception e) {
            log.error("Ocurrió un error al devolver el Prestamo! " + e.getCause().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

