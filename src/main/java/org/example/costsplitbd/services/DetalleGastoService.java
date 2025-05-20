package org.example.costsplitbd.services;

import jakarta.validation.Valid;
import org.example.costsplitbd.dto.DetalleGastoDTO;
import org.example.costsplitbd.exceptions.ResourceNotFoundException;
import org.example.costsplitbd.models.DetalleGasto;
import org.example.costsplitbd.models.Gasto;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.DetalleGastoRepository;
import org.example.costsplitbd.repositories.GastoRepository;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con los detalles de gasto.
 */
@Service
@Validated
public class DetalleGastoService {

    @Autowired
    private DetalleGastoRepository detalleGastoRepository;

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crea un nuevo detalle de gasto.
     *
     * @param detalleGastoDTO los datos del detalle de gasto
     * @return el detalle de gasto creado
     */
    @Transactional
    public DetalleGastoDTO crearDetalleGasto(@Valid DetalleGastoDTO detalleGastoDTO) {
        Gasto gasto = gastoRepository.findById(detalleGastoDTO.getGastoId())
                .orElseThrow(() -> new ResourceNotFoundException("Gasto no encontrado"));

        Usuario usuario = usuarioRepository.findById(detalleGastoDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (detalleGastoDTO.getImporte().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El importe no puede ser negativo");
        }

        DetalleGasto detalleGasto = new DetalleGasto();
        detalleGasto.setGasto(gasto);
        detalleGasto.setUsuario(usuario);
        detalleGasto.setImporte(detalleGastoDTO.getImporte());

        DetalleGasto detalleGuardado = detalleGastoRepository.save(detalleGasto);
        return mapDetalleGastoToDTO(detalleGuardado);
    }

    /**
     * Obtiene los detalles de gasto por ID de gasto.
     *
     * @param gastoId el ID del gasto
     * @return lista de detalles de gasto
     */
    public List<DetalleGastoDTO> obtenerDetallesPorGasto(Long gastoId) {
        if (!gastoRepository.existsById(gastoId)) {
            throw new ResourceNotFoundException("Gasto no encontrado");
        }

        return detalleGastoRepository.findByGastoId(gastoId).stream()
                .map(this::mapDetalleGastoToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mapea una entidad DetalleGasto a un DetalleGastoDTO
     */
    private DetalleGastoDTO mapDetalleGastoToDTO(DetalleGasto detalleGasto) {
        DetalleGastoDTO dto = new DetalleGastoDTO();
        dto.setId(detalleGasto.getId());
        dto.setGastoId(detalleGasto.getId());
        dto.setUsuarioId(detalleGasto.getUsuario().getId());
        dto.setUsuarioNombre(detalleGasto.getUsuario().getNombre());
        dto.setImporte(detalleGasto.getImporte());
        return dto;
    }
}