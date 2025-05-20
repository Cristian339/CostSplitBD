package org.example.costsplitbd.services;

import jakarta.validation.Valid;
import org.example.costsplitbd.dto.UsuarioDTO;
import org.example.costsplitbd.exceptions.ResourceNotFoundException;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con los usuarios.
 */
@Service
@Validated
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene la lista de todos los usuarios.
     *
     * @return la lista de todos los usuarios
     */
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::mapUsuarioToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista los amigos de un usuario.
     *
     * @param idUsuario el ID del usuario
     * @return la lista de amigos del usuario
     */
    public List<UsuarioDTO> listarAmigos(Long idUsuario) {
        return usuarioRepository.findAmigosByUsuarioId(idUsuario).stream()
                .map(this::mapUsuarioToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea una relación de amistad entre dos usuarios.
     *
     * @param idUsuario el ID del usuario
     * @param idAmigo el ID del amigo
     * @return los datos del amigo creado
     */
    @Transactional
    public UsuarioDTO crearAmigo(Long idUsuario, Long idAmigo) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Usuario amigo = usuarioRepository.findById(idAmigo)
                .orElseThrow(() -> new ResourceNotFoundException("Amigo no encontrado"));

        if (usuario.getAmigos() == null) {
            usuario.setAmigos(new HashSet<>());
        }
        usuario.getAmigos().add(amigo);
        usuarioRepository.save(usuario);

        return mapUsuarioToDTO(amigo);
    }

    /**
     * Crea un nuevo usuario con validaciones.
     *
     * @param usuarioDTO los datos del usuario a crear
     * @return los datos del usuario creado
     */
    @Transactional
    public UsuarioDTO crearUsuario(@Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellidos(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setUrlImg(usuarioDTO.getUrlImg());
        usuario.setContrasenia("defaultPassword"); // Considerar usar password encoder

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapUsuarioToDTO(usuarioGuardado);
    }

    /**
     * Mapea una entidad Usuario a un UsuarioDTO
     */
    private UsuarioDTO mapUsuarioToDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getUrlImg()
        );
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id el ID del usuario
     * @return el usuario encontrado
     * @throws ResourceNotFoundException si el usuario no existe
     */
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }
}