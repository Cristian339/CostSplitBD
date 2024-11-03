package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.AniadirParticipanteDTO;
import org.example.costsplitbd.dto.CrearGrupoDTO;
import org.example.costsplitbd.dto.GrupoDTO;
import org.example.costsplitbd.dto.GrupoDetalladoDTO;
import org.example.costsplitbd.services.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return grupoService.aniadirParticipantes(idGrupo, aniadirParticipanteDTO);

    }


}
