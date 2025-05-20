package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.*;
import org.example.costsplitbd.exceptions.ResourceNotFoundException;
import org.example.costsplitbd.services.GrupoService;
import org.example.costsplitbd.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los grupos.
 */
@RestController
@RequestMapping("/api/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Crea un nuevo grupo.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO crearGrupo(@RequestBody CrearGrupoDTO crearGrupoDTO, @RequestParam Long idUsuarioCreador) throws Exception {
        return grupoService.crearGrupo(crearGrupoDTO,
                usuarioService.getUsuarioById(idUsuarioCreador));
    }

    /**
     * Obtiene un grupo específico.
     */
    @GetMapping("/{idGrupo}")
    public GrupoDetalladoDTO obtenerGrupo(@PathVariable Long idGrupo) {
        return grupoService.verGrupo(idGrupo);
    }

    /**
     * Lista todos los grupos de un usuario.
     */
    @GetMapping
    public List<GrupoDTO> listarGrupos(@RequestParam Long idUsuario) {
        return grupoService.listarGrupos(idUsuario);
    }

    /**
     * Añade nuevos participantes a un grupo.
     */
    @PostMapping("/{idGrupo}/participantes")
    public GrupoDetalladoDTO aniadirParticipantes(@PathVariable Long idGrupo,
                                                  @RequestBody AniadirParticipanteDTO aniadirParticipanteDTO) {
        return grupoService.aniadirParticipantes(idGrupo, aniadirParticipanteDTO);
    }

    /**
     * Obtiene la lista de participantes de un grupo.
     */
    @GetMapping("/{idGrupo}/participantes")
    public List<UsuarioDTO> verParticipantes(@PathVariable Long idGrupo) {
        return grupoService.verParticipantesGrupo(idGrupo);
    }

    /**
     * Elimina participantes de un grupo.
     */
    @DeleteMapping("/{idGrupo}/participantes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarParticipantes(@PathVariable Long idGrupo,
                                      @RequestBody AniadirParticipanteDTO aniadirParticipanteDTO) {
        grupoService.eliminarParticipante(idGrupo, aniadirParticipanteDTO);
    }
}