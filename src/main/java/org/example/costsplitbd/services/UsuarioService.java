package org.example.costsplitbd.services;

import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void deleteUsuarioById(Long id) {
        usuarioRepository.deleteById(id);
    }

}
