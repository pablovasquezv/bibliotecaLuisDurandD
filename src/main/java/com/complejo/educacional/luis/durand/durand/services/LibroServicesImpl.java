package com.complejo.educacional.luis.durand.durand.services;


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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = false)
    @Override
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
            log.info("Json de Salida =>", objectMapper.writeValueAsString(createLibro));
            log.info("----Fin de método Creación Libro----");

            return new LibroDTORequest(
                    createLibro.getTitulo_libro(),
                    createLibro.getAutor().getId_autor(),
                    createLibro.getCategoria().getId_categoria(),
                    createLibro.getEditorial().getId_editorial(),
                    createLibro.getGenero().getId_genero(),
                    createLibro.getPaginas_libro(),
                    createLibro.getEdicion_libro()
            );
        } catch (Exception e) {
            log.error("Ocurrió un error al guardar el Libro: " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al guardar el Libro!");
        }
    }

    @Override
    public LibroDTOResponse updateLibro(LibroDTOResponseUpdate libroDTOResponseUpdate) throws Exception {
        return null;
    }

    @Override
    public LibroDTOResponse findAllLibroSort(Sort sort) throws Exception {
        return null;
    }

    @Override
    public LibroDTOResponse findAllLibroPage(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public LibroDTOResponse findByIdLibro(long id) throws Exception {
        return null;
    }

    @Override
    public boolean deleteLibroById(long id) throws Exception {
        return false;
    }
}
