package com.complejo.educacional.luis.durand.durand.services.implementsServices;
//Import necesarias para la interface.

import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponseUpdate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Pablo
 * @version 1.0
 * @create 06-04-2024 23:34
 * @project bibliotecaLuisDurandD
 */
public interface ILibroServices {

    /**
     * Método para guardar un libro en la base de datos.
     * Este método recibe un objeto LibroDTORequest y lo guarda en la base de datos. En caso de que ocurra una excepción durante el proceso, se lanzará una excepción del tipo Exception.
     *
     * @param libroDTORequest El objeto LibroDTORequest que se desea guardar.
     * @return El objeto LibroDTORequest que se ha guardado.
     * @throws Exception Si ocurre un error durante el proceso de guardado.
     */
    public LibroDTORequest saveLibro(LibroDTORequest libroDTORequest) throws Exception;

    /**
     * Método para actualizar un libro en la base de datos.
     * Este método recibe un objeto LibroDTOResponseUpdate y actualiza la información del libro correspondiente en la base de datos. En caso de que ocurra una excepción durante el proceso, se lanzará una excepción del tipo Exception.
     *
     * @param libroDTOResponseUpdate El objeto LibroDTOResponseUpdate que contiene la información actualizada del libro.
     * @return El objeto LibroDTOResponseUpdate que ha sido actualizado.
     * @throws Exception Si ocurre un error durante el proceso de actualización.
     */
    public LibroDTOResponse updateLibro(LibroDTOResponseUpdate libroDTOResponseUpdate) throws Exception;

    /**
     * Método para recuperar una lista de todos los libros con ordenación personalizada.
     * Este método devuelve una lista de libros, con la posibilidad de aplicar una ordenación específica.
     * En caso de que ocurra una excepción durante el proceso, se lanzará una excepción del tipo Exception.
     *
     * @param sort La información de ordenación para ordenar la lista de libros.
     * @return Un LibroDTOResponse que contiene la lista de libros ordenada según los criterios especificados.
     * @throws Exception Si ocurre un error durante el proceso de recuperación.
     */
    public LibroDTOResponse findAllLibroSort(Sort sort) throws Exception;

    /**
     * Método para recuperar una página de todos los libros.
     * Este método devuelve una página de libros, con la posibilidad de aplicar paginación y ordenación.
     * En caso de que ocurra una excepción durante el proceso, se lanzará una excepción del tipo Exception.
     *
     * @param pageable La información de paginación y ordenación para recuperar la página deseada de resultados.
     * @return Un Page<LibroDTOResponse> que contiene la página solicitada de libros.
     * @throws Exception Si ocurre un error durante el proceso de recuperación.
     */
    public LibroDTOResponse findAllLibroPage(Pageable pageable) throws Exception;

    /**
     * Método para encontrar un libro por su ID.
     * Este método busca un libro en la base de datos basado en su ID.
     * En caso de que ocurra una excepción durante el proceso, se lanzará una excepción del tipo Exception.
     *
     * @param id El ID del libro que se desea encontrar.
     * @return Un LibroDTOResponse que contiene la información del libro encontrado.
     * @throws Exception Si ocurre un error durante el proceso de búsqueda.
     */
    public LibroDTOResponse findByIdLibro(long id) throws Exception;

    /**
     * Método para eliminar un libro por su ID.
     * Este método elimina un libro de la base de datos basado en su ID.
     * En caso de que ocurra una excepción durante el proceso, se lanzará una excepción del tipo Exception.
     *
     * @param id El ID del libro que se desea eliminar.
     * @return Un booleano que indica si la eliminación del libro fue exitosa.
     * @throws Exception Si ocurre un error durante el proceso de eliminación.
     */
    public boolean deleteLibroById(long id) throws Exception;
}
