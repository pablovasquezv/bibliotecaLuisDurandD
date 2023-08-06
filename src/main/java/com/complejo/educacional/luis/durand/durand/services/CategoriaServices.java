package com.complejo.educacional.luis.durand.durand.services;

import com.complejo.educacional.luis.durand.durand.implementsServices.ICategoriaImplements;
import com.complejo.educacional.luis.durand.durand.models.Categoria;
import com.complejo.educacional.luis.durand.durand.repositories.ICategoriaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoriaServices implements ICategoriaImplements {
    @Autowired
    private ICategoriaRepository iCategoriaRepository;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     *
     * @param categoria
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public Categoria saveCategoria(Categoria categoria) throws Exception {
      Categoria  categoriaSave= null;
      try {
          categoriaSave= new Categoria();
          log.info("¡Inicio de la creación Categoría");
          categoriaSave= iCategoriaRepository.save(categoria);
          log.info("!Categoría creada Exitosamente!", objectMapper.writeValueAsString(categoriaSave));
      }catch (Exception e){
          log.error("¡Falló la creación de la Categoría!", e.getCause().toString());
      }
        return categoriaSave;
    }

    /**
     *
     * @param id
     * @param categoria
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public Categoria updateCategoria(Long id, Categoria categoria) throws Exception {
        Optional<Categoria> optionalCategoria= null;
        Categoria categoriaUpdate= null;
        try {
            log.info("¡Incio de Proceso de Actualización de Categoría", objectMapper.writeValueAsString(categoria));
            optionalCategoria= iCategoriaRepository.findById(id);
            categoriaUpdate= optionalCategoria.get();
            categoriaUpdate=iCategoriaRepository.save(categoria);
            log.info("¡Categoría Actualizada correctamente!",objectMapper.writeValueAsString(iCategoriaRepository.save(categoria)));
        }catch (Exception e){
            log.error("¡Error en la Actualización de la Categoría!", e.getCause().toString());
        }
        return categoriaUpdate;
    }

    /**
     *
     * @param sort
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public List<Categoria> findAllCategoriaSort(Sort sort) throws Exception {
        return iCategoriaRepository.findAllCategoriaSort(sort);
    }

    /**
     *
     * @param pageable
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Categoria> findAllCategoriaPage(Pageable pageable) throws Exception {
        return iCategoriaRepository.findAllCategoriaPage(pageable);
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public Categoria findByIdCategoria(long id) throws Exception {
        return iCategoriaRepository.findByIdCategoria(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public Categoria deleteEditorialById(long id) throws Exception {
        Categoria categoriaDelete= null;
        try {
            log.debug("¡Categorían eliminada!");
            iCategoriaRepository.deleteById(id);
        }catch (Exception e){
            log.error("¡No se elimino la Categoría!", e.getCause().toString());
        }
        return null;
    }


}
