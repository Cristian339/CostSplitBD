package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.*;
import org.example.costsplitbd.services.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupo")
public class GrupoController {
    @Autowired
    private GrupoService grupoService;

    @PostMapping("/crear")
    public GrupoDTO crearGrupo(@RequestBody CrearGrupoDTO crearGrupoDTO) {
        return grupoService.crearGrupo(crearGrupoDTO);

    }

    @PostMapping("/{idGrupo}/participantes/nuevo")
    public GrupoDetalladoDTO aniadirParticipantes(@PathVariable Long idGrupo,
                                                  @RequestBody AniadirParticipanteDTO aniadirParticipanteDTO) {
        return grupoService.aniadirParticipantes (idGrupo, aniadirParticipanteDTO);

    }

    @GetMapping("/{idGrupo}/participantes")
    public List<UsuarioDTO> verParticipantes(@PathVariable Long idGrupo) {
        return grupoService.verParticipantesGrupo(idGrupo);
    }

    @DeleteMapping("/{idGrupo}/participantes/eliminar")
    public void eliminarParticipante(@PathVariable Long idGrupo,
                                     @RequestBody AniadirParticipanteDTO aniadirParticipanteDTO) {
        grupoService.eliminarParticipante(idGrupo, aniadirParticipanteDTO);
    }

    @GetMapping("/")
    public List<GrupoDTO> listarGrupos(@RequestParam Long idUsuario   ) {
        return grupoService.listarGrupos(idUsuario);
    }

    @PostMapping("/{idGrupo}/gasto/nuevo")
    public GastoDTO aniadirGasto(@PathVariable Long idGrupo,
                                 @RequestBody GastoDTO gastoDTO) {
        gastoDTO.setIdGrupo(idGrupo);
        return grupoService.aniadirGasto(idGrupo, gastoDTO);
    }

}
