package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.BalanceDTO;
import org.example.costsplitbd.services.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los balances.
 */
@RestController
@RequestMapping("/api/balances")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    /**
     * Obtiene los balances de un grupo.
     */
    @GetMapping
    public List<BalanceDTO> obtenerBalancesPorGrupo(@RequestParam Long idGrupo) {
        return balanceService.verBalances(idGrupo);
    }

    /**
     * Obtiene el balance de un usuario específico en un grupo.
     */
    @GetMapping("/usuario")
    public BalanceDTO obtenerBalanceUsuario(@RequestParam Long idGrupo, @RequestParam Long idUsuario) {
        return balanceService.obtenerBalanceUsuario(idGrupo, idUsuario);
    }
}