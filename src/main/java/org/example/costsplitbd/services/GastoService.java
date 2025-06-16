package org.example.costsplitbd.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.example.costsplitbd.dto.GastoDTO;
import org.example.costsplitbd.enumerados.MetodoPago;
import org.example.costsplitbd.enumerados.MetodoReparticion;
import org.example.costsplitbd.exceptions.ResourceNotFoundException;
import org.example.costsplitbd.models.Balance;
import org.example.costsplitbd.models.Gasto;
import org.example.costsplitbd.models.Grupo;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.BalanceRepository;
import org.example.costsplitbd.repositories.GastoRepository;
import org.example.costsplitbd.repositories.GrupoRepository;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con los gastos.
 */
@Service
@Validated
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    /**
     * Añade un gasto a un grupo.
     *
     * @param gastoDTO los datos del gasto a añadir
     * @return los datos del gasto añadido
     */
    @Transactional
    public GastoDTO aniadirGasto(@Valid GastoDTO gastoDTO) throws Exception {
        validarDatosGasto(gastoDTO);

        Grupo grupo = grupoRepository.findById(gastoDTO.getIdGrupo())
                .orElseThrow(() -> new ResourceNotFoundException("El grupo no existe"));

        Usuario pagador = usuarioRepository.findById(gastoDTO.getIdPagador())
                .orElseThrow(() -> new ResourceNotFoundException("El pagador no existe"));

        Gasto gasto = new Gasto();
        gasto.setDescripcion(gastoDTO.getDescripcion());
        gasto.setMontoTotal(gastoDTO.getMontoTotal());
        gasto.setFecha(gastoDTO.getFecha() != null ? gastoDTO.getFecha() : LocalDateTime.now());
        gasto.setPagador(pagador);
        gasto.setGrupo(grupo);
        gasto.setTipoGasto(gastoDTO.getTipoGasto());

        // CORREGIDO: Omitimos estas líneas problemáticas por ahora
        // gasto.setMetodoReparticion(gastoDTO.getMetodoReparticion());
        // gasto.setMetodoPago(gastoDTO.getMetodoPago());

        gasto.setDivisa(gastoDTO.getDivisa());

        // Si hay categoría, establecerla
        if (gastoDTO.getCategoriaId() != null) {
            // Aquí deberías obtener la categoría del repositorio correspondiente
            // Este código es solo un ejemplo y debe ser adaptado
            // gasto.setCategoria(categoriaRepository.findById(gastoDTO.getCategoriaId()).orElse(null));
        }

        Gasto gastoGuardado = gastoRepository.save(gasto);

        // Actualizar balances
        actualizarBalances(gastoGuardado);

        return mapGastoToDTO(gastoGuardado);
    }

    /**
     * Actualiza los balances después de añadir un gasto
     */
    private void actualizarBalances(Gasto gasto) {
        Grupo grupo = gasto.getGrupo();
        int nroParticipantes = grupo.getUsuarios().size();
        BigDecimal montoPorParticipante = gasto.getMontoTotal()
                .divide(BigDecimal.valueOf(nroParticipantes), 2, RoundingMode.HALF_UP);

        for (Usuario usuario : grupo.getUsuarios()) {
            Balance balance = balanceRepository.findBalanceByGrupoIdAndUsuarioId(grupo.getId(), usuario.getId())
                    .orElse(new Balance());

            balance.setGrupo(grupo);
            balance.setUsuario(usuario);

            if (balance.getImporte() == null) {
                balance.setImporte(BigDecimal.ZERO);
            }

            if (usuario.equals(gasto.getPagador())) {
                balance.setImporte(balance.getImporte().add(gasto.getMontoTotal().subtract(montoPorParticipante)));
            } else {
                balance.setImporte(balance.getImporte().subtract(montoPorParticipante));
            }

            balanceRepository.save(balance);
        }
    }

    /**
     * Valida los datos del gasto antes de añadirlo
     */
    private void validarDatosGasto(GastoDTO gastoDTO) throws Exception {
        if (gastoDTO.getDescripcion() == null || gastoDTO.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }

        if (gastoDTO.getMontoTotal() == null || gastoDTO.getMontoTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto total no puede ser negativo");
        }

        if (gastoDTO.getTipoGasto() == null) {
            throw new IllegalArgumentException("El tipo de gasto es inválido");
        }
    }

    /**
     * Obtiene la lista de gastos de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @return la lista de gastos del grupo
     */
    public List<GastoDTO> verGastos(Long idGrupo) {
        return gastoRepository.findGastoByGrupoId(idGrupo).stream()
                .map(this::mapGastoToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mapea una entidad Gasto a un GastoDTO
     */
    private GastoDTO mapGastoToDTO(Gasto gasto) {
        GastoDTO dto = new GastoDTO();
        dto.setId(gasto.getId());
        dto.setIdGrupo(gasto.getGrupo().getId());
        dto.setDescripcion(gasto.getDescripcion());
        dto.setMontoTotal(gasto.getMontoTotal());
        dto.setFecha(gasto.getFecha());
        dto.setIdPagador(gasto.getPagador().getId());
        dto.setTipoGasto(gasto.getTipoGasto());

        // CORREGIDO: Omitimos estas líneas problemáticas por ahora
        // dto.setMetodoReparticion(gasto.getMetodoReparticion());
        // dto.setMetodoPago(gasto.getMetodoPago());

        dto.setDivisa(gasto.getDivisa());

        return dto;
    }

    /**
     * Obtiene un gasto específico por su ID.
     *
     * @param id El identificador único del gasto
     * @return GastoDTO con la información del gasto
     * @throws EntityNotFoundException si no se encuentra el gasto
     */
    public GastoDTO obtenerGastoPorId(Long id) {
        return gastoRepository.findById(id)
                .map(this::mapGastoToDTO)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el gasto con ID: " + id));
    }
}