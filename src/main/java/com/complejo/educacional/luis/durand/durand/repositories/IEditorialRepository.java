package com.complejo.educacional.luis.durand.durand.repositories;

import com.complejo.educacional.luis.durand.durand.models.Editorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEditorialRepository extends JpaRepository <Editorial, Long>{
    // Seleccionar todas la instancias desde la BD

    @Query(value = "select e from Editorial a")
    public List<Editorial> findAllEditorialSort(Sort sort);

    // Contar la cantidad de Editorial que vienen en la búsqueda y páginarlos
    @Query(value = "select a from Editorial",
            countQuery = "select count(e) from Editorial")
    public Page<Editorial> findAllEditorialPage(Pageable pageable);

    //Buscar por id Editorial
    @Query(value = "select e from Editorial e where e.id=:id")
    public Editorial findByIdEditorial(long id);

}
