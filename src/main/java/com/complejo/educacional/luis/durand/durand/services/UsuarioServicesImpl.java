package com.complejo.educacional.luis.durand.durand.services;
//Import necesarios para la clase.

import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTOResponseUpdate;
import com.complejo.educacional.luis.durand.durand.models.Usuario;
import com.complejo.educacional.luis.durand.durand.repositories.IUsuarioRepository;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IUsuarioServices;
import com.complejo.educacional.luis.durand.durand.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @autor Pablo
 * @create 12-04-2024 22:55
 * @project bibliotecaLuisDurandD
 * @Version 1.0
 */
@Slf4j
@Service
public class UsuarioServicesImpl implements IUsuarioServices {
    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Método que guarda un nuevo Usuario con la información proporcionada en usuarioDTORequest.
     *
     * @param usuarioDTORequest La información del nuevo Usuario a guardar.
     * @return Un objeto UsuarioDTORequest con la información del Usuario guardado.
     * @throws Exception Si ocurre un error durante el proceso de guardado del Usuario.
     */
    @Transactional(readOnly = false)
    @Override
    public UsuarioDTORequest saveUsuario(UsuarioDTORequest usuarioDTORequest) throws Exception {
        try {
            // Crear un nuevo objeto Usuario con los datos proporcionados
            Usuario createUsuario = new Usuario(
                    usuarioDTORequest.getNombres_usuario(),
                    usuarioDTORequest.getApellido_paterno_usuario(),
                    usuarioDTORequest.getApellido_materno_usuario(),
                    usuarioDTORequest.getTelefono_usuario(),
                    usuarioDTORequest.getEmail_usuario()
            );

            // Registrar la creación del usuario en los registros de log
            log.info("---Inicio de creción Usuario ---" + objectMapper.writeValueAsString(usuarioDTORequest));

            // Guardar el nuevo usuario en la base de datos
            createUsuario = iUsuarioRepository.save(createUsuario);

            // Registrar la salida del usuario creado en los registros de log
            log.info("Json de Salida =>" + objectMapper.writeValueAsString(createUsuario));
            log.info("----Fin de método Creación Usuario  ----");

            // Devolver una respuesta con los datos del usuario creado
            return new UsuarioDTORequest(
                    createUsuario.getNombres(),
                    createUsuario.getApellidoPaterno(),
                    createUsuario.getApellidoMaterno(),
                    createUsuario.getTelefono(),
                    createUsuario.getEmail()
            );
        } catch (Exception e) {
            // Manejar cualquier error que ocurra durante la creación del usuario
            log.error("Ocurrió un error al guardar el Usuario: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al guardar el Usuario");
        }
    }

