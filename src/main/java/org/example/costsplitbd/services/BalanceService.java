package org.example.costsplitbd.services;

import org.example.costsplitbd.dto.BalanceDTO;
import org.example.costsplitbd.exceptions.ResourceNotFoundException;
import org.example.costsplitbd.models.Balance;
import org.example.costsplitbd.repositories.BalanceRepository;
import org.example.costsplitbd.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con los balances.
 */
@Service
@Validated
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    /**
     * Obtiene la lista de balances de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @return la lista de balances del grupo
     */
    public List<BalanceDTO> verBalances(Long idGrupo) {
        if (!grupoRepository.existsById(idGrupo)) {
            throw new ResourceNotFoundException("Grupo no encontrado");
        }

        return balanceRepository.findBalanceByGrupoId(idGrupo).stream()
                .map(this::mapBalanceToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mapea una entidad Balance a un BalanceDTO
     */
    private BalanceDTO mapBalanceToDTO(Balance balance) {
        BalanceDTO dto = new BalanceDTO();
        dto.setUsuarioId(balance.getUsuario().getId());
        dto.setUsuarioNombre(balance.getUsuario().getNombre());
        dto.setImporte(balance.getImporte());
        return dto;
    }
}