package com.complejo.educacional.luis.durand.durand.repositories;
//Import necesarias para la clase.
import com.complejo.educacional.luis.durand.durand.models.Genero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Pablo
 * @version 1.0
 * @create 29-03-2024 23:34
 * @project bibliotecaLuisDurandD
 */
public interface IGeneroRepository extends JpaRepository<Genero, Long> {

    //Seleccionar todas la instancias desde Genero la BD.
    @Query(value = "select g from Genero g")
    public List<Genero> findAllGeneroSort(Sort sort);

    //Contar la cantidad Géneros que vienen en la búsqueda y páginarlos.
    @Query(value = "select g from Genero g")
    public Page<Genero> findAllGeneroPage(Pageable pageable);

    //Buscar por ID.
    @Query(value = "select g from Genero g where id=:id")
    public Genero findByIdCategoria(long id );


}
