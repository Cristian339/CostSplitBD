package org.example.costsplitbd.services;

import org.example.costsplitbd.dto.ImageResponse;
import org.example.costsplitbd.dto.PerfilImagenDTO;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${app.upload.dir:${user.home}/uploads}")
    private String uploadDir;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ImageResponse guardarImagen(MultipartFile file) {
        try {
            // Crear directorio si no existe
            Path directorio = Paths.get(uploadDir);
            if (!Files.exists(directorio)) {
                Files.createDirectories(directorio);
            }

            // Generar nombre único
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = directorio.resolve(filename);

            // Guardar archivo
            Files.copy(file.getInputStream(), filePath);

            // Crear URL (en producción debería ser una URL completa)
            String fileUrl = "/api/images/" + filename;

            return ImageResponse.builder()
                    .url(fileUrl)
                    .mensaje("Imagen subida correctamente")
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage());
        }
    }

    /**
     * Obtiene la imagen de perfil de un usuario
     */
    public PerfilImagenDTO obtenerImagenPerfil(Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return PerfilImagenDTO.builder()
                    .usuarioId(usuario.getId())
                    .imagenUrl(usuario.getUrlImg())
                    .build();
        }
        return null;
    }

    /**
     * Actualiza la imagen de perfil de un usuario
     */
    public PerfilImagenDTO actualizarImagenPerfil(Long usuarioId, MultipartFile file) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            // Guardar la nueva imagen
            ImageResponse imageResponse = guardarImagen(file);

            // Actualizar la URL de la imagen en el usuario
            Usuario usuario = usuarioOpt.get();
            usuario.setUrlImg(imageResponse.getUrl());
            usuarioRepository.save(usuario);

            // Retornar el DTO con la información actualizada
            return PerfilImagenDTO.builder()
                    .usuarioId(usuario.getId())
                    .imagenUrl(usuario.getUrlImg())
                    .build();
        }
        return null;
    }
}