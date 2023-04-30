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

import com.complejo.educacional.luis.durand.durand.controllers.PaisRestController;
import com.complejo.educacional.luis.durand.durand.implementsServices.IPaisImplements;
import com.complejo.educacional.luis.durand.durand.models.Pais;
import com.complejo.educacional.luis.durand.durand.repositories.IPaisRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Pablo
 *
 */
@Service
@Slf4j
public class PaisServices implements IPaisImplements {

	@Autowired
	private IPaisRepository iPaisRepository;

	@Override
	@Transactional(readOnly = false)
	public Pais save(Pais pais) throws Exception {
		// TODO Auto-generated method stub
		try {
			pais = iPaisRepository.save(pais);
			return pais;
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getCause());
		}
	}

	@Override
	@Transactional(readOnly = false)
	public Pais update(Long id, Pais pais) throws Exception {
		// TODO Auto-generated method stub
		try {
			Optional<Pais> paisOptional = iPaisRepository.findById(id);
			Pais paisUpdate = paisOptional.get();
			paisUpdate = iPaisRepository.save(pais);
			return paisUpdate;
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getCause());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pais> findAllPaisSort(Sort sort) throws Exception {
		// TODO Auto-generated method stub
		return iPaisRepository.findAllPaisSort(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Pais> findAllPaisPageable(Pageable pageable) throws Exception {
		// TODO Auto-generated method stub
		return iPaisRepository.findAllPaisPage(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Pais findById(long id) throws Exception {
		// TODO Auto-generated method stub
		return iPaisRepository.findById(id);
	}

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
