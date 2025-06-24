package org.example.costsplitbd.controllers;

import lombok.RequiredArgsConstructor;
import org.example.costsplitbd.dto.ImageResponse;
import org.example.costsplitbd.services.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> subirImagen(@RequestParam("file") MultipartFile file) {
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

        ImageResponse response = imageService.guardarImagen(file);
        return ResponseEntity.ok(response);
    }
}