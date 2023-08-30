/**
 * 
 */
package com.complejo.educacional.luis.durand.durand.services.implementsServices;

import java.util.List;

import com.complejo.educacional.luis.durand.durand.dto.PaisDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.PaisDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.PaisDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Pablo
 *
 */
public interface IPaisServices {
	/**
	 *
	 * @param paisDTORequest
	 * @return
	 * @throws Exception
	 */
	public PaisDTORequest createPais(PaisDTORequest paisDTORequest) throws Exception;

	/**
	 *
	 * @param id
	 * @param paisDTORequestUpdate
	 * @return
	 * @throws Exception
	 */

	public PaisDTOResponse updatePais(Long id, PaisDTORequestUpdate paisDTORequestUpdate) throws Exception;

	/**
	 *
	 * @param sort
	 * @return
	 * @throws Exception
	 */

	public List<PaisDTOResponse> findAllPaisSort(Sort sort) throws Exception;

	/**
	 *
	 * @param pageable
	 * @return
	 * @throws Exception
	 */

	public Page<PaisDTOResponse> findAllPaisPageable(Pageable pageable) throws Exception;

	/**
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */

	public PaisDTOResponse findById(long id) throws Exception;

	/**
	 *
	 * @param id
	 * @throws Exception
	 */

	public void deletePaisById(long id) throws Exception;

}
