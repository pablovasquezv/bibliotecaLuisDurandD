package com.complejo.educacional.luis.durand.durand.repositories;

import com.complejo.educacional.luis.durand.durand.models.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version 1.0
 * @autor Pablo
 * @creates 09-05-2025 23:48
 * @proyect bibliotecaLuisDurandD
 */
@Repository
public interface ILibroRepository extends JpaRepository<Libro, Long> {
    // Seleccionar todas la instancias desde la BD y haciendo un Join con la tabla
    @Query(value = "select l from Libro l " +
            "left join fetch l.autor " +
            "left join fetch l.categoria " +
            "left join fetch l.editorial " +
            "left join fetch l.genero")
    public List<Libro> findAllLibroSort(Sort sort);

    // Contar la cantidad de Libro que vienen en la búsqueda y páginarlos
    @Query(value = "select l from Libro l " +
            "left join fetch l.autor " +
            "left join fetch l.categoria " +
            "left join fetch l.editorial " +
            "left join fetch l.genero",
            countQuery = "select count(l) from Libro l " +
                    "left join l.autor " +
                    "left join l.categoria " +
                    "left join l.editorial " +
                    "left join l.genero")
    public Page<Libro> findAllLibroPage(Pageable pageable);

    //Buscar por id
    @Query(value = "select l from Libro l " +
            "left join fetch l.autor " +
            "left join fetch l.categoria " +
            "left join fetch l.editorial " +
            "left join fetch l.genero " +
            "where l.id=:id")
    public Libro findByIdLibro(long id);
}
