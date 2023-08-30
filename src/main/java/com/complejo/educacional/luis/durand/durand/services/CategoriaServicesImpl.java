package com.complejo.educacional.luis.durand.durand.services;

import com.complejo.educacional.luis.durand.durand.dto.CategoriaDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.CategoriaDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.CategoriaRequestUpdate;
import com.complejo.educacional.luis.durand.durand.models.Categoria;
import com.complejo.educacional.luis.durand.durand.repositories.ICategoriaRepository;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.ICategoriaServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoriaServicesImpl implements ICategoriaServices {
    @Autowired
    private ICategoriaRepository iCategoriaRepository;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @param categoriaDTORequest
     * @return categoriaDTORequest
     * @throws Exception
     */

    @Override
    @Transactional(readOnly = false)
    public CategoriaDTORequest saveCategoria(CategoriaDTORequest categoriaDTORequest) throws Exception {

        try {
            Categoria categoriaCreate = new Categoria(
                    null,
                    categoriaDTORequest.getNombre_categoria(),
                    categoriaDTORequest.getDescripcion_categoria(),
                    categoriaDTORequest.getCreateAt(),
                    categoriaDTORequest.getCreateAt()
            );
            log.info("¡Creación de Categoría!");
            iCategoriaRepository.save(categoriaCreate);
            log.info("Categoría creda", objectMapper.writeValueAsString(categoriaDTORequest));
            return categoriaDTORequest;
        } catch (Exception e) {
            log.error("Falló la creación de la Categoria =>", e.getCause().toString());
        }
        return categoriaDTORequest;
    }

    /**
     * @param id
     * @param categoriaRequestUpdate
     * @return categoriaDTOResponse
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public CategoriaDTOResponse updateCategoria(Long id, CategoriaRequestUpdate categoriaRequestUpdate) throws Exception {
        Optional<Categoria> optionalCategoria = null;
        CategoriaDTOResponse categoriaDTOResponse = null;
        Categoria categoriaUpdate = new Categoria(
                categoriaRequestUpdate.getId_categoria(),
                categoriaRequestUpdate.getNombre_categoria(),
                categoriaRequestUpdate.getDescripcion_categoria(),
                categoriaRequestUpdate.getCreateAt(),
                categoriaRequestUpdate.getUdpdateAt()
        );
        try {
            optionalCategoria = iCategoriaRepository.findById(id);
            if (optionalCategoria.isPresent()) {
                categoriaUpdate = iCategoriaRepository.save(categoriaUpdate);
                log.info("Categoría Actualizada!", objectMapper.writeValueAsString(categoriaRequestUpdate));
            } else {
                log.error("Falló la actualización de la Categoría =>");
            }
            categoriaDTOResponse = new CategoriaDTOResponse(
                    categoriaUpdate.getId_categoria(),
                    categoriaUpdate.getNombre_categoria(),
                    categoriaUpdate.getDescripcion_categoria(),
                    categoriaUpdate.getCreateAt(),
                    categoriaUpdate.getUdpdateAt()
            );
            return categoriaDTOResponse;
        } catch (Exception e) {
            log.error("Falló la creación de la Categoria =>", e.getCause().toString());
        }
        return categoriaDTOResponse;
    }

    /**
     * @param sort
     * @return categoriaDTOResponses
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTOResponse> findAllCategoriaSort(Sort sort) throws Exception {
        List<CategoriaDTOResponse> categoriaDTOResponses = new ArrayList<CategoriaDTOResponse>();
        for (Categoria categoria : iCategoriaRepository.findAllCategoriaSort(sort)) {
            categoriaDTOResponses.add(
                    new CategoriaDTOResponse(
                            categoria.getId_categoria(),
                            categoria.getNombre_categoria(),
                            categoria.getDescripcion_categoria(),
                            categoria.getCreateAt(),
                            categoria.getUdpdateAt()
                    )
            );
        }
        return categoriaDTOResponses;
    }

    /**
     *
     * @param pageable
     * @return categoriaDTOResponses
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaDTOResponse> findAllCategoriaPage(Pageable pageable) throws Exception {
        List<CategoriaDTOResponse> categoriaDTOResponses = new ArrayList<CategoriaDTOResponse>();
        for (Categoria categoria : iCategoriaRepository.findAllCategoriaPage(pageable)) {
            categoriaDTOResponses.add(
                    new CategoriaDTOResponse(
                            categoria.getId_categoria(),
                            categoria.getNombre_categoria(),
                            categoria.getDescripcion_categoria(),
                            categoria.getCreateAt(),
                            categoria.getUdpdateAt()
                    )
            );
        }
        return (Page<CategoriaDTOResponse>) categoriaDTOResponses;
    }

    /**
     *
     * @param id
     * @return CategoriaDTOResponse
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public CategoriaDTOResponse findByIdCategoria(long id) throws Exception {
        Categoria categoriaId = iCategoriaRepository.findByIdCategoria(id);
        if (categoriaId == null) {
            throw new Exception("¡Categoría not found!");
        }
        return new CategoriaDTOResponse(
                categoriaId.getId_categoria(),
                categoriaId.getNombre_categoria(),
                categoriaId.getDescripcion_categoria(),
                categoriaId.getCreateAt(),
                categoriaId.getUdpdateAt()
        );
    }

    /**
     *
     * @param id
     * @return eliminarCategoria
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public Object deleteCategoriaById(Long id) throws Exception {
        Object eliminarCategoria= null;
        try {
            log.info("¡Eliminar Categoría");
        }catch (Exception e){
            log.error("¡Falló la eliminación de la Categoría"+e.getCause().toString());
        }
        return eliminarCategoria;
    }
}
