package com.complejo.educacional.luis.durand.durand.services;

import com.complejo.educacional.luis.durand.durand.implementsServices.ILibroImplements;
import com.complejo.educacional.luis.durand.durand.models.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Pablo
 */
public class LibroServices implements ILibroImplements {
    @Override
    public Libro createLibro(Libro libro) throws Exception {
        return null;
    }

    @Override
    public Libro udateLibro(Long id, Libro libro) throws Exception {
        return null;
    }

    @Override
    public List<Libro> findAllLibroSort(Sort sort) throws Exception {
        return null;
    }

    @Override
    public Page<Libro> findAllLibroPage(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public Libro findAllIdLibro(long id) throws Exception {
        return null;
    }

    @Override
    public Libro deleteLibroById(Long id) throws Exception {
        return null;
    }
}
