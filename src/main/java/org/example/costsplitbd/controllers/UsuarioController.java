package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.UsuarioDTO;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/all")
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("{idUsuario}/amigos")
    public List<UsuarioDTO> listarAmigos(@PathVariable Long idUsuario) {
        return usuarioService.listarAmigos(idUsuario);
    }
    @PostMapping("{idUsuario}/amigo/crear/{idAmigo}")
    public UsuarioDTO crearAmigo(@PathVariable Long idUsuario, @PathVariable Long idAmigo) {
        return usuarioService.crearAmigo(idUsuario, idAmigo);
    }

}
