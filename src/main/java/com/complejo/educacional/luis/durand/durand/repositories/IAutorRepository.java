package com.complejo.educacional.luis.durand.durand.repositories;
//Import necesarias para la clase.

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.complejo.educacional.luis.durand.durand.models.Autor;

/**
 * @author Pablo
 */
@Repository
public interface IAutorRepository extends JpaRepository<Autor, Long> {
    /**
     * Método de consulta JPQL personalizado para recuperar una lista de autores junto con su país asociado.
     * Esta consulta carga de manera inmediata los datos del país asociado en una sola consulta, evitando consultas adicionales para datos relacionados.
     * Utiliza la cláusula left join fetch para cargar eficientemente el país asociado, asegurando una vista completa de los autores y sus países.
     *
     * @param sort La información de clasificación para ordenar la lista de autores.
     * @return Una lista de autores con el país asociado cargado de manera inmediata.
     */
    @Query(value = "select a from Autor a left join fetch a.pais")
    public List<Autor> findAllAutorSort(Sort sort);

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.pais")
    public List<Autor> findAllAuthorsWithCountrySort(Sort sort);

    /**
     * Método de consulta JPQL personalizado para recuperar una página de autores junto con sus países asociados.
     * Esta consulta carga de manera inmediata los datos de los países asociados en una sola consulta, evitando consultas adicionales para datos relacionados.
     * Utiliza la cláusula left join fetch para cargar eficientemente los países asociados, asegurando una vista completa de los autores y sus países.
     *
     * @param pageable La información de paginación para recuperar la página deseada de resultados.
     * @return Un Page<Autor> que contiene la página solicitada de autores con los países asociados cargados de manera inmediata.
     */
    @Query(value = "SELECT a FROM Autor a LEFT JOIN FETCH a.pais",
            countQuery = "select count(a) from Autor a left join a.pais")
    public Page<Autor> findAllAuthorsWithCountryPageable(Pageable pageable);

    // Contar la cantidad de Autor que vienen en la búsqueda y páginarlos
    @Query(value = "select a from Autor a left join fetch a.pais",
            countQuery = "select count(a) from Autor a left join a.pais")
    public Page<Autor> findAllAutorPage(Pageable pageable);

    /**
     * Método de consulta JPQL personalizado para recuperar un autor por su ID junto con su país asociado.
     * Esta consulta carga de manera inmediata los datos del país asociado en una sola consulta, evitando consultas adicionales para datos relacionados.
     * Utiliza la cláusula left join para recuperar el autor junto con su país asociado basado en el ID proporcionado.
     *
     * @param id El ID del autor que se desea recuperar.
     * @return El autor con el ID especificado junto con su país asociado.
     */
    @Query("SELECT a FROM Autor a LEFT JOIN a.pais p WHERE a.id_autor = :id")
    public Autor findAuthorWithCountryById(@Param("id") long id);


    //Buscar por id
    @Query(value = "select a from Autor a left join a.pais where a.id_autor=:id")
    public Autor findByIdAutor(long id);

}
