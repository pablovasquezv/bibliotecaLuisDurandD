package com.complejo.educacional.luis.durand.durand.implementsServices;

import com.complejo.educacional.luis.durand.durand.models.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Pablo
 */
public interface ILibroImplements {
    /**
     *
     * @param libro
     * @return
     * @throws Exception
     */
    public Libro createLibro( Libro libro)throws Exception;

    /**
     *
     * @param id
     * @param libro
     * @return
     * @throws Exception
     */
    public Libro udateLibro(Long id, Libro libro) throws Exception;

    /**
     *
     * @param sort
     * @return
     * @throws Exception
     */
    public List<Libro> findAllLibroSort(Sort sort)throws Exception;

    /**
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    public Page<Libro> findAllLibroPage(Pageable pageable) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Libro findAllIdLibro(long id)throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Libro deleteLibroById(Long id)throws Exception;


}
