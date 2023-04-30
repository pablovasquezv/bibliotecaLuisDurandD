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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.complejo.educacional.luis.durand.durand.implementsServices.IAutorImplements;
import com.complejo.educacional.luis.durand.durand.models.Autor;
import com.complejo.educacional.luis.durand.durand.models.Pais;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Pablo
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "biblioteca/autor")
public class AutorRestController {
	@Autowired
	private IAutorImplements iAutorImplements;

	@PostMapping(value = "create")
	private ResponseEntity<Map<String, Object>> addNewAutor(@Valid @RequestBody Autor autor,
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
			Autor autorFromDB = iAutorImplements.saveAutor(autor);
			if (autorFromDB != null) {
				responseAsMap.put("Autor", autor);
				responseAsMap.put("Mensaje:", "El Autor con ID:" + autor.getId_autor() + "¡Sé creó exitosamente!");
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

	@GetMapping(value = "/get/all")
	@ResponseStatus(HttpStatus.OK)
	private ResponseEntity<List<Autor>> findAllAutor(@RequestParam(required = false) Integer page,
												     @RequestParam(required = false) Integer size) {
		// Para ordenar la búsqueda
		Sort sortByName = Sort.by("nombres_autor");
		ResponseEntity<List<Autor>> responseEntity = null;
		List<Autor> autores = null;

		try {
			if (page != null & size != null) {
				Pageable pageable = PageRequest.of(page, size, sortByName);
				autores = iAutorImplements.findAllAutorPage(pageable).getContent();
			} else {
				autores = iAutorImplements.findAllAutorSort(sortByName);
			}
			// Validación sí tiene Paises la lista
			responseEntity=(autores.size() > 0)?
					new ResponseEntity<List<Autor>>(autores,HttpStatus.OK)
					:
				    new ResponseEntity<List<Autor>>(autores,HttpStatus.NO_CONTENT);
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

	@GetMapping(value = "{id}")
	private ResponseEntity<Autor> findById(@PathVariable int id) {
		Autor autor = null;
		ResponseEntity<Autor> responseEntity = null;
		try {
			autor = iAutorImplements.findById(id);
			if (autor != null) {
				responseEntity = new ResponseEntity<Autor>(autor, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<Autor>(autor, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Ocurrio un error =>" + e);
		}
		return responseEntity;
	}
}
