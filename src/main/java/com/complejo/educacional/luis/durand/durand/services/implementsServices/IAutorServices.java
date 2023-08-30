/**
 * 
 */
package com.complejo.educacional.luis.durand.durand.services.implementsServices;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.complejo.educacional.luis.durand.durand.models.Autor;

/**
 * @author Pablo
 *
 */
public interface IAutorServices {
	/**
	 *
	 * @param autor
	 * @return
	 * @throws Exception
	 */
	public Autor saveAutor(Autor autor) throws Exception;

	/**
	 *
	 * @param id
	 * @param autor
	 * @return
	 * @throws Exception
	 */

	public Autor updateAutor(Long id, Autor autor) throws Exception;

	/**
	 *
	 * @param sort
	 * @return
	 * @throws Exception
	 */

	public List<Autor> findAllAutorSort(Sort sort) throws Exception;

	/**
	 *
	 * @param pageable
	 * @return
	 * @throws Exception
	 */

	public Page<Autor> findAllAutorPage(Pageable pageable) throws Exception;

	/**
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */

	public Autor findByIdAutor(long id) throws Exception;

	/**
	 *
	 * @param id
	 * @throws Exception
	 */

	public void deleteAutorById(long id) throws Exception;
}