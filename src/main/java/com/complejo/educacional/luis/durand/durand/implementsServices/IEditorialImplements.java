package com.complejo.educacional.luis.durand.durand.implementsServices;

import com.complejo.educacional.luis.durand.durand.models.Editorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IEditorialImplements {
    /**
     *
     * @param editorial
     * @return
     * @throws Exception
     */
    public Editorial saveEditorial (Editorial editorial) throws Exception;

    /**
     *
     * @param id
     * @param editorial
     * @return
     * @throws Exception
     */
    public Editorial updateEditorial(Long id, Editorial editorial) throws Exception;

    /**
     *
     * @param sort
     * @return
     * @throws Exception
     */
    public List<Editorial> findAllEditorialSort(Sort sort) throws Exception;

    /**
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    public Page<Editorial> findAllEditorialPage(Pageable pageable) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Editorial findByIdEditorial(long id) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Object deleteEditorialById(Long id) throws Exception;

}
