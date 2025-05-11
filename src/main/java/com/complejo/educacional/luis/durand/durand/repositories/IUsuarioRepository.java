package com.complejo.educacional.luis.durand.durand.repositories;

import com.complejo.educacional.luis.durand.durand.models.Usuario;
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
 * @creates 10-05-2025 17:07
 * @proyect bibliotecaLuisDurandD
 */
@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {
    // Seleccionar todas la instancias desde Usuario la BD.
    @Query(value = "SELECT u FROM Usuario u")
    List<Usuario> findAllUsuarioSort(Sort sort);

    // Contar la cantidad de Categoria que vienen en la búsqueda y páginarlos.
    @Query(value = "SELECT u FROM Usuario u", countQuery = "SELECT COUNT (u) FROM Usuario u")
    Page<Usuario> findAllPageUsuario(Pageable pageable);

    //Buscar por id.
    @Query(value = "SELECT u FROM Usuario u WHERE id=:id")
    Usuario findByIdUsuario(long id);

}
