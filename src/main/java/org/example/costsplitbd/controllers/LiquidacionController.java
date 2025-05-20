package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.LiquidacionDTO;
import org.example.costsplitbd.services.LiquidacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con las liquidaciones.
 */
@RestController
@RequestMapping("/api/liquidaciones")
public class LiquidacionController {

    @Autowired
    private LiquidacionService liquidacionService;

    /**
     * Crea una nueva liquidación.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LiquidacionDTO crearLiquidacion(@RequestBody LiquidacionDTO liquidacionDTO) {
        return liquidacionService.crearLiquidacion(liquidacionDTO);
    }

    /**
     * Obtiene las liquidaciones de un grupo.
     */
    @GetMapping
    public List<LiquidacionDTO> obtenerLiquidacionesPorGrupo(@RequestParam Long idGrupo) {
        return liquidacionService.obtenerLiquidacionesPorGrupo(idGrupo);
    }

    /**
     * Actualiza el estado de una liquidación.
     */
    @PatchMapping("/{idLiquidacion}/estado")
    public LiquidacionDTO actualizarEstadoLiquidacion(@PathVariable Long idLiquidacion,
                                                      @RequestParam String nuevoEstado) {
        return liquidacionService.actualizarEstadoLiquidacion(idLiquidacion, nuevoEstado);
    }
}