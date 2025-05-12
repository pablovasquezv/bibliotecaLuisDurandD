package com.complejo.educacional.luis.durand.durand.services;

import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTOResponse;
import com.complejo.educacional.luis.durand.durand.models.Usuario;
import com.complejo.educacional.luis.durand.durand.repositories.IUsuarioRepository;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IUsuarioServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @autor Pablo
 * @creates 10-05-2025 18:44
 * @proyect bibliotecaLuisDurandD
 */
@Slf4j
@Service
public class UsuarioServicesImpl implements IUsuarioServices {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public UsuarioDTORequest suaveUsuario(UsuarioDTORequest usuarioDTORequest) throws Exception {
        try {
            Usuario createUsuario = new Usuario();
            createUsuario.setId_usuario(null);
            createUsuario.setNombres_usuario(usuarioDTORequest.getNombres_usuario());
            createUsuario.setApellido_paterno_usuario(usuarioDTORequest.getApellido_paterno_usuario());
            createUsuario.setApellido_materno_usuario(usuarioDTORequest.getApellido_materno_usuario());
            createUsuario.setFecha_nacimiento_usuario(usuarioDTORequest.getFecha_nacimiento_usuario());
            createUsuario.setGenero_usuario(usuarioDTORequest.getGenero_usuario());
            createUsuario.setEdad_usuario(usuarioDTORequest.getEdad_usuario());
            createUsuario.setCreatedAt(usuarioDTORequest.getCreatedAt());
            createUsuario.setUpdatedAt(usuarioDTORequest.getUpdatedAt());
            log.info("--Inicio de la Creación del Usuario" + objectMapper.writeValueAsString(usuarioDTORequest));
            iUsuarioRepository.save(createUsuario);
            log.info("--Json del Usuario => " + objectMapper.writeValueAsString(iUsuarioRepository.save(createUsuario)));
            log.info("----Fin de método Creación Usuario----");
            return new UsuarioDTORequest(
                    usuarioDTORequest.getNombres_usuario(),
                    usuarioDTORequest.getApellido_paterno_usuario(),
                    usuarioDTORequest.getApellido_materno_usuario(),
                    usuarioDTORequest.getFecha_nacimiento_usuario(),
                    usuarioDTORequest.getGenero_usuario(),
                    usuarioDTORequest.getEdad_usuario(),
                    usuarioDTORequest.getCreatedAt(),
                    usuarioDTORequest.getUpdatedAt()
            );
        }catch (Exception e){
            log.error("Ocurrió un error al guardar el Libro: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al guardar el Libro :(");
        }
    }

    @Override
    public UsuarioDTOResponse updateUsuario(Long id, UsuarioDTORequestUpdate update) throws Exception {
        return null;
    }

    @Override
    public List<UsuarioDTOResponse> findAllUsuarioSort(Sort sort) throws Exception {
        try {
            List<Usuario>usuarios= iUsuarioRepository.findAllUsuarioSort(sort);
            return usuarios.stream().map(
                    usuario ->  new UsuarioDTOResponse(
                            usuario.getId_usuario(),
                            usuario.getNombres_usuario(),
                            usuario.getApellido_paterno_usuario(),
                            usuario.getApellido_materno_usuario(),
                            usuario.getFecha_nacimiento_usuario(),
                            usuario.getGenero_usuario(),
                            usuario.getEdad_usuario(),
                            usuario.getCreatedAt(),
                            usuario.getUpdatedAt()
                    )
            ).collect(Collectors.toList());
        }catch (Exception e){
            log.error("Ocurrió un error al Listar todos los Usuarios: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al listar los Usuarios");
        }
    }

    @Override
    public Page<UsuarioDTOResponse> findAllUsuarioPage(Pageable pageable) throws Exception {
        try {
            Page<Usuario>pageUsuario= iUsuarioRepository.findAllPageUsuario(pageable);
            /**
             * Utilicé el método stream() y map() para convertir la lista de Libro en una lista
             * de libroDTOResponses de forma más concisa.
             */
            List<UsuarioDTOResponse>usuarioDTOResponses = pageUsuario.stream().map(
                        usuario -> new UsuarioDTOResponse(
                                usuario.getId_usuario(),
                                usuario.getNombres_usuario(),
                                usuario.getApellido_paterno_usuario(),
                                usuario.getApellido_materno_usuario(),
                                usuario.getFecha_nacimiento_usuario(),
                                usuario.getGenero_usuario(),
                                usuario.getEdad_usuario(),
                                usuario.getCreatedAt(),
                                usuario.getUpdatedAt()
                        )
            ).collect(Collectors.toList());
            /**
             * Utilicé el método collect() junto con Collectors.toList() para recopilar los elementos mapeados
             * en una lista.
             */
            return new PageImpl<>(usuarioDTOResponses,pageable,pageUsuario.getTotalElements());
            /**
             * Creé un nuevo objeto PageImpl para devolver una página de resultados con la lista de AutorDTOResponse,
             * el objeto Pageable original y el número total de elementos.
             */
        }catch (Exception e){
            log.error("Ocurrió un error al Listar todos los Usuarios: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al listar los Usuarios");
        }
    }

    @Override
    public UsuarioDTOResponse findByIdUsuario(long id) throws Exception {
        return null;
    }

    @Override
    public void deleteByIdUsuario(long id) throws Exception {

    }
}
