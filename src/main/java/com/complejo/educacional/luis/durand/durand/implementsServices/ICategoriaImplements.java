package com.complejo.educacional.luis.durand.durand.implementsServices;

import com.complejo.educacional.luis.durand.durand.models.Categoria;
import com.complejo.educacional.luis.durand.durand.models.Editorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Pablo
 */
public interface ICategoriaImplements {
    /**
     *
     *
     * @param categoria
     * @return
     * @throws Exception
     */
    public Categoria saveCategoria(Categoria categoria) throws Exception;

    /**
     *
     * @param id
     * @param categoria
     * @return
     * @throws Exception
     */
    public Categoria updateCategoria(Long id, Categoria categoria) throws Exception;

    /**
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    public List<Categoria> findAllCategoriaPage(Pageable pageable)throws Exception;

    /**
     *
     * @param sort
     * @return
     * @throws Exception
     */
    public Page<Categoria> findAllCategoriaSor(Sort sort) throws Exception;
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Editorial findByIdCategoria(long id) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Editorial deleteEditorialById(long id) throws Exception;

}
