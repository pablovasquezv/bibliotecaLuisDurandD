package com.complejo.educacional.luis.durand.durand.services.implementsServices;
//Import necesarias para la interface.

import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponseUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Pablo
 * @version 1.0
 * @create 06-04-2024 23:34
 * @project bibliotecaLuisDurandD
 */
public interface ILibroServices {

    /**
     * Método para guardar un libro en la base de datos.
     * Este método recibe un objeto LibroDTORequest y lo guarda en la base de datos. En caso de que ocurra una
     * excepción durante el proceso, se lanzará una excepción del tipo Exception.
     *
     * @param libroDTORequest El objeto LibroDTORequest que se desea guardar.
     * @return El objeto LibroDTORequest que se ha guardado.
     * @throws Exception Si ocurre un error durante el proceso de guardado.
     */
    public LibroDTORequest saveLibro(LibroDTORequest libroDTORequest) throws Exception;

    /**
     * Este método actualiza un libro existente identificado por su ID, utilizando la información proporcionada en el
     * objeto LibroDTOResponseUpdate.
     * Si la actualización es exitosa, se devuelve un objeto LibroDTOResponse que contiene la información actualizada
     * del libro.
     * En caso de que ocurra una excepción durante el proceso de actualización, se lanza una excepción con un mensaje
     * descriptivo.
     *
     * @param id                     El ID del libro que se va a actualizar.
     * @param libroDTOResponseUpdate El objeto LibroDTOResponseUpdate que contiene la información actualizada del libro.
     * @return Un objeto LibroDTOResponse que contiene la información actualizada del libro.
     * @throws Exception Si ocurre un error durante el proceso de actualización del libro.
     */
    public LibroDTOResponse updateLibro(Long id, LibroDTOResponseUpdate libroDTOResponseUpdate) throws Exception;


    /**
     * Este método busca y devuelve una lista de objetos LibroDTOResponse ordenados según el criterio especificado en el
     * parámetro 'sort'.
     * Si la operación es exitosa, se devuelve la lista de libros ordenada.
     * En caso de que ocurra una excepción durante la búsqueda, se lanza una excepción con un mensaje descriptivo.
     *
     * @param sort El criterio de ordenamiento a aplicar a la lista de libros.
     * @return Una lista de objetos LibroDTOResponse ordenados según el criterio especificado.
     * @throws Exception Si ocurre un error durante la búsqueda de la lista de libros.
     */
    public List<LibroDTOResponse> findAllLibroSort(Sort sort) throws Exception;


    /**
     * Este método busca y devuelve una página de objetos LibroDTOResponse de acuerdo con el criterio de paginación
     * especificado en el parámetro 'pageable'.
     * Si la operación es exitosa, se devuelve la página de libros.
     * En caso de que ocurra una excepción durante la búsqueda, se lanza una excepción con un mensaje descriptivo.
     *
     * @param pageable El objeto Pageable que especifica el criterio de paginación a aplicar a la búsqueda de libros.
     * @return Una página de objetos LibroDTOResponse de acuerdo con el criterio de paginación especificado.
     * @throws Exception Si ocurre un error durante la búsqueda de la página de libros.
     */
    public Page<LibroDTOResponse> findAllLibroPage(Pageable pageable) throws Exception;


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
