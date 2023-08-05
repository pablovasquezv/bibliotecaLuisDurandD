package com.complejo.educacional.luis.durand.durand.repositories;

import com.complejo.educacional.luis.durand.durand.models.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
    // Seleccionar todas la instancias desde la BD
    @Query(value = "select c from categoria c")
    public List<Categoria> findAllCategoriaSort(Sort sort);

    // Contar la cantidad de Paises que vienen en la búsqueda y páginarlos
    @Query(value = "select c from categoria c",
    countQuery = "select count(c) from categoria c")
    public Page<Categoria> findAllCategoriaPage(Pageable pageable);

    //Buscar por id
    @Query(value = "select c from categoria c where id=:id")
    public Categoria findByIdCategoria(long id);
}
