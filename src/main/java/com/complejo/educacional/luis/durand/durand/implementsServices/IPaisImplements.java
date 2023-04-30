/**
 * 
 */
package com.complejo.educacional.luis.durand.durand.implementsServices;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.complejo.educacional.luis.durand.durand.models.Pais;

/**
 * @author Pablo
 *
 */
public interface IPaisImplements {

	public Pais save(Pais pais) throws Exception;

	public Pais update(Long id, Pais pais) throws Exception;

	public List<Pais> findAllPaisSort(Sort sort) throws Exception;

	public Page<Pais> findAllPaisPageable(Pageable pageable) throws Exception;

	public Pais findById(long id) throws Exception;

	public void delete(long id) throws Exception;

}
