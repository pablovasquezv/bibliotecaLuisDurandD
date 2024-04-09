package com.complejo.educacional.luis.durand.durand.repositories;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.models.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Pablo
 * @version 1.0
 * @create 06-04-2024 15:29
 * @project bibliotecaLuisDurandD
 */
@Repository
public interface ILibroRepository extends JpaRepository<Libro, Long> {
    /**
     * Método de consulta JPQL personalizado para recuperar una lista de libros junto con sus entidades asociadas
     * (Autor, Categoría, Editorial, Género).
     * Esta consulta carga de manera inmediata los datos de las entidades relacionadas en una sola consulta, evitando
     * consultas adicionales para datos relacionados.
     * Utiliza la cláusula left join fetch para cargar eficientemente las entidades asociadas, asegurando una vista
     * completa de los libros y sus relaciones.
     *
     * @param sort La información de clasificación para ordenar la lista de libros.
     * @return Una lista de libros con entidades asociadas cargadas de manera inmediata.
     */
    @Query(value = "select l from Libro l " +
            "left join fetch l.autor " +
            "left join fetch l.categoria " +
            "left join fetch l.editorial " +
            "left join fetch l.genero")
    public List<Libro> findAllLibroSort(Sort sort);

    /**
     * Método de consulta JPQL personalizado para recuperar una página de libros junto con sus entidades asociadas
     * (Autor, Categoría, Editorial, Género).
     * Este método carga eficientemente los datos necesarios en una sola consulta, evitando consultas adicionales para
     * datos relacionados.
     * Utiliza la cláusula left join fetch para cargar de manera inmediata las entidades asociadas, asegurando una vista
     * completa de los libros y sus relaciones.
     * Se proporciona countQuery para contar de manera eficiente el número total de libros, lo que respalda la paginación.
     * El método devuelve los resultados como un objeto Page<Libro>, facilitando la paginación y navegación a través del
     * conjunto de resultados.
     *
     * @param pageable La información de paginación para recuperar la página deseada de resultados.
     * @return Un Page<Libro> que contiene la página solicitada de libros con entidades asociadas.
     */
    @Query(value = "select l from Libro l " +
            "left join fetch l.autor " +
            "left join fetch l.categoria " +
            "left join fetch l.editorial " +
            "left join fetch l.genero",
            countQuery = "select count (l) from Libro l " +
                    "left join l.autor " +
                    "left join l.categoria " +
                    "left join l.editorial " +
                    "left join l.genero")
    public Page<Libro> findAllAutorPage(Pageable pageable);

    /**
     * Método de consulta JPQL personalizado para recuperar un libro junto con su autor, categoría, editorial y género
     * asociados basado en el ID del libro.
     * Esta consulta carga de manera inmediata los datos del autor, categoría, editorial y género asociados en una sola
     * consulta, evitando consultas adicionales para datos relacionados.
     * Utiliza múltiples cláusulas left join para recuperar el libro junto con su autor, categoría, editorial y género
     * asociados basados en el ID proporcionado.
     *
     * @param id El ID del libro que se desea recuperar.
     * @return El libro con el ID especificado junto con su autor, categoría, editorial y género asociados.
     */
    @Query(value = "select l from Libro l " +
            "left join l.autor " +
            "left join l.categoria " +
            "left join l.editorial " +
            "left join l.genero " +
            "where l.id_libro = :id")
    public Libro findByLibroAndAutorAndCategoriaAndEditorialAndGenero(@Param("id") long id);

}
