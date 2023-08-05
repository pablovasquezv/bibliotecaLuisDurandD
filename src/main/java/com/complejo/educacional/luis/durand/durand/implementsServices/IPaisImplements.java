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
	/**
	 *
	 * @param pais
	 * @return
	 * @throws Exception
	 */
	public Pais save(Pais pais) throws Exception;

	/**
	 *
	 * @param id
	 * @param pais
	 * @return
	 * @throws Exception
	 */

	public Pais update(Long id, Pais pais) throws Exception;

	/**
	 *
	 * @param sort
	 * @return
	 * @throws Exception
	 */

	public List<Pais> findAllPaisSort(Sort sort) throws Exception;

	/**
	 *
	 * @param pageable
	 * @return
	 * @throws Exception
	 */

	public Page<Pais> findAllPaisPageable(Pageable pageable) throws Exception;

	/**
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */

	public Pais findById(long id) throws Exception;

	/**
	 *
	 * @param id
	 * @throws Exception
	 */

	public void delete(long id) throws Exception;

}
