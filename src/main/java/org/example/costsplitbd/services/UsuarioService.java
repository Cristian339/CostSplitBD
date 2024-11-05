package org.example.costsplitbd.services;

import org.example.costsplitbd.dto.UsuarioDTO;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Servicio para gestionar las operaciones relacionadas con los usuarios.
 */
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene la lista de todos los usuarios.
     *
     * @return la lista de todos los usuarios
     */
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Lista los amigos de un usuario.
     *
     * @param idUsuario el ID del usuario
     * @return la lista de amigos del usuario
     */
    public List<UsuarioDTO> listarAmigos(Long idUsuario) {
        Set<Usuario> amigos = usuarioRepository.findAmigosByUsuarioId(idUsuario);
        List<UsuarioDTO> amigosDTO = new ArrayList<>();
        for (Usuario amigo : amigos) {
            amigosDTO.add(new UsuarioDTO(amigo.getId(), amigo.getNombre(), amigo.getApellidos(), amigo.getEmail()));
        }
        return amigosDTO;
    }

    /**
     * Crea una relación de amistad entre dos usuarios.
     *
     * @param idUsuario el ID del usuario
     * @param idAmigo el ID del amigo
     * @return los datos del amigo creado
     */
    public UsuarioDTO crearAmigo(Long idUsuario, Long idAmigo) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return null;
        }

        Usuario amigo = usuarioRepository.findById(idAmigo).orElse(null);
        if (amigo == null) {
            return null;
        }

        if (usuario.getAmigos() == null) {
            usuario.setAmigos(new HashSet<>());
        }
        usuario.getAmigos().add(amigo);
        usuarioRepository.save(usuario);

        return new UsuarioDTO(amigo.getId(), amigo.getNombre(), amigo.getApellidos(), amigo.getEmail());
    }
}