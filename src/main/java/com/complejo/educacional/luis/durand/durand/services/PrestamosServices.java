package com.complejo.educacional.luis.durand.durand.services;

import com.complejo.educacional.luis.durand.durand.dto.prestamo.PrestamoDTORequest;
import com.complejo.educacional.luis.durand.durand.dto.prestamo.PrestamoDTORequestUpdate;
import com.complejo.educacional.luis.durand.durand.dto.prestamo.PrestamoDTOResponse;
import com.complejo.educacional.luis.durand.durand.models.Libro;
import com.complejo.educacional.luis.durand.durand.models.Prestamo;
import com.complejo.educacional.luis.durand.durand.models.Usuario;
import com.complejo.educacional.luis.durand.durand.repositories.ILibroRepository;
import com.complejo.educacional.luis.durand.durand.repositories.IPrestamoRepository;
import com.complejo.educacional.luis.durand.durand.repositories.IUsuarioRepository;
import com.complejo.educacional.luis.durand.durand.services.implementsServices.IPrestamoService;
import com.complejo.educacional.luis.durand.durand.utils.enums.EstadoPrestamo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @autor Pablo
 * @creates 11-05-2025 0:07
 * @proyect bibliotecaLuisDurandD
 */
@Slf4j
@Service
public class PrestamosServices implements IPrestamoService {
    @Autowired
    private IPrestamoRepository iPrestamoRepository;
    @Autowired
    private ILibroRepository iLibroRepository;
    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PrestamoDTORequest savePrestamo(PrestamoDTORequest prestamoDTORequest) throws Exception {
        try {
            Usuario usuarios_id = iUsuarioRepository.getReferenceById(prestamoDTORequest.getId_usuario());
            Libro libro_id = iLibroRepository.getReferenceById(prestamoDTORequest.getId_libro());

            Prestamo createPrestamo = new Prestamo();
            createPrestamo.setId_prestamo(null);
            createPrestamo.setUsuario(usuarios_id);
            createPrestamo.setLibro(libro_id);
            createPrestamo.setFechaPrestamo(prestamoDTORequest.getFechaPrestamo());
            createPrestamo.setFechaDevolucionPrevista(prestamoDTORequest.getFechaDevolucionPrevista());
            createPrestamo.setFechaDevolucionReal(prestamoDTORequest.getFechaDevolucionReal());
            createPrestamo.setEstado(prestamoDTORequest.getEstado());
            createPrestamo.setMulta(prestamoDTORequest.getMulta());
            createPrestamo.setObservaciones(prestamoDTORequest.getObservaciones());
            createPrestamo.setCreatedAt(prestamoDTORequest.getCreatedAt());
            createPrestamo.setUpdatedAt(prestamoDTORequest.getUpdatedAt());
            log.info("--Inicio de la Creación del Prestamo" + objectMapper.writeValueAsString(prestamoDTORequest));
            iPrestamoRepository.save(createPrestamo);
            log.info("--Json del Prestamo => " + objectMapper.writeValueAsString(iPrestamoRepository.save(createPrestamo)));
            log.info("----Fin de método Creación Prestamo----");
            return new PrestamoDTORequest(
                    prestamoDTORequest.getId_usuario(),
                    prestamoDTORequest.getId_libro(),
                    prestamoDTORequest.getFechaPrestamo(),
                    prestamoDTORequest.getFechaDevolucionPrevista(),
                    prestamoDTORequest.getFechaDevolucionReal(),
                    prestamoDTORequest.getEstado(),
                    prestamoDTORequest.getMulta(),
                    prestamoDTORequest.getObservaciones(),
                    prestamoDTORequest.getCreatedAt(),
                    prestamoDTORequest.getUpdatedAt()
            );
        } catch (Exception e) {
            log.error("Ocurrió un error al guardar el Prestamo: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al guardar el Prestamo :(");
        }
    }

    @Override
    public PrestamoDTOResponse updatePrestamo(Long id, PrestamoDTORequestUpdate prestamoDTORequestUpdate) throws Exception {
        return null;
    }