    /**
     * Método que actualiza un usuario según su ID con la información proporcionada en usuarioDTOResponseUpdate.
     *
     * @param id                       El ID del usuario a actualizar.
     * @param usuarioDTOResponseUpdate La información actualizada del usuario.
     * @return Un objeto UsuarioDTOResponseUpdate con la información actualizada del usuario.
     * @throws Exception Si ocurre un error durante el proceso de actualización del usuario.
     */
    @Transactional(readOnly = false)
    @Override
    public UsuarioDTOResponse updateUsuario(Long id, UsuarioDTOResponseUpdate usuarioDTOResponseUpdate) throws Exception {
        try {
            Optional<Usuario> usuarioOptional;
            Usuario usuario;
            Usuario usuarioUpdate = new Usuario();

            // Buscar el usuario en la base de datos por su ID
            usuarioOptional = iUsuarioRepository.findById(id);

            // Verificar si el usuario existe en la base de datos
            usuario = usuarioOptional.orElseThrow(() -> {
                log.error("¡Ocurrió un problema en la actualización de Usuario!!");
                return new Exception("¡Ocurrió un problema en la actualización de Usuario!!");
            });

            // Actualizar los datos del usuario con los valores proporcionados
            usuario.setNombres(usuarioDTOResponseUpdate.getNombres_usuario());
            usuario.setApellidoPaterno(usuarioDTOResponseUpdate.getApellido_paterno_usuario());
            usuario.setApellidoMaterno(usuarioDTOResponseUpdate.getApellido_materno_usuario());
            usuario.setTelefono(usuarioDTOResponseUpdate.getTelefono_usuario());
            usuario.setEmail(usuarioDTOResponseUpdate.getEmail_usuario());

            // Registrar la actualización del usuario en los registros de log
            log.info("Json de Salida ==> " + objectMapper.writeValueAsString(usuarioUpdate));

            // Devolver una respuesta con los datos actualizados del usuario
            return new UsuarioDTOResponse(
                    usuarioUpdate.getId_usuario(),
                    usuarioUpdate.getNombres(),
                    usuarioUpdate.getApellidoPaterno(),
                    usuarioUpdate.getApellidoMaterno(),
                    usuarioUpdate.getTelefono(),
                    usuarioUpdate.getEmail()
            );

        } catch (Exception e) {
            // Manejar cualquier error que ocurra durante la actualización del usuario
            log.error("Ocurrió un error al guardar el Usuario: " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al guardar el Usuario!");
        }
    }

    /**
     * Método que recupera una lista de objetos UsuarioDTOResponse ordenados según el criterio especificado.
     *
     * @param sort El criterio de ordenación.
     * @return Una lista de objetos UsuarioDTOResponse ordenados según el criterio especificado.
     * @throws Exception Si ocurre un error al recuperar la lista de géneros ordenados.
     */
    @Transactional(readOnly = true)
    @Override
    public List<UsuarioDTOResponse> findAllUsuarioSort(Sort sort) throws Exception {
        try {
            /**
             * Utilicé el método stream() y map() para convertir la lista de allUsuariosSort en una lista de
             * UsuarioDTOResponse de forma más concisa.
             */
            List<Usuario> allUsuariosSort = iUsuarioRepository.findUsuarioBySort(sort);
            return allUsuariosSort.stream().map(
                    usuario -> new UsuarioDTOResponse(
                            usuario.getId_usuario(),
                            usuario.getNombres(),
                            usuario.getApellidoPaterno(),
                            usuario.getApellidoMaterno(),
                            usuario.getTelefono(),
                            usuario.getEmail()
                    )
            ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Ocurrió un error al listar todos los Usuario " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al listar todos los Usuario!");
        }
    }

    /**
     * Método que recupera una página de objetos UsuarioDTOResponse utilizando paginación y ordenación.
     *
     * @param pageable La información de paginación y ordenación.
     * @return Una página de objetos UsuarioDTOResponse.
     * @throws Exception Si ocurre un error al recuperar la página de usuario.
     */
    @Transactional(readOnly = true)
    @Override
    public Page<UsuarioDTOResponse> findAllUsuarioPage(Pageable pageable) throws Exception {
        try {
            Page<Usuario> usuarioPage = iUsuarioRepository.findAllUsuarioPage(pageable);
            List<UsuarioDTOResponse> usuarioDTOResponsesList = usuarioPage
                    .stream().map(
                            usuario -> new UsuarioDTOResponse(
                                    usuario.getId_usuario(),
                                    usuario.getNombres(),
                                    usuario.getApellidoPaterno(),
                                    usuario.getApellidoMaterno(),
                                    usuario.getTelefono(),
                                    usuario.getEmail()
                            )
                    ).collect(Collectors.toList());
            /**
             * Utilicé el método collect() junto con Collectors.toList() para recopilar los elementos mapeados
             * en una lista.
             */
            return new PageImpl<>(usuarioDTOResponsesList, pageable, usuarioPage.getNumberOfElements());
            /**
             * Creé un nuevo objeto PageImpl para devolver una página de resultados con la lista de UsuarioDTOResponse,
             * el objeto Pageable original y el número total de elementos.
             */
        } catch (Exception e) {
            log.error("Ocurrió un error al listar todos los Usuario " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al listar todos los Usuario!");
        }
    }

    /**
     * Método que qusca un género por su ID y devuelve su información en un objeto UsuarioDTOResponse.
     *
     * @param id El ID del usuario a buscar.
     * @return Un objeto UsuarioDTOResponse con la información del usuario.
     * @throws Exception Si el usaurio no se encuentra o si ocurre un error durante la búsqueda.
     */
    @Transactional(readOnly = true)
    @Override
    public UsuarioDTOResponse findByIdUsuario(long id) throws Exception {
        try {
            Usuario usuario = iUsuarioRepository.findByIdUsuario(id);
            if (usuario == null) {
                throw new Exception("¡Usuario no encontrado!");
            } else {
                return new UsuarioDTOResponse(
                        usuario.getId_usuario(),
                        usuario.getNombres(),
                        usuario.getApellidoPaterno(),
                        usuario.getApellidoMaterno(),
                        usuario.getTelefono(),
                        usuario.getEmail()
                );
            }
        } catch (Exception e) {
            log.error("Ocurrió un error al buscar el Usuario con ID " + id + ": " + e.getCause().toString());
            throw new Exception("¡Ocurrió un error al buscar el Usuario!");
        }
    }

    /**
     * Método para eliminar un usuario según su ID.
     *
     * @param id El ID del usuario a eliminar.
     * @return true si se elimina el usuario con éxito.
     * @throws Exception Si ocurre un error durante el proceso de eliminación del usuario.
     */
    @Transactional(readOnly = false)
    @Override
    public boolean deleteById(long id) throws Exception {
        try {
            if (iUsuarioRepository.existsById(id)) {
                log.info("Elimininado Usuario con ID: " + id);
                iUsuarioRepository.deleteById(id);
                return true;
            } else {
                log.error("No existe el ID del Usuario!");
                throw new Exception("¡No existe el ID del Usuario!");
            }
        } catch (Exception e) {
            log.error("Ocurrió un error al eliminar el Usuario con ID " + id + ": " + e.getCause().toString());
            throw new Exception("¡Ocurrión una error al eliminar el Usuario!");
        }
    }
}