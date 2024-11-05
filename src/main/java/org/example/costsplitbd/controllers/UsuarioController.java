package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.UsuarioDTO;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los usuarios.
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene la lista de todos los usuarios.
     *
     * @return la lista de todos los usuarios
     */
    @GetMapping("/all")
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    /**
     * Lista los amigos de un usuario.
     *
     * @param idUsuario el ID del usuario
     * @return la lista de amigos del usuario
     */
    @GetMapping("{idUsuario}/amigos")
    public List<UsuarioDTO> listarAmigos(@PathVariable Long idUsuario) {
        return usuarioService.listarAmigos(idUsuario);
    }

    /**
     * Crea una relación de amistad entre dos usuarios.
     *
     * @param idUsuario el ID del usuario
     * @param idAmigo el ID del amigo
     * @return los datos del amigo creado
     */
    @PostMapping("{idUsuario}/amigo/crear/{idAmigo}")
    public UsuarioDTO crearAmigo(@PathVariable Long idUsuario, @PathVariable Long idAmigo) {
        return usuarioService.crearAmigo(idUsuario, idAmigo);
    }
}