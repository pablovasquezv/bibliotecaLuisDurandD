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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> responseAsMap = new HashMap<>();

        // Maneja los errores de validación y devuelve una respuesta de error si es necesario.
        ResponseEntity<Map<String, Object>> errorResponse = utils.handleErrors(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }

        try {
            // Intenta guardar el nuevo libro y obtiene el resultado.
            LibroDTORequest libroFromDB = iLibroServices.saveLibro(libroDTORequest);

            if (libroFromDB != null) {
                // Si la creación fue exitosa, construye la respuesta con el libro creado y un mensaje.
                responseAsMap.put("Libro", libroDTORequest);
                responseAsMap.put("Mensaje: ", "¡El Libro se creó exitosamente!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                // Si la creación no fue exitosa, construye la respuesta con un mensaje de error.
                responseAsMap.put("Mensaje: ", "¡No sé creó el Libro exitosamente!");
                return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            // Maneja cualquier excepción de acceso a datos y construye la respuesta con un mensaje de error.
            responseAsMap.put("Mensaje: ", "¡No sé creó el Libro!" + e.getMostSpecificCause().getMessage().toString());
            return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
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

        // Maneja los errores de validación y devuelve una respuesta de error si es necesario.
        ResponseEntity<Map<String, Object>> errorResponse = utils.handleErrors(bindingResult);
        if (errorResponse != null) return errorResponse;

        try {
            // Intenta actualizar el libro y obtiene el resultado.
            LibroDTOResponse libroFromDB = iLibroServices.updateLibro(id, libroDTOResponseUpdate);

            if (libroFromDB != null && libroFromDB.getId_libro() != null) {
                // Si la actualización fue exitosa, construye la respuesta con el libro actualizado y un mensaje.
                responseAsMap.put("Libro", libroDTOResponseUpdate);
                responseAsMap.put("Mensaje: ", "¡Se actualizó correctamente el Autor con ID: " +
                        libroDTOResponseUpdate.getId_libro());
                return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                // Si la actualización no fue exitosa, construye la respuesta con un mensaje de error.
                responseAsMap.put("Mensaje: ", "¡Se actualizó correctamente el Libro con ID: " +
                        libroDTOResponseUpdate.getId_libro());
                return new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            // Maneja cualquier excepción de acceso a datos y construye la respuesta con un mensaje de error.
            responseAsMap.put("Mensaje: ", "¡No se actualizó correctamente el Libro con ID: " +
                    libroDTOResponseUpdate.getId_libro());
            return new ResponseEntity<>(responseAsMap, HttpStatus.OK);
        }
    }

    /**
     * Este método maneja las solicitudes HTTP GET para recuperar todos los libros, con la opción de paginación.
     *
     * @param page El número de página para la paginación de resultados (opcional).
     * @param size El tamaño de la página para la paginación de resultados (opcional).
     * @return Un ResponseEntity que contiene una lista de LibroDTOResponse y el código de estado HTTP correspondiente.
     */
    @GetMapping(value = "libro/gett/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LibroDTOResponse>> getPagedBooks(@RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) Integer size) {
        try {
            // Se define el criterio de ordenamiento por el campo "titulo_libro".
            Sort sortByName = Sort.by("titulo_libro");

            // Declaración de variables para almacenar los resultados de la búsqueda.
            List<LibroDTOResponse> libroDTOResponses;
            Pageable pageable = null;

            // Verifica si se proporcionan parámetros de paginación y crea el objeto Pageable correspondiente.
            if (page != null && size != null) {
                pageable = PageRequest.of(page, size, sortByName);
                libroDTOResponses = iLibroServices.findAllLibroPage(pageable).getContent();
            } else {
                // Si no se proporcionan parámetros de paginación, se recuperan todos los libros ordenados.
                libroDTOResponses = iLibroServices.findAllLibroSort(sortByName);
            }

            // Determina el código de estado HTTP en función de si la lista de libros está vacía o no.
            HttpStatus responseStatus = libroDTOResponses.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;

            // Devuelve una respuesta con la lista de libros y el código de estado HTTP correspondiente.
            return new ResponseEntity<>(libroDTOResponses, responseStatus);
        } catch (Exception e) {
            // Maneja cualquier excepción que ocurra durante la ejecución del método y devuelve un código de estado de
            // error interno del servidor.
            log.error("Ocurrió un error al listar todos los Libros: " + e.getCause().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Este método maneja las solicitudes HTTP GET para recuperar un libro por su ID.
     *
     * @param id El ID del libro que se va a buscar.
     * @return Un ResponseEntity que contiene un LibroDTOResponse y el código de estado HTTP correspondiente.
     */
    @GetMapping(value = "libro/{id}")
    public ResponseEntity<LibroDTOResponse> getBookById(@PathVariable int id) {
        LibroDTOResponse libroDTOResponse;
        try {
            // Busca el libro por su ID y asigna el resultado a libroDTOResponse.
            libroDTOResponse = iLibroServices.findByIdLibro(id);

            // Determina el código de estado HTTP en función de si se encontró el libro o no.
            HttpStatus responseStatus = (libroDTOResponse != null) ? HttpStatus.OK : HttpStatus.NO_CONTENT;

            // Devuelve una respuesta con el LibroDTOResponse y el código de estado HTTP correspondiente.
            return new ResponseEntity<>(libroDTOResponse, responseStatus);
        } catch (Exception e) {
            /**
             * Maneja cualquier excepción que ocurra durante la ejecución del método y devuelve un código de estado de
             * error interno del servidor
             */
            log.error("Ocurrió un error al listar el Libro: " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Este método maneja las solicitudes para eliminar un libro por su ID.
     *
     * @param id El ID del libro que se va a eliminar.
     * @return Un ResponseEntity que indica si la eliminación se realizó con éxito o si ocurrió un error.
     */
    @DeleteMapping(value = "libro/delete/{id}")
    public ResponseEntity<Boolean> deleteBookById(@PathVariable Long id) {
        try {
            // Busca el libro por su ID.
            LibroDTOResponse libroDTOResponse = iLibroServices.findByIdLibro(id);

            if (libroDTOResponse != null && libroDTOResponse.getId_libro() != null) {
                // Si se encuentra el libro, se procede con la eliminación y se devuelve un código de estado OK.
                log.info("Se eliminará el Libro con ID: " + id);
                iLibroServices.deleteLibroById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // Si el libro no existe, se devuelve un código de estado NO_CONTENT.
                log.error("El ID: " + id + " No existe");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            /**
             * Maneja cualquier excepción que ocurra durante la ejecución del método y devuelve un código de estado de
             * error interno del servidor
             * */
            log.error("Ocurrió un error al eliminar un Autor: " + e.getCause().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
