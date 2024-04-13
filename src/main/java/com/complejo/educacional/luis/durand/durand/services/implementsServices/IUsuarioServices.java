package com.complejo.educacional.luis.durand.durand.services.implementsServices;

import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTOResponse;
import com.complejo.educacional.luis.durand.durand.dto.usuario.UsuarioDTOResponseUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @autor Pablo
 * @create 12-04-2024 21:12
 * @project bibliotecaLuisDurandD
 * @Version 1.0
 */
public interface IUsuarioServices {

    /**
     * Método que guarda un usuario utilizando la información proporcionada en el objeto DTO de usuario.
     *
     * @param usuarioDTORequest Objeto DTO que contiene la información del usuario a guardar.
     * @return Objeto DTO del usuario guardado.
     * @throws Exception Excepción que puede ser lanzada durante el proceso de guardado.
     */
    public UsuarioDTORequest saveUsuario(UsuarioDTORequest usuarioDTORequest) throws Exception;


    /**
     * Método que actualiza un usuario utilizando la información proporcionada en el objeto DTO de actualización.
     *
     * @param id                       Identificador del usuario a actualizar.
     * @param usuarioDTOResponseUpdate Objeto DTO que contiene la información actualizada del usuario.
     * @return Objeto DTO del usuario actualizado.
     * @throws Exception Excepción que puede ser lanzada durante el proceso de actualización.
     */
    public UsuarioDTOResponse updateUsuario(Long id, UsuarioDTOResponseUpdate usuarioDTOResponseUpdate) throws Exception;


    /**
     * Método que realiza una consulta para obtener una lista de usuarios ordenada según el criterio especificado.
     *
     * @param sort Criterio de ordenación para la consulta.
     * @return Lista de objetos DTO de usuarios ordenada según el criterio especificado.
     * @throws Exception Excepción que puede ser lanzada durante la consulta.
     */
    public List<UsuarioDTOResponse> findAllUsuarioSort(Sort sort) throws Exception;

    /**
     * Método que realiza una consulta para obtener una página de objetos DTO de usuarios, con la posibilidad de especificar la paginación y el orden.
     *
     * @param pageable Información de paginación y ordenación para la consulta.
     * @return Página de objetos DTO de usuarios según los parámetros especificados.
     * @throws Exception Excepción que puede ser lanzada durante la consulta.
     */
    public Page<UsuarioDTOResponse> findAllUsuarioPage(Pageable pageable) throws Exception;

    /**
     * Método que realiza una consulta para obtener un objeto DTO de usuario por su identificador.
     *
     * @param id Identificador del usuario.
     * @return Objeto DTO del usuario correspondiente al identificador especificado.
     * @throws Exception Excepción que puede ser lanzada durante la consulta.
     */
    public UsuarioDTOResponse findByIdUsuario(long id) throws Exception;

    /**
     * Método que elimina un usuario por su identificador.
     *
     * @param id Identificador del usuario a eliminar.
     * @return Verdadero si el usuario fue eliminado exitosamente, falso si no.
     * @throws Exception Excepción que puede ser lanzada durante el proceso de eliminación.
     */
    public boolean deleteById(long id) throws Exception;


}
