package com.complejo.educacional.luis.durand.durand.repositories;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.models.Editorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEditorialRepository extends JpaRepository<Editorial, Long> {
    /**
     * Método de consulta JPQL personalizado para recuperar una lista de todas las editoriales ordenadas según el
     * criterio especificado.
     * Esta consulta devuelve una lista de todas las editoriales, ordenadas según el criterio proporcionado.
     *
     * @param sort El criterio de ordenación para las editoriales.
     * @return Una lista de todas las editoriales ordenadas según el criterio especificado.
     */
    @Query(value = "select e from Editorial e")
    public List<Editorial> findAllEditorialSort(Sort sort);

    /**
     * Método de consulta JPQL personalizado para recuperar una página de todas las editoriales.
     * Esta consulta devuelve una página de todas las editoriales, con la posibilidad de aplicar paginación y ordenación.
     * Utiliza una consulta de conteo separada para optimizar el rendimiento al recuperar el número total de editoriales.
     *
     * @param pageable La información de paginación y ordenación para recuperar la página deseada de resultados.
     * @return Un Page<Editorial> que contiene la página solicitada de todas las editoriales.
     */
    @Query(value = "select e from Editorial e",
            countQuery = "select count(e) from Editorial e")
    public Page<Editorial> findAllEditorialPage(Pageable pageable);


    /**
     * Método de consulta JPQL personalizado para recuperar una editorial basada en su ID.
     * Esta consulta devuelve la editorial que coincide con el ID proporcionado.
     *
     * @param id El ID de la editorial que se desea recuperar.
     * @return La editorial que coincide con el ID especificado.
     */
    @Query(value = "select e from Editorial e where e.id_editorial=:id")
    public Editorial findByIdEditorial(@Param("id") long id);


}
