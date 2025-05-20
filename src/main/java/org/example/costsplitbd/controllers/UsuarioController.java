package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.UsuarioDTO;
import org.example.costsplitbd.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene la lista de todos los usuarios.
     */
    @GetMapping
    public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    /**
     * Crea un nuevo usuario.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.crearUsuario(usuarioDTO);
    }

    /**
     * Lista los amigos de un usuario.
     */
    @GetMapping("/{idUsuario}/amigos")
    public List<UsuarioDTO> listarAmigos(@PathVariable Long idUsuario) {
        return usuarioService.listarAmigos(idUsuario);
    }

    /**
     * Crea una relación de amistad entre dos usuarios.
     */
    @PostMapping("/{idUsuario}/amigos/{idAmigo}")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO crearAmigo(@PathVariable Long idUsuario, @PathVariable Long idAmigo) {
        return usuarioService.crearAmigo(idUsuario, idAmigo);
    }
}