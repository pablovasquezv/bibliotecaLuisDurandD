package com.complejo.educacional.luis.durand.durand.services;
// Import necesarios para la clase.

import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.libro.LibroDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.models.Autor;
import com.complejo.educacional.luis.durand.durand.models.Categoria;
import com.complejo.educacional.luis.durand.durand.models.Editorial;
import com.complejo.educacional.luis.durand.durand.models.Genero;
import com.complejo.educacional.luis.durand.durand.models.Libro;
import com.complejo.educacional.luis.durand.durand.repositories.IAutorRepository;
import com.complejo.educacional.luis.durand.durand.repositories.ICategoriaRepository;
import com.complejo.educacional.luis.durand.durand.repositories.IEditorialRepository;
import com.complejo.educacional.luis.durand.durand.repositories.IGeneroRepository;
import com.complejo.educacional.luis.durand.durand.repositories.ILibroRepository;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.ILibroServices;
import com.complejo.educacional.luis.durand.durand.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Pablo
 * @version 1.0
 * @create 06-04-2024 23:49
 * @project bibliotecaLuisDurandD
 */
@Slf4j
@Service
public class LibroServicesImpl implements ILibroServices {
    @Autowired
    private ILibroRepository iLibroRepository;
    @Autowired
    private IAutorRepository iAutorRepository;
    @Autowired
    private ICategoriaRepository iCategoriaRepository;
    @Autowired
    private IEditorialRepository iEditorialRepository;
    @Autowired
    private IGeneroRepository iGeneroRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Este método guarda una nueva entrada de libro en la base de datos, utilizando la información proporcionada en el
     * objeto LibroDTORequest.
     * Comienza obteniendo referencias a las entidades relacionadas (Autor, Categoría, Editorial y Género) basadas en
     * los IDs proporcionados.
     * Luego, se crea y guarda un nuevo objeto Libro utilizando la instancia iLibroRepository.
     * Después de guardar el libro, se construye y retorna un nuevo objeto LibroDTORequest con la información de la
     * entrada de libro guardada.
     * En caso de que ocurran excepciones durante el proceso, se registra un error y se lanza una excepción con un
     * mensaje de error.
     *
     * @param libroDTORequest El objeto LibroDTORequest que contiene la información para la nueva entrada de libro.
     * @return Un objeto LibroDTORequest que contiene la información de la entrada de libro guardada.
     * @throws Exception Si ocurre un error durante el proceso de guardar la entrada de libro.
     */

