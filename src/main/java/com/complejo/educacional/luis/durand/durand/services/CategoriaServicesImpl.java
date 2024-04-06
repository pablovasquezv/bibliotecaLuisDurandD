package com.complejo.educacional.luis.durand.durand.services;
//Import necesarios para la clase.

import com.complejo.educacional.luis.durand.durand.dto.categoria.CategoriaDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.categoria.CategoriaDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.categoria.CategoriaRequestUpdate;
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
     * Método que guarda una nueva categoría con la información proporcionada en categoriaDTORequest.
     *
     * @param categoriaDTORequest La información de la nueva categoría a guardar.
     * @return Un objeto CategoriaDTORequest con la información de la categoría guardado.
     * @throws Exception Si ocurre un error durante el proceso de guardado de la categoría.
     */
    @Override
    @Transactional(readOnly = false)
    public CategoriaDTORequest saveCategoria(CategoriaDTORequest categoriaDTORequest) throws Exception {

        try {
            Categoria categoriaCreate = new Categoria(
                    null,
                    categoriaDTORequest.getNombre_categoria(),
                    categoriaDTORequest.getDescripcion_categoria()
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
     * Método que actualiza una categoría según su ID con la información proporcionada en categoriaRequestUpdate.
     *
     * @param id                     El ID de la categoría a actualizar.
     * @param categoriaRequestUpdate La información actualizada de la categoría.
     * @return Un objeto CategoriaRequestUpdate con la información actualizada de la categoría.
     * @throws Exception Si ocurre un error durante el proceso de actualización de la categoría.
     */
    @Override
    @Transactional(readOnly = false)
    public CategoriaDTOResponse updateCategoria(Long id, CategoriaRequestUpdate categoriaRequestUpdate) throws Exception {
        Optional<Categoria> optionalCategoria = null;
        CategoriaDTOResponse categoriaDTOResponse = null;
        Categoria categoriaUpdate = new Categoria(
                categoriaRequestUpdate.getId_categoria(),
                categoriaRequestUpdate.getNombre_categoria(),
                categoriaRequestUpdate.getDescripcion_categoria()
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
                    categoriaUpdate.getDescripcion_categoria()
            );
            return categoriaDTOResponse;
        } catch (Exception e) {
            log.error("Falló la creación de la Categoria =>", e.getCause().toString());
        }
        return categoriaDTOResponse;
    }

    /**
     * Método que recupera una lista de objetos CategoriaDTOResponse ordenados según el criterio especificado.
     *
     * @param sort El criterio de ordenación.
     * @return Una lista de objetos CategoriaDTOResponse ordenados según el criterio especificado.
     * @throws Exception Si ocurre un error al recuperar la lista de las categorías ordenadas.
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
                            categoria.getDescripcion_categoria()
                    )
            );
        }
        return categoriaDTOResponses;
    }

    /**
     * Método que recupera una página de objetos CategoriaTOResponse utilizando paginación y ordenación.
     *
     * @param pageable La información de paginación y ordenación.
     * @return Una página de objetos CategoriaDTOResponse.
     * @throws Exception Si ocurre un error al recuperar la página de las categorías.
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
                            categoria.getDescripcion_categoria()
                    )
            );
        }
        return (Page<CategoriaDTOResponse>) categoriaDTOResponses;
    }

    /**
     * Método que busca una categoría por su ID y devuelve su información en un objeto CategoriaDTOResponse.
     *
     * @param id El ID de la categoría a buscar.
     * @return Un objeto CategoriaDTOResponse con la información de la categoría.
     * @throws Exception Si la categoría no se encuentra o si ocurre un error durante la búsqueda.
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
                categoriaId.getDescripcion_categoria()
        );
    }

    /**
     * Método para eliminar una categoría según su ID.
     *
     * @param id El ID de la categoría a eliminar.
     * @return true si se elimina de la categoría con éxito.
     * @throws Exception Si ocurre un error durante el proceso de eliminación de la categoría.
     */
    @Override
    @Transactional(readOnly = false)
    public boolean deleteCategoriaById(Long id) throws Exception {
        try {
            if (iCategoriaRepository.existsById(id)) {
                log.info("Eliminar Categoría con ID: " + id);
                iCategoriaRepository.deleteById(id);
                return true;
            } else {
                log.error("¡No existe el ID del Categoría!");
                throw new Exception("¡No existe el ID del Categoría!");
            }
        } catch (Exception e) {
            log.error("¡Falló la eliminación de la Categoría" + e.getCause().toString());
        }
    }
}