    @Override
    public List<PrestamoDTOResponse> findAllPrestamoSort(Sort sort) throws Exception {
        try {
            List<Prestamo> prestamoDTOResponseList = iPrestamoRepository.findAllPrestamosSort(sort);
            return prestamoDTOResponseList.stream().map(
                    prestamo -> new PrestamoDTOResponse(
                            prestamo.getId_prestamo(),
                            prestamo.getUsuario().getId_usuario(),
                            prestamo.getLibro().getId_libro(),
                            prestamo.getFechaPrestamo(),
                            prestamo.getFechaDevolucionPrevista(),
                            prestamo.getFechaDevolucionReal(),
                            prestamo.getEstado(),
                            prestamo.getMulta(),
                            prestamo.getObservaciones(),
                            prestamo.getCreatedAt(),
                            prestamo.getUpdatedAt()
                    )
            ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Ocurrió un error al Listar todos los libros: " + e.getCause().toString());
            throw new Exception("Ocurrió un error al listar los libros");
        }
    }

    @Override
    public Page<PrestamoDTOResponse> findAllPrestamoPage(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public PrestamoDTOResponse findByIdPrestamo(long id) throws Exception {
        return null;
    }

    @Override
    public void deleteByIdPrestamo(long id) throws Exception {

    }

    /**
     * Verifica si un préstamo está atrasado.
     *
     * @param prestamo préstamo a verificar
     * @return true si está atrasado, false si no
     */
    @Override
    public boolean estaAtrasado(PrestamoDTOResponse prestamo) throws Exception {
        try {
            LocalDate fechaDevolucionReal = prestamo.getFechaDevolucionReal();
            LocalDate fechaDevolucionPrevista = prestamo.getFechaDevolucionPrevista();

            if (fechaDevolucionReal == null) {
                return LocalDate.now().isAfter(fechaDevolucionPrevista);
            } else {
                return fechaDevolucionReal.isAfter(fechaDevolucionPrevista);
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Calcula la multa por retraso en el préstamo.
     *
     * @param prestamo    préstamo a calcular multa
     * @param multaDiaria multa diaria por retraso
     * @return multa calculada
     */
    @Override
    public BigDecimal calcularMulta(PrestamoDTOResponse prestamo, BigDecimal multaDiaria) throws Exception {
        try {
            if (!estaAtrasado(prestamo)) {
                return BigDecimal.ZERO;
            }
            LocalDate fechaFin = prestamo.getFechaDevolucionReal() != null ? prestamo.getFechaDevolucionReal() : LocalDate.now();
            long diasAtraso = ChronoUnit.DAYS.between(prestamo.getFechaDevolucionPrevista(), fechaFin);
            return multaDiaria.multiply(BigDecimal.valueOf(diasAtraso));
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * Marca un préstamo como devuelto, actualizando fecha y estado.
     *
     * @param prestamo        préstamo a actualizar
     * @param fechaDevolucion fecha real de devolución
     */
    @Override
    public void marcarComoDevuelto(PrestamoDTOResponse prestamo, LocalDate fechaDevolucion) throws Exception {
        prestamo.setFechaDevolucionReal(fechaDevolucion);
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
    }

    /**
     * Calcula la duración del préstamo en días.
     *
     * @param prestamo préstamo a calcular duración
     * @return duración en días
     */
    @Override
    public long calcularDuracion(PrestamoDTOResponse prestamo) throws Exception {
        try {
            LocalDate fechaFin = prestamo.getFechaDevolucionReal() != null ? prestamo.getFechaDevolucionReal() : LocalDate.now();
            return ChronoUnit.DAYS.between(prestamo.getFechaPrestamo(), fechaFin);
        } catch (Exception e) {

        }
        return 0;
    }

    /**
     * Renueva el préstamo extendiendo la fecha prevista de devolución.
     *
     * @param prestamo  préstamo a renovar
     * @param diasExtra días adicionales para la renovación
     */
    @Override
    public void renovarPrestamo(PrestamoDTOResponse prestamo, int diasExtra) throws Exception {
        try {

            prestamo.setFechaDevolucionPrevista(prestamo.getFechaDevolucionPrevista().plusDays(diasExtra));
        } catch (Exception e) {

        }
    }
}