    @Override
    @Transactional(readOnly = false)
    public LibroDTORequest saveLibro(LibroDTORequest libroDTORequest) throws Exception {
        try {
            Autor autor = iAutorRepository.getReferenceById(libroDTORequest.getId_autor());
            Categoria categoria = iCategoriaRepository.getReferenceById(libroDTORequest.getId_categoria());
            Editorial editorial = iEditorialRepository.getReferenceById(libroDTORequest.getId_editorial());
            Genero genero = iGeneroRepository.getReferenceById(libroDTORequest.getId_genero());
            Libro createLibro = new Libro(
                    null,
                    libroDTORequest.getTitulo_libro(),
                    autor,
                    categoria,
                    editorial,
                    genero,
                    libroDTORequest.getPaginas_libro(),
                    libroDTORequest.getEdicion_libro()
            );

            log.info("---Inicio de creción Libro----" + objectMapper.writeValueAsString(libroDTORequest));
            createLibro = iLibroRepository.save(createLibro);
            log.info("Json de Salida =>" + objectMapper.writeValueAsString(createLibro));
            log.info("----Fin de método Creación Libro----");

            return new LibroDTORequest(
                    createLibro.getTitulo_libro(),
                    createLibro.getAutor().getId_autor(),
                    createLibro.getCategoria().getId_categoria(),
                    createLibro.getEditorial().getId_editorial(),
                    createLibro.getGenero().getId_genero(),
                    createLibro.getEdicion_libro(),
                    createLibro.getPaginas_libro()

            );
        } catch (Exception e) {
            log.error("Ocurrió un error al guardar el Libro: " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al guardar el Libro!");
        }
    }

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
    @Override
    @Transactional(readOnly = false)
    public LibroDTOResponse updateLibro(Long id, LibroDTOResponseUpdate libroDTOResponseUpdate) throws Exception {
        try {
            Optional<Libro> libroOptional = iLibroRepository.findById(id);
            log.info("---Inicio de actualización Libro----" + objectMapper.writeValueAsString(libroOptional));
            if (libroOptional.isPresent()) {
                Libro libro = libroOptional.get();
                Autor autor = iAutorRepository.getReferenceById(libroDTOResponseUpdate.getId_autor());
                Categoria categoria = iCategoriaRepository.getReferenceById(libroDTOResponseUpdate.getId_categoria());
                Editorial editorial = iEditorialRepository.getReferenceById(libroDTOResponseUpdate.getId_editorial());
                Genero genero = iGeneroRepository.getReferenceById(libroDTOResponseUpdate.getId_genero());
                libro.setTitulo_libro(libroDTOResponseUpdate.getTitulo_libro());
                libro.setAutor(autor);
                libro.setCategoria(categoria);
                libro.setEditorial(editorial);
                libro.setGenero(genero);
                libro.setEdicion_libro(libroDTOResponseUpdate.getEdicion_libro());
                libro.setPaginas_libro(libroDTOResponseUpdate.getPaginas_libro());
                Libro updateLibro = iLibroRepository.save(libro);
                log.info("Json de Salida =>" + utils.imprimirLogSalida(updateLibro));
                return new LibroDTOResponse(
                        updateLibro.getId_libro(),
                        updateLibro.getTitulo_libro(),
                        updateLibro.getAutor().getId_autor(),
                        updateLibro.getCategoria().getId_categoria(),
                        updateLibro.getEditorial().getId_editorial(),
                        updateLibro.getGenero().getId_genero(),
                        updateLibro.getEdicion_libro(),
                        updateLibro.getPaginas_libro()
                );
            } else {
                log.error("¡Ocurrió un error en la actualización del Libro!" + id + libroOptional.toString());
                throw new Exception("¡Ocurrió un error en la actualización del Libro!");
            }
        } catch (Exception e) {
            log.error("¡Ocurrió un error en la actualización del Autor con ID " + id + ": " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error en la actualización del Autor!");
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<LibroDTOResponse> findAllLibroSort(Sort sort) throws Exception {
        try {
            /**
             * Utilicé el método stream() y map() para convertir la lista de Libros en una lista de LibroDTOResponse
             * de forma más concisa.
             */
            List<Libro> libroList = iLibroRepository.findAllLibroSort(sort);
            return libroList.stream().
                    map(libro -> new LibroDTOResponse(
                                    libro.getId_libro(),
                                    libro.getTitulo_libro(),
                                    libro.getAutor().getId_autor(),
                                    libro.getCategoria().getId_categoria(),
                                    libro.getEditorial().getId_editorial(),
                                    libro.getGenero().getId_genero(),
                                    libro.getEdicion_libro(),
                                    libro.getPaginas_libro()
                            )
                    ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Ocurrió un error al listar todos los Libros: " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al listar todos los Libros!");
        }
    }

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
    @Override
    @Transactional(readOnly = true)
    public Page<LibroDTOResponse> findAllLibroPage(Pageable pageable) throws Exception {
        try {
            Page<Libro> libroPage = iLibroRepository.findAllAutorPage(pageable);
            /**
             * Utilicé el método stream() y map() para convertir la lista de autor en una lista
             * de AutorDTOResponse de forma más concisa.
             */
            List<LibroDTOResponse> libroDTOResponses = libroPage.stream().
                    map(libro -> new LibroDTOResponse(
                            libro.getId_libro(),
                            libro.getTitulo_libro(),
                            libro.getAutor().getId_autor(),
                            libro.getCategoria().getId_categoria(),
                            libro.getEditorial().getId_editorial(),
                            libro.getGenero().getId_genero(),
                            libro.getEdicion_libro(),
                            libro.getPaginas_libro()
                    )).collect(Collectors.toList());
            /**
             * Utilicé el método collect() junto con Collectors.toList() para recopilar los elementos mapeados
             * en una lista.
             */
            return new PageImpl<>(libroDTOResponses, pageable, libroPage.getTotalElements());
            /**
             * Utilicé el método collect() junto con Collectors.toList() para recopilar los elementos mapeados
             * en una lista.
             */
        } catch (Exception e) {
            log.error("Ocurrió un error al listar todos los Libros: " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al listar todos los Libros!");
        }
    }


    /**
     * Este método busca y devuelve un objeto LibroDTOResponse correspondiente al ID proporcionado.
     * Si el libro se encuentra, se devuelve un objeto LibroDTOResponse con la información del libro.
     * En caso de que el libro no se encuentre, se lanza una excepción con un mensaje descriptivo.
     * Si ocurre una excepción durante la búsqueda, se lanza una excepción con un mensaje descriptivo.
     *
     * @param id El ID del libro que se va a buscar.
     * @return Un objeto LibroDTOResponse con la información del libro correspondiente al ID proporcionado.
     * @throws Exception Si el libro no se encuentra o si ocurre un error durante la búsqueda.
     */
    @Override
    @Transactional(readOnly = true)
    public LibroDTOResponse findByIdLibro(long id) throws Exception {
        try {
            Libro libro = iLibroRepository.findByLibroAndAutorAndCategoriaAndEditorialAndGenero(id);
            if (libro.getId_libro() == null) {
                throw new Exception("¡Libro no encontrado!");
            } else {
                return new LibroDTOResponse(
                        libro.getId_libro(),
                        libro.getTitulo_libro(),
                        libro.getAutor().getId_autor(),
                        libro.getCategoria().getId_categoria(),
                        libro.getEditorial().getId_editorial(),
                        libro.getGenero().getId_genero(),
                        libro.getEdicion_libro(),
                        libro.getPaginas_libro()
                );
            }

        } catch (Exception e) {
            log.error("Ocurrió un error al buscar el Libro con ID " + id + ": " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al buscar el Libro!");
        }
    }

    /**
     * Este método intenta eliminar un libro según el ID proporcionado.
     * Si el libro se elimina con éxito, devuelve true. Si el ID del libro no existe, lanza una excepción con un
     * mensaje descriptivo.
     * En caso de que ocurra una excepción durante el proceso de eliminación, se lanza una excepción con un mensaje
     * descriptivo.
     *
     * @param id El ID del libro que se va a eliminar.
     * @return true si el libro se elimina con éxito.
     * @throws Exception Si el ID del libro no existe o si ocurre un error durante el proceso de eliminación.
     */
    @Override
    @Transactional(readOnly = false)
    public boolean deleteLibroById(long id) throws Exception {
        try {
            if (iLibroRepository.existsById(id)) {
                log.info("Eliminar Libro con ID: " + id);
                iLibroRepository.deleteById(id);
                return true;
            } else {
                log.error("¡No existe el ID del Autor: " + id);
                throw new Exception("No existe el ID del Autor");
            }
        } catch (Exception e) {
            log.error("Ocurrió un error al eliminar el Libro con ID " + id + ": " + e.getCause().toString());
            throw new Exception("Ocurrió un error al eliminar el Libro!");
        }
    }

}
