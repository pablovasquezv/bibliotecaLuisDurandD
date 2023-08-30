package com.complejo.educacional.luis.durand.durand.services;

import com.complejo.educacional.luis.durand.durand.dto.CategoriaDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.CategoriaDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.CategoriaRequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.EditorialDTOResponse;
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
     *
     * @param categoriaDTORequest
     * @return categoriaDTORequest
     * @throws Exception
     */

    @Override
    @Transactional(readOnly = false)
    public CategoriaDTORequest saveCategoria(CategoriaDTORequest categoriaDTORequest) throws Exception {

        try {
            Categoria categoriaCreate= new Categoria(
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
        }catch (Exception e){
            log.error("Falló la creación de la Categoria =>", e.getCause().toString());
        }
        return categoriaDTORequest;
    }

    @Override
    @Transactional(readOnly = false)
    public CategoriaDTOResponse updateCategoria(CategoriaRequestUpdate categoriaRequestUpdate) throws Exception {
        Optional<Categoria> optionalCategoria=null;
        EditorialDTOResponse editorialDTOResponse= null;
        Categoria categoriaUpdate= new Categoria(
                categoriaRequestUpdate.getId_categoria(),
                categoriaRequestUpdate.getNombre_categoria(),
                categoriaRequestUpdate.getDescripcion_categoria(),
                categoriaRequestUpdate.getCreateAt(),
                categoriaRequestUpdate.getUdpdateAt()
        );
        try {
optionalCategoria=iCategoriaRepository.findById()
        }catch (Exception e){
            log.error("Falló la creación de la Categoria =>", e.getCause().toString());
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTOResponse> findAllCategoriaSort(Sort sort) throws Exception {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaDTOResponse> findAllCategoriaPage(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTOResponse findByIdCategoria(long id) throws Exception {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public Object deleteCategoriaById(Long id) throws Exception {
        return null;
    }
}
