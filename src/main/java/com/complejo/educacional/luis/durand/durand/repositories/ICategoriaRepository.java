package com.complejo.educacional.luis.durand.durand.repositories;
//Import necesarias para la clase.

import com.complejo.educacional.luis.durand.durand.models.Categoria;
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
 * @create 05-08-2023 23:34
 * @project bibliotecaLuisDurandD
 */
@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
    /**
     * Método de consulta JPQL personalizado para recuperar una lista de todas las categorías ordenadas según el
     * criterio especificado.
     * Esta consulta devuelve una lista de todas las categorías, ordenadas según el criterio proporcionado.
     *
     * @param sort El criterio de ordenación para las categorías.
     * @return Una lista de todas las categorías ordenadas según el criterio especificado.
     */
    @Query(value = "select c from Categoria c")
    public List<Categoria> findAllCategoriaSort(Sort sort);

    // Contar la cantidad de Categoria que vienen en la búsqueda y páginarlos.

    /**
     * Método de consulta JPQL personalizado para recuperar una página de todas las categorías.
     * Esta consulta devuelve una página de todas las categorías, con la posibilidad de aplicar paginación y ordenación.
     * Utiliza una consulta de conteo separada para optimizar el rendimiento al recuperar el número total de categorías.
     *
     * @param pageable La información de paginación y ordenación para recuperar la página deseada de resultados.
     * @return Un Page<Categoria> que contiene la página solicitada de todas las categorías.
     */
    @Query(value = "select c from Categoria c",
            countQuery = "select count(c) from Categoria c")
    public Page<Categoria> findAllCategoriaPage(Pageable pageable);


    //Buscar por id.

    /**
     * Método de consulta JPQL personalizado para recuperar una categoría basada en su ID.
     * Esta consulta devuelve la categoría que coincide con el ID proporcionado.
     *
     * @param id El ID de la categoría que se desea recuperar.
     * @return La categoría que coincide con el ID especificado.
     */
    @Query(value = "select c from Categoria c where c.id_categoria=:id")
    public Categoria findByIdCategoria(@Param("id") long id);


}
