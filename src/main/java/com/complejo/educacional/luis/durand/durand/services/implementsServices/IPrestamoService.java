package com.complejo.educacional.luis.durand.durand.services.implementsServices;

import com.complejo.educacional.luis.durand.durand.dto.prestamo.PrestamoDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.prestamo.PrestamoDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.prestamo.PrestamoDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @version 1.0
 * @autor Pablo
 * @creates 10-05-2025 21:19
 * @proyect bibliotecaLuisDurandD
 */
public interface IPrestamoService {

    /**
     * @param prestamoDTORequest
     * @return
     * @throws Exception
     */
    PrestamoDTORequest savePrestamo(PrestamoDTORequest prestamoDTORequest) throws Exception;

    /**
     * @param id
     * @param prestamoDTORequestUpdate
     * @return
     * @throws Exception
     */
    PrestamoDTOResponse updatePrestamo(Long id, PrestamoDTORequestUpdate prestamoDTORequestUpdate) throws Exception;

    /**
     * @param sort
     * @return
     * @throws Exception
     */
    List<PrestamoDTOResponse> findAllPrestamoSort(Sort sort) throws Exception;

    /**
     * @param pageable
     * @return
     * @throws Exception
     */
    Page<PrestamoDTOResponse> findAllPrestamoPage(Pageable pageable) throws Exception;

    /**
     * @param id
     * @return
     * @throws Exception
     */
    PrestamoDTOResponse findByIdPrestamo(long id) throws Exception;

    /**
     * @param id
     * @throws Exception
     */
    void deleteByIdPrestamo(long id) throws Exception;


    /**
     * @param prestamo
     * @return
     * @throws Exception
     */
    boolean estaAtrasado(PrestamoDTOResponse prestamo) throws Exception;

    /**
     * @param prestamo
     * @param multaDiaria
     * @return
     * @throws Exception
     */
    BigDecimal calcularMulta(PrestamoDTOResponse prestamo, BigDecimal multaDiaria) throws Exception;

    /**
     * @param prestamo
     * @param fechaDevolucion
     * @throws Exception
     */
    void marcarComoDevuelto(PrestamoDTOResponse prestamo, LocalDate fechaDevolucion) throws Exception;

    /**
     * @param prestamo
     * @return
     * @throws Exception
     */
    long calcularDuracion(PrestamoDTOResponse prestamo) throws Exception;

    /**
     * @param prestamo
     * @param diasExtra
     * @throws Exception
     */
    void renovarPrestamo(PrestamoDTOResponse prestamo, int diasExtra) throws Exception;
}
