/**
 * 
 */
package com.complejo.educacional.luis.durand.durand.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.complejo.educacional.luis.durand.durand.models.Pais;

/**
 * @author Pablo
 *
 */
@Repository
public interface IPaisRepository extends JpaRepository<Pais, Long> {
	// Seleccionar todas la instancias desde la BD y haciendo un Join con la tabla
	@Query(value = "select p from Pais p")
	public List<Pais> findAllPaisSort(Sort sort);

	// Contar la cantidad de Paises que vienen en la búsqueda y páginarlos
	@Query(value = "select p from Pais p", 
			countQuery = "select count(p) from Pais p")
	public Page<Pais> findAllPaisPage(Pageable pageable);

	//Buscar por id
	@Query(value = "select p from Pais p where id=:id")
	public Pais findById(long id);
}
