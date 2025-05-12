package com.complejo.educacional.luis.durand.durand.repositories;

import com.complejo.educacional.luis.durand.durand.models.Libro;
import com.complejo.educacional.luis.durand.durand.models.Prestamo;
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
 * @creates 10-05-2025 22:38
 * @proyect bibliotecaLuisDurandD
 */
@Repository
public interface IPrestamoRepository extends JpaRepository<Prestamo, Long> {
    // Seleccionar todas la instancias desde la BD y haciendo un Join con la tabla
    @Query(value = "SELECT p FROM Prestamo p " +
            "LEFT JOIN FETCH p.usuario " +
            "LEFT JOIN FETCH p.libro")
    List<Prestamo> findAllPrestamosSort(Sort sort);

    /**
     * Contar la cantidad de Libro que vienen en la búsqueda y páginarlos
     * Eliminar los LEFT JOIN FETCH de la countQuery. La consulta de conteo debe ser lo más simple posible para un
     * mejor rendimiento.
     */
    @Query(value = "SELECT p FROM Prestamo p " +
            "LEFT JOIN FETCH p.usuario " +
            "LEFT JOIN FETCH p.libro",
            countQuery = "SELECT COUNT(p) FROM Prestamo p")
    Page<Prestamo> findAllPrestamoPage(Pageable pageable);

    //Buscar por id
    @Query(value = "SELECT p FROM Prestamo p " +
            "LEFT JOIN FETCH p.usuario " +
            "LEFT JOIN FETCH p.libro " +
            "WHERE p.id=:id")
    Prestamo findByIdPrestamo(long id);
}
