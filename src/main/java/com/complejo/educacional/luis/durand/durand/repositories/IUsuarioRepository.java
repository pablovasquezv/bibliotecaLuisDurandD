package com.complejo.educacional.luis.durand.durand.repositories;
//Import necesarias para la Interface
import com.complejo.educacional.luis.durand.durand.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @autor Pablo
 * @create 12-04-2024 20:56
 * @project bibliotecaLuisDurandD
 * @Version 1.0
 */
@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Método que realiza una consulta para obtener una lista de usuarios ordenada según el criterio especificado.
     *
     * @param sort Criterio de ordenación para la consulta.
     * @return Lista de usuarios ordenada según el criterio especificado.
     */
    @Query(value = "SELECT u FROM Usuario u")
    public List<Usuario> findUsuarioBySort(Sort sort);


    /**
     * Método que realiza una consulta para obtener una página de usuarios, con la posibilidad de especificar la
     * paginación y el orden.
     *
     * @param pageable Información de paginación y ordenación para la consulta.
     * @return Página de usuarios según los parámetros especificados.
     */
    @Query(value = "SELECT u FROM Usuario u", countQuery = "SELECT COUNT(u) FROM Usuario u")
    public Page<Usuario> findAllUsuarioPage(Pageable pageable);

    /**
     * Método que realiza una consulta para obtener un usuario por su identificador.
     *
     * @param id Identificador del usuario.
     * @return Usuario correspondiente al identificador especificado.
     */
    @Query(value = "SELECT u FROM Usuario u WHERE u.id = :id")
    public Usuario findByIdUsuario(@Param("id") long id);

}
