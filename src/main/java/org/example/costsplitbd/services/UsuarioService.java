package org.example.costsplitbd.services;

import jakarta.validation.Valid;
import org.example.costsplitbd.dto.UsuarioDTO;
import org.example.costsplitbd.exceptions.ResourceNotFoundException;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las operaciones relacionadas con los usuarios.
 */
@Service
@Validated
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Patrón para validar contraseñas (mínimo 8 caracteres, al menos una letra y un número)
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

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
     * @throws IllegalArgumentException si ya existe un usuario con el mismo email o si la contraseña no cumple los requisitos
     */
    @Transactional
    public UsuarioDTO crearUsuario(@Valid UsuarioDTO usuarioDTO) {
        // Verificar si ya existe un usuario con ese email
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuarioDTO.getEmail());
        }

        // Validar que la contraseña cumpla con los requisitos
        if (usuarioDTO.getContrasenia() == null || !PASSWORD_PATTERN.matcher(usuarioDTO.getContrasenia()).matches()) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres y contener letras y números");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellidos(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setUrlImg(usuarioDTO.getUrlImg());
        usuario.setContrasenia(passwordEncoder.encode(usuarioDTO.getContrasenia()));

        // Establecer la fecha de creación al momento actual
        usuario.setFechaCreacion(LocalDate.now());

        // Por defecto, un nuevo usuario no es administrador
        usuario.setEsAdmin(false);

        try {
            Usuario usuarioGuardado = usuarioRepository.save(usuario);
            return mapUsuarioToDTO(usuarioGuardado);
        } catch (DataIntegrityViolationException e) {
            // Capturar violaciones de integridad como claves duplicadas
            throw new IllegalArgumentException("Error al crear el usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un usuario por su email.
     *
     * @param email el email del usuario
     * @return el usuario encontrado
     * @throws ResourceNotFoundException si el usuario no existe
     */
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
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
                usuario.getUrlImg(),
                usuario.getContrasenia()
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

    /**
     * Busca usuarios por nombre, excluyendo al usuario actual.
     *
     * @param termino el término de búsqueda (nombre)
     * @param idUsuarioActual el ID del usuario que realiza la búsqueda
     * @return lista de usuarios que coinciden con el término, excluyendo al usuario actual
     */
    public List<UsuarioDTO> buscarUsuarios(String termino, Long idUsuarioActual) {
        return usuarioRepository
                .findByNombreContainingIgnoreCase(termino)
                .stream()
                .filter(usuario -> !usuario.getId().equals(idUsuarioActual))
                .map(this::mapUsuarioToDTO)
                .collect(Collectors.toList());
    }
}