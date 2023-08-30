/**
 * 
 */
package com.complejo.educacional.luis.durand.durand.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complejo.educacional.luis.durand.durand.services.implementsServices.IPaisServices;
import com.complejo.educacional.luis.durand.durand.models.Pais;
import com.complejo.educacional.luis.durand.durand.repositories.IPaisRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Pablo
 *
 */
@Service
@Slf4j
public class PaisServicesImpl implements IPaisServices {

	@Autowired
	private IPaisRepository iPaisRepository;

	/**
	 *
	 * @param pais
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false)
	public Pais save(Pais pais) throws Exception {
		// TODO Auto-generated method stub
		Pais createPais= null;
		try {
			createPais = iPaisRepository.save(pais);
			return createPais;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("¡Error al Crear el país!", e.getCause().toString());
		}
		return createPais;
	}

	/**
	 *
	 * @param id
	 * @param pais
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false)
	public Pais update(Long id, Pais pais) throws Exception {
		// TODO Auto-generated method stub
		Pais paisUpdate =null;
		try {
			Optional<Pais> paisOptional = iPaisRepository.findById(id);
			paisUpdate = paisOptional.get();
			paisUpdate = iPaisRepository.save(pais);
			return paisUpdate;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("¡Error al Actualizar el país!", e.getCause().toString());
		}
		return paisUpdate;
	}

	/**
	 *
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Pais> findAllPaisSort(Sort sort) throws Exception {
		// TODO Auto-generated method stub
		return iPaisRepository.findAllPaisSort(sort);
	}

	/**
	 *
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Pais> findAllPaisPageable(Pageable pageable) throws Exception {
		// TODO Auto-generated method stub
		return iPaisRepository.findAllPaisPage(pageable);
	}

	/**
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public Pais findById(long id) throws Exception {
		// TODO Auto-generated method stub
		return iPaisRepository.findById(id);
	}

	/**
	 *
	 * @param id
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			if (iPaisRepository.existsById(id)) {
				iPaisRepository.deleteById(id);
			} else {
				log.error("¡No exite el Id del País!");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getCause());
		}
	}

}
