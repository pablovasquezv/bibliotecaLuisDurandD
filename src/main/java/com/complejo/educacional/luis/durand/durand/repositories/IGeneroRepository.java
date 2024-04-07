package com.complejo.educacional.luis.durand.durand.repositories;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.models.Genero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Pablo
 * @version 1.0
 * @create 29-03-2024 23:34
 * @project bibliotecaLuisDurandD
 */
public interface IGeneroRepository extends JpaRepository<Genero, Long> {

    /**
     * Método de consulta JPQL personalizado para recuperar una lista de todos los géneros ordenados según el criterio
     * especificado.
     * Esta consulta devuelve una lista de todos los géneros, ordenados según el criterio proporcionado.
     *
     * @param sort El criterio de ordenación para los géneros.
     * @return Una lista de todos los géneros ordenados según el criterio especificado.
     */
    @Query(value = "select g from Genero g")
    public List<Genero> findAllGeneroSort(Sort sort);

    /**
     * Método de consulta JPQL personalizado para recuperar una página de todos los géneros.
     * Esta consulta devuelve una página de todos los géneros, con la posibilidad de aplicar paginación y ordenación.
     * Utiliza una consulta de conteo separada para optimizar el rendimiento al recuperar el número total de géneros.
     *
     * @param pageable La información de paginación y ordenación para recuperar la página deseada de resultados.
     * @return Un Page<Genero> que contiene la página solicitada de todos los géneros.
     */
    @Query(value = "select g from Genero g",
            countQuery = "select count (g) from Genero g")
    public Page<Genero> findAllGeneroPage(Pageable pageable);


    /**
     * Método de consulta JPQL personalizado para recuperar un género basado en su ID.
     * Esta consulta devuelve el género que coincide con el ID proporcionado.
     *
     * @param id El ID del género que se desea recuperar.
     * @return El género que coincide con el ID especificado.
     */
    @Query(value = "select g from Genero g where g.id_genero=:id")
    public Genero findByIdGenero(@Param("id") long id);

    /**
     * Método de consulta personalizado para verificar si un nombre de género ya existe en la base de datos.
     * Esta consulta devuelve un valor booleano que indica si el nombre de género proporcionado ya existe en la base de datos.
     *
     * @param nombreGenero El nombre del género que se desea verificar.
     * @return true si el nombre de género ya existe en la base de datos, de lo contrario false.
     */
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM Genero g WHERE g.nombre_genero = :nombreGenero")
    boolean nombreGeneroYaExisteEnBaseDeDatos(@Param("nombreGenero") String nombreGenero);

}
