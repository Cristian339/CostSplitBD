package org.example.costsplitbd.controllers;

import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/all")
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.getAllUsuarios();
    }


}
