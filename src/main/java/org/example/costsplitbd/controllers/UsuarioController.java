package org.example.costsplitbd.controllers;

import org.example.costsplitbd.dto.ImageResponse;
import org.example.costsplitbd.dto.PerfilImagenDTO;
import org.example.costsplitbd.dto.UsuarioDTO;
import org.example.costsplitbd.exceptions.ResourceNotFoundException;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.services.ImageService;
import org.example.costsplitbd.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ImageService imageService;


    /**
     * Obtiene un usuario por su ID.
     */
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long idUsuario) {
        try {
            Usuario usuario = usuarioService.getUsuarioById(idUsuario);
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getApellidos(),
                    usuario.getEmail(),
                    usuario.getUrlImg(),
                    null // No devolvemos la contraseña
            );
            return ResponseEntity.ok(usuarioDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Obtiene mi propio id de usuario.
     */
    @GetMapping("/me/id")
    public ResponseEntity<Long> obtenerMiId(Principal principal) {
        Usuario usuario = usuarioService.getUsuarioByEmail(principal.getName());
        return ResponseEntity.ok(usuario.getId());
    }

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
/*    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.crearUsuario(usuarioDTO);
    }*/

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

    /**
     * Obtiene la imagen de perfil de un usuario
     */
    @GetMapping("/{usuarioId}/imagen")
    public ResponseEntity<?> obtenerImagenPerfil(@PathVariable Long usuarioId) {
        PerfilImagenDTO perfilImagen = imageService.obtenerImagenPerfil(usuarioId);
        if (perfilImagen == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(perfilImagen);
    }

    /**
     * Actualiza la imagen de perfil de un usuario
     */
    @PostMapping("/{usuarioId}/imagen")
    public ResponseEntity<?> actualizarImagenPerfil(
            @PathVariable Long usuarioId,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    ImageResponse.builder()
                            .mensaje("No se ha enviado ningún archivo")
                            .build()
            );
        }

        // Validar que sea una imagen
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest().body(
                    ImageResponse.builder()
                            .mensaje("El archivo debe ser una imagen")
                            .build()
            );
        }

        PerfilImagenDTO perfilImagen = imageService.actualizarImagenPerfil(usuarioId, file);
        if (perfilImagen == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(perfilImagen);
    }

    /**
     * Busca usuarios por nombre o email, excluyendo al usuario actual.
     *
     * @param termino el término de búsqueda (nombre)
     * @param idUsuarioActual el ID del usuario que realiza la búsqueda
     * @return lista de usuarios que coinciden con el término, excluyendo al usuario actual
     */
    @GetMapping("/buscar")
    public List<UsuarioDTO> buscarUsuarios(
            @RequestParam String termino,
            @RequestParam Long idUsuarioActual) {
        return usuarioService.buscarUsuarios(termino, idUsuarioActual);
    }
}