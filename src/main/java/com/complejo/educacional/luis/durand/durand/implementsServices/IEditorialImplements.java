package com.complejo.educacional.luis.durand.durand.implementsServices;

import com.complejo.educacional.luis.durand.durand.models.Editorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IEditorialImplements {

    public Editorial saveEditorial (Editorial editorial) throws Exception;

    public Editorial updateEditorial(Long id, Editorial editorial) throws Exception;

    public List<Editorial> findAllEditorialSort(Sort sort) throws Exception;

    public Page<Editorial> findAllEditorialPage(Pageable pageable) throws Exception;

    public Editorial findByIdEditorial(long id) throws Exception;

    public Object deleteEditorialById(Long id) throws Exception;

}
