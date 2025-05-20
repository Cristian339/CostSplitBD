package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.DetalleGastoDTO;
import org.example.costsplitbd.dto.GastoDTO;
import org.example.costsplitbd.services.DetalleGastoService;
import org.example.costsplitbd.services.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los gastos.
 */
@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private DetalleGastoService detalleGastoService;

    /**
     * Crea un nuevo gasto.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GastoDTO crearGasto(@RequestBody GastoDTO gastoDTO) throws Exception {
        return gastoService.aniadirGasto(gastoDTO);
    }

    /**
     * Lista los gastos de un grupo.
     */
    @GetMapping
    public List<GastoDTO> listarGastosPorGrupo(@RequestParam Long idGrupo) {
        return gastoService.verGastos(idGrupo);
    }

    /**
     * Obtiene un gasto por su ID.
     */
    @GetMapping("/{idGasto}")
    public GastoDTO obtenerGasto(@PathVariable Long idGasto) {
        return gastoService.obtenerGastoPorId(idGasto);
    }

    /**
     * Añade un detalle a un gasto (reparto entre usuarios).
     */
    @PostMapping("/{idGasto}/detalles")
    @ResponseStatus(HttpStatus.CREATED)
    public DetalleGastoDTO aniadirDetalleGasto(@PathVariable Long idGasto,
                                               @RequestBody DetalleGastoDTO detalleGastoDTO) {
        detalleGastoDTO.setGastoId(idGasto);
        return detalleGastoService.crearDetalleGasto(detalleGastoDTO);
    }

    /**
     * Lista los detalles de un gasto.
     */
    @GetMapping("/{idGasto}/detalles")
    public List<DetalleGastoDTO> listarDetallesGasto(@PathVariable Long idGasto) {
        return detalleGastoService.obtenerDetallesPorGasto(idGasto);
    }
}