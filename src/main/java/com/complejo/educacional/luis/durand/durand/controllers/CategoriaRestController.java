package com.complejo.educacional.luis.durand.durand.controllers;

import com.complejo.educacional.luis.durand.durand.implementsServices.ICategoriaImplements;
import com.complejo.educacional.luis.durand.durand.models.Autor;
import com.complejo.educacional.luis.durand.durand.models.Categoria;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pablo
 */
@Slf4j
@RestController
@RequestMapping(value = "/biblioteca/v1/")
public class CategoriaRestController {
    @Autowired
    private ICategoriaImplements iCategoriaImplements;

    /**
     * @param categoria
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping(value = "categoria/create")
    private ResponseEntity<Map<String, Object>> addNewCategoria(@Valid @RequestBody Categoria categoria, BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        List<String> errores = null;
        if (bindingResult.hasErrors()) {
            errores = new ArrayList<String>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("Errores", errores);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_GATEWAY);
            return responseEntity;
        }
        try {
            Categoria categoriaFromDB = iCategoriaImplements.saveCategoria(categoria);
            if (categoriaFromDB != null) {
                responseAsMap.put("Categoría", categoria);
                responseAsMap.put("¡Mensaje", "La Categoría con ID: " + categoria.getId_categoria() + " se creó correctamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
                return responseEntity;
            } else {
                responseAsMap.put("Mensaje: ", "¡No sé creó la Categoria!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        } catch (DataAccessException dataAccessException) {
            responseAsMap.put("Mensaje:", "No se creo la Categoria" + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    /**
     * @param id
     * @param categoria
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PutMapping(value = "categoria/update/{id}")
    private ResponseEntity<Map<String, Object>> updateCategoria(@PathVariable long id, @Valid @RequestBody Categoria categoria, BindingResult bindingResult) throws Exception {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        List<String> errores = null;
        if (bindingResult.hasErrors()) {
            errores = new ArrayList<String>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("Errores", errores);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        try {
            Categoria udateCategoriaFromDB = iCategoriaImplements.updateCategoria(id, categoria);
            if (udateCategoriaFromDB != null) {
                responseAsMap.put("Categoría", categoria);
                responseAsMap.put("Mensaje: ", "¡La Categoría " + categoria.getId_categoria() + " sé actualizó!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
                return responseEntity;
            } else {
                responseAsMap.put("Mensaje: ", "¡La Categoría " + categoria.getId_categoria() + " no sé actualizó!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }

        } catch (DataAccessException dataAccessException) {
            responseAsMap.put("Mensaje", "No se actulizó la Categoría " + categoria.getId_categoria() + " a causa de este:" + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @GetMapping(value = "categoria/get/all")
    private ResponseEntity<List<Categoria>> findAllCategoria(@RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size) throws Exception {
        Sort sortByName = Sort.by("nombre_categoria");
        ResponseEntity<List<Categoria>> responseEntity = null;
        List<Categoria> categoriaList = null;
        Pageable pageable = null;
        try {
            if (page != null & size != null) {
                pageable = PageRequest
                        .of(page, size, sortByName);
                categoriaList = iCategoriaImplements.findAllCategoriaPage(pageable).getContent();
            } else {
                categoriaList = iCategoriaImplements.findAllCategoriaSort(sortByName);
            }
            responseEntity = (categoriaList.size() > 0) ?
                    new ResponseEntity<List<Categoria>>(categoriaList, HttpStatus.OK)
                    :
                    new ResponseEntity<List<Categoria>>(categoriaList, HttpStatus.NO_CONTENT);
            return responseEntity;
        } catch (Exception e) {
            log.error("¡Ocurrió un error al obtener la lista de Categorías!", e.getCause().toString());
            responseEntity = new ResponseEntity<List<Categoria>>(HttpStatus.BAD_GATEWAY);
        }
        return responseEntity;
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "categoria/{id}")
    private ResponseEntity<Categoria> findByIdCategoria(@PathVariable long id) throws Exception {
        Categoria categoriaSearch = null;
        ResponseEntity<Categoria> responseEntity = null;
        try {
            categoriaSearch = iCategoriaImplements.findByIdCategoria(id);
            responseEntity = (categoriaSearch != null) ?
                    new ResponseEntity<Categoria>(categoriaSearch, HttpStatus.OK)
                    :
                    new ResponseEntity<Categoria>(categoriaSearch, HttpStatus.NO_CONTENT);
            return responseEntity;
        } catch (DataAccessException dataAccessException) {
            log.error("¡Ocurrio un error al buscar la Categoría! " + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Categoria>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "categoria/delete/{id}")
    private ResponseEntity<Categoria> deleteByIdCategoria(@PathVariable long id) throws Exception {
        Categoria categoriaDelete = null;
        ResponseEntity<Categoria> responseEntity = null;
        try {
            categoriaDelete = iCategoriaImplements.findByIdCategoria(id);
            if (categoriaDelete != null) {
                iCategoriaImplements.deleteEditorialById(id);
                responseEntity = new ResponseEntity<Categoria>(HttpStatus.OK);
                return responseEntity;
            } else {
                responseEntity = new ResponseEntity<Categoria>(HttpStatus.NO_CONTENT);
                return responseEntity;
            }
        } catch (DataAccessException dataAccessException) {
            log.error("Ocurrio un error al eliminar la Categoría: " + dataAccessException.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Categoria>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
