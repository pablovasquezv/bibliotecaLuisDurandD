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

import com.complejo.educacional.luis.durand.durand.models.Pais;

/**
 * @author Pablo
 */
@Repository
public interface IPaisRepository extends JpaRepository<Pais, Long> {
    /**
     * Método de consulta JPQL personalizado para recuperar una lista de todos los países ordenados según el criterio
     * especificado.
     * Esta consulta devuelve una lista de todos los países, ordenados según el criterio proporcionado.
     *
     * @param sort El criterio de ordenación para los países.
     * @return Una lista de todos los países ordenados según el criterio especificado.
     */
    @Query(value = "select p from Pais p")
    public List<Pais> findAllPaisSort(Sort sort);


    /**
     * Método de consulta JPQL personalizado para recuperar una página de todos los países.
     * Esta consulta devuelve una página de todos los países, con la posibilidad de aplicar paginación y ordenación.
     * Utiliza una consulta de conteo separada para optimizar el rendimiento al recuperar el número total de países.
     *
     * @param pageable La información de paginación y ordenación para recuperar la página deseada de resultados.
     * @return Un Page<Pais> que contiene la página solicitada de todos los países.
     */
    @Query(value = "select p from Pais p",
            countQuery = "select count(p) from Pais p")
    public Page<Pais> findAllPaisPage(Pageable pageable);


    /**
     * Método de consulta JPQL personalizado para recuperar un país basado en su ID.
     * Esta consulta devuelve el país que coincide con el ID proporcionado.
     *
     * @param id El ID del país que se desea recuperar.
     * @return El país que coincide con el ID especificado.
     */
    @Query(value = "select p from Pais p where p.id_pais=:id")
    public Pais findById(@Param("id") long id);

}
