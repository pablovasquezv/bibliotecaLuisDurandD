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

import com.complejo.educacional.luis.durand.durand.models.Autor;

/**
 * @author Pablo
 *
 */
@Repository
public interface IAutorRepository extends JpaRepository<Autor, Long> {
	// Seleccionar todas la instancias desde la BD y haciendo un Join con la tabla
	@Query(value = "select a from Autor a left join fetch a.pais")
	public List<Autor> findAllAutorSort(Sort sort);

	// Contar la cantidad de Autor que vienen en la búsqueda y páginarlos
	@Query(value = "select a from Autor a left join fetch a.pais", 
			countQuery = "select count(a) from Autor a left join a.pais")
	public Page<Autor> findAllAutorPage(Pageable pageable);

	//Buscar por id
	@Query(value = "select a from Autor a left join a.pais where a.id=:id")
	public Autor findByIdAutor(long id);
	
}
