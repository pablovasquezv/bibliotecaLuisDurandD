/**
 * 
 */
package com.complejo.educacional.luis.durand.durand.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.complejo.educacional.luis.durand.durand.implementsServices.IPaisImplements;
import com.complejo.educacional.luis.durand.durand.models.Pais;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Pablo
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "biblioteca/pais")
public class PaisRestController {
	@Autowired
	private IPaisImplements iPaisImplements;

	@PostMapping("/create")
	private ResponseEntity<Map<String, Object>> addNewPais(@Valid @RequestBody Pais pais, BindingResult bindingResult)
			throws Exception {
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
			Pais paisFromDB = iPaisImplements.save(pais);
			if (paisFromDB != null) {
				responseAsMap.put("producto", pais);
				responseAsMap.put("Mensaje: ", "El País con ID: " + pais.getId_pais() + "¡Sé creó exitosamente!");
				responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
			} else {
				responseAsMap.put("Mensaje: ", "¡No sé creó el País!");
				responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			// TODO: handle exception}
			responseAsMap.put("Mensaje: ", "¡No sé creo el País!" + e.getMostSpecificCause().toString());
			responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@PutMapping("/update/{id}")
	private ResponseEntity<Map<String, Object>> updatePais(@PathVariable long id, @Valid @RequestBody Pais pais,
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
			Pais paisFromDB = iPaisImplements.update(id, pais);
			if (paisFromDB != null) {
				responseAsMap.put("producto", pais);
				responseAsMap.put("Mensaje: ", "El País con ID: " + pais.getId_pais() + "¡Sé Actualizo exitosamente!");
				responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
			} else {
				responseAsMap.put("Mensaje: ", "¡No sé actualizo el País!");
				responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataAccessException e) {
			// TODO: handle exception}
			responseAsMap.put("Mensaje: ", "¡No sé actualizo el País!" + e.getMostSpecificCause().toString());
			responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping("/get/all")
	@ResponseStatus(HttpStatus.OK)
	private ResponseEntity<List<Pais>> findAllAlumnos(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {
		// Para ordenar la búsqueda
		Sort sortByName = Sort.by("nombre_pais");
		ResponseEntity<List<Pais>> responseEntity = null;
		List<Pais> paises = null;
		try {
			if (page != null & size != null) {
				Pageable pageable = PageRequest.of(page, size, sortByName);
				paises = iPaisImplements.findAllPaisPageable(pageable).getContent();
			} else {
				paises = iPaisImplements.findAllPaisSort(sortByName);
			}
			// Validación sí tiene Paises la lista
			responseEntity=(paises.size() > 0)?new ResponseEntity<List<Pais>>(paises, HttpStatus.OK):
				new ResponseEntity<List<Pais>>(HttpStatus.NO_CONTENT);
					
			
			/**if (paises.size() > 0) {
				responseEntity = new ResponseEntity<List<Pais>>(paises, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<List<Pais>>(HttpStatus.NO_CONTENT);
			}*/
		} catch (Exception e) {
			 log.error("Ocurrio un error a Listar los Paises => "+e);
		}
		return responseEntity;
	}

	@GetMapping(value = "{id}")
	private ResponseEntity<Pais> findById(@PathVariable int id) {
		Pais pais = null;
		ResponseEntity<Pais> responseEntity = null;
		try {
			pais = iPaisImplements.findById(id);
			responseEntity=(pais != null) ?  
					new ResponseEntity<Pais>(pais, HttpStatus.OK): 
					new ResponseEntity<Pais>(pais, HttpStatus.NO_CONTENT);
			/**if (pais != null) {
				responseEntity = new ResponseEntity<Pais>(pais, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<Pais>(pais, HttpStatus.NO_CONTENT);
			}*/
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Ocurrio un error =>" + e);
		}
		return responseEntity;
	}

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<Pais> deleteById(@PathVariable int id) {
        // TODO Auto-generated method stub
        Pais paises = null;
        ResponseEntity<Pais> responseEntity = null;
        try {

            paises = iPaisImplements.findById(id);
            // si exite
            if (paises != null) {
                // retorna un 200
                iPaisImplements.delete(id);
                responseEntity = new ResponseEntity<Pais>(HttpStatus.OK);
            } else {
                // retorna un 202
                responseEntity = new ResponseEntity<Pais>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.error("Ocurrio un Error =>" + e);
        }

        return responseEntity;
    }
}
