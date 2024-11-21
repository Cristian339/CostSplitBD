package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.*;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.example.costsplitbd.services.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los grupos.
 */
@RestController
@RequestMapping("/grupo")
public class GrupoController {
    @Autowired
    private GrupoService grupoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crea un nuevo grupo.
     *
     * @param crearGrupoDTO los datos necesarios para crear el grupo
     * @return el grupo creado
     */
    @PostMapping("/crear")
    public GrupoDTO crearGrupo(@RequestBody CrearGrupoDTO crearGrupoDTO, @RequestParam Long idUsuarioCreador) {
        Usuario usuarioCreador = usuarioRepository.findById(idUsuarioCreador).orElse(null);
        if (usuarioCreador == null) {
            throw new RuntimeException("Usuario creador no encontrado");
        }
        return grupoService.crearGrupo(crearGrupoDTO, usuarioCreador);
    }

    /**
     * Añade nuevos participantes a un grupo.
     *
     * @param idGrupo el ID del grupo
     * @param aniadirParticipanteDTO los datos de los participantes a añadir
     * @return el grupo detallado con los nuevos participantes
     */
    @PostMapping("/{idGrupo}/participantes/nuevo")
    public GrupoDetalladoDTO aniadirParticipantes(@PathVariable Long idGrupo,
                                                  @RequestBody AniadirParticipanteDTO aniadirParticipanteDTO) {
        return grupoService.aniadirParticipantes(idGrupo, aniadirParticipanteDTO);
    }

    /**
     * Obtiene la lista de participantes de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @return la lista de participantes del grupo
     */
    @GetMapping("/{idGrupo}/participantes")
    public List<UsuarioDTO> verParticipantes(@PathVariable Long idGrupo) {
        return grupoService.verParticipantesGrupo(idGrupo);
    }

    /**
     * Elimina un participante de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @param aniadirParticipanteDTO los datos del participante a eliminar
     */
    @DeleteMapping("/{idGrupo}/participantes/eliminar")
    public void eliminarParticipante(@PathVariable Long idGrupo,
                                     @RequestBody AniadirParticipanteDTO aniadirParticipanteDTO) {
        grupoService.eliminarParticipante(idGrupo, aniadirParticipanteDTO);
    }

    /**
     * Lista todos los grupos de un usuario.
     *
     * @param idUsuario el ID del usuario
     * @return la lista de grupos del usuario
     */
    @GetMapping("/")
    public List<GrupoDTO> listarGrupos(@RequestParam Long idUsuario) {
        return grupoService.listarGrupos(idUsuario);
    }

    /**
     * Añade un nuevo gasto a un grupo.
     *
     * @param gastoDTO los datos del gasto a añadir
     * @return el gasto añadido
     */
    @PostMapping("/gasto/nuevo")
    public GastoDTO aniadirGasto(@RequestBody GastoDTO gastoDTO) {
        return grupoService.aniadirGasto(gastoDTO);
    }

    /**
     * Obtiene la lista de balances de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @return la lista de balances del grupo
     */
    @GetMapping("/{idGrupo}/balances")
    public List<BalanceDTO> verBalances(@PathVariable Long idGrupo) {
        return grupoService.verBalances(idGrupo);
    }

    /**
     * Obtiene la lista de gastos de un grupo.
     *
     * @param idGrupo el ID del grupo
     * @return la lista de gastos del grupo
     */
    @GetMapping("/{idGrupo}/gastos")
    public List<GastoDTO> verGastos(@PathVariable Long idGrupo) {
        return grupoService.verGastos(idGrupo);
    }
}