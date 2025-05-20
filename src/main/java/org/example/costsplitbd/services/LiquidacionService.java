package org.example.costsplitbd.services;

import jakarta.validation.Valid;
import org.example.costsplitbd.dto.LiquidacionDTO;
import org.example.costsplitbd.enumerados.EstadoLiquidacion;
import org.example.costsplitbd.exceptions.ResourceNotFoundException;
import org.example.costsplitbd.models.Balance;
import org.example.costsplitbd.models.Grupo;
import org.example.costsplitbd.models.Liquidacion;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.BalanceRepository;
import org.example.costsplitbd.repositories.GrupoRepository;
import org.example.costsplitbd.repositories.LiquidacionRepository;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con las liquidaciones.
 */
@Service
@Validated
public class LiquidacionService {

    @Autowired
    private LiquidacionRepository liquidacionRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    /**
     * Crea una nueva liquidación entre usuarios.
     *
     * @param liquidacionDTO datos de la liquidación
     * @return la liquidación creada
     */
    @Transactional
    public LiquidacionDTO crearLiquidacion(@Valid LiquidacionDTO liquidacionDTO) {
        // Validar que el grupo existe
        Grupo grupo = grupoRepository.findById(liquidacionDTO.getGrupoId())
                .orElseThrow(() -> new ResourceNotFoundException("Grupo no encontrado"));

        // Validar que los usuarios existen
        Usuario pagador = usuarioRepository.findById(liquidacionDTO.getPagadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario pagador no encontrado"));

        Usuario receptor = usuarioRepository.findById(liquidacionDTO.getReceptorId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario receptor no encontrado"));

        // Validar que el importe es positivo
        if (liquidacionDTO.getImporte() == null || liquidacionDTO.getImporte().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El importe debe ser positivo");
        }

        // Crear la liquidación
        Liquidacion liquidacion = new Liquidacion();
        liquidacion.setGrupo(grupo);
        liquidacion.setPagador(pagador);
        liquidacion.setReceptor(receptor);
        liquidacion.setImporte(liquidacionDTO.getImporte());
        liquidacion.setFecha(LocalDateTime.now());
        liquidacion.setEstado(EstadoLiquidacion.PENDIENTE);

        Liquidacion liquidacionGuardada = liquidacionRepository.save(liquidacion);

        // Devolver el DTO con los datos actualizados
        return mapLiquidacionToDTO(liquidacionGuardada);
    }

    /**
     * Obtiene todas las liquidaciones de un grupo.
     *
     * @param idGrupo ID del grupo
     * @return lista de liquidaciones del grupo
     */
    public List<LiquidacionDTO> obtenerLiquidacionesPorGrupo(Long idGrupo) {
        if (!grupoRepository.existsById(idGrupo)) {
            throw new ResourceNotFoundException("Grupo no encontrado");
        }

        return liquidacionRepository.findByGrupoId(idGrupo).stream()
                .map(this::mapLiquidacionToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza el estado de una liquidación y ajusta los balances si es necesario.
     *
     * @param idLiquidacion ID de la liquidación
     * @param nuevoEstado nuevo estado de la liquidación
     * @return la liquidación actualizada
     */
    @Transactional
    public LiquidacionDTO actualizarEstadoLiquidacion(Long idLiquidacion, String nuevoEstado) {
        // Validar y obtener la liquidación
        Liquidacion liquidacion = liquidacionRepository.findById(idLiquidacion)
                .orElseThrow(() -> new ResourceNotFoundException("Liquidación no encontrada"));

        // Convertir string a enum
        EstadoLiquidacion estado;
        try {
            estado = EstadoLiquidacion.valueOf(nuevoEstado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de liquidación no válido");
        }

        // Solo actualizar si el estado cambia
        if (liquidacion.getEstado() != estado) {
            // Si cambia a COMPLETADO, actualizar balances
            if (estado == EstadoLiquidacion.COMPLETADA) {
                actualizarBalancesPorLiquidacion(liquidacion);
            }

            liquidacion.setEstado(estado);
            liquidacionRepository.save(liquidacion);
        }

        return mapLiquidacionToDTO(liquidacion);
    }

    /**
     * Actualiza los balances después de completar una liquidación.
     *
     * @param liquidacion la liquidación completada
     */
    private void actualizarBalancesPorLiquidacion(Liquidacion liquidacion) {
        // Actualizar balance del pagador (resta)
        Balance balancePagador = balanceRepository
                .findBalanceByGrupoIdAndUsuarioId(liquidacion.getGrupo().getId(), liquidacion.getPagador().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Balance del pagador no encontrado"));

        balancePagador.setImporte(balancePagador.getImporte().subtract(liquidacion.getImporte()));
        balanceRepository.save(balancePagador);

        // Actualizar balance del receptor (suma)
        Balance balanceReceptor = balanceRepository
                .findBalanceByGrupoIdAndUsuarioId(liquidacion.getGrupo().getId(), liquidacion.getReceptor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Balance del receptor no encontrado"));

        balanceReceptor.setImporte(balanceReceptor.getImporte().add(liquidacion.getImporte()));
        balanceRepository.save(balanceReceptor);
    }

    /**
     * Mapea una entidad Liquidacion a un LiquidacionDTO.
     *
     * @param liquidacion la entidad a mapear
     * @return el DTO resultante
     */
    private LiquidacionDTO mapLiquidacionToDTO(Liquidacion liquidacion) {
        LiquidacionDTO dto = new LiquidacionDTO();
        dto.setId(liquidacion.getId());
        dto.setPagadorId(liquidacion.getPagador().getId());
        dto.setPagadorNombre(liquidacion.getPagador().getNombre());
        dto.setReceptorId(liquidacion.getReceptor().getId());
        dto.setReceptorNombre(liquidacion.getReceptor().getNombre());
        dto.setImporte(liquidacion.getImporte());
        dto.setFecha(liquidacion.getFecha());
        dto.setGrupoId(liquidacion.getGrupo().getId());
        dto.setGrupoNombre(liquidacion.getGrupo().getNombre());
        dto.setEstado(liquidacion.getEstado());
        return dto;
    }
}