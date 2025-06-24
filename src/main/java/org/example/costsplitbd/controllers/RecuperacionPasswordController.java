package org.example.costsplitbd.controllers;

import lombok.RequiredArgsConstructor;
import org.example.costsplitbd.dto.CambiarPasswordDTO;
import org.example.costsplitbd.dto.RecuperarPasswordDTO;
import org.example.costsplitbd.dto.RespuestaDTO;
import org.example.costsplitbd.dto.VerificarCodigoDTO;
import org.example.costsplitbd.services.RecuperacionPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/recuperacion")
@RequiredArgsConstructor
public class RecuperacionPasswordController {

    private final RecuperacionPasswordService recuperacionService;

    @PostMapping("/solicitar")
    public ResponseEntity<RespuestaDTO> solicitarRecuperacion(@RequestBody RecuperarPasswordDTO dto) {
        boolean enviado = recuperacionService.solicitarRecuperacion(dto);

        if (enviado) {
            return ResponseEntity.ok(RespuestaDTO.builder()
                    .token("Se ha enviado un código a tu correo electrónico")
                    .build());
        } else {
            return ResponseEntity.badRequest().body(RespuestaDTO.builder()
                    .token("El correo electrónico no está registrado")
                    .build());
        }
    }

    @PostMapping("/verificar")
    public ResponseEntity<RespuestaDTO> verificarCodigo(@RequestBody VerificarCodigoDTO dto) {
        boolean codigoCorrecto = recuperacionService.verificarCodigo(dto);

        if (codigoCorrecto) {
            return ResponseEntity.ok(RespuestaDTO.builder()
                    .token("Código verificado correctamente")
                    .build());
        } else {
            return ResponseEntity.badRequest().body(RespuestaDTO.builder()
                    .token("Código inválido o expirado")
                    .build());
        }
    }

    @PostMapping("/cambiar-password")
    public ResponseEntity<RespuestaDTO> cambiarPassword(@RequestBody CambiarPasswordDTO dto) {
        if (recuperacionService.cambiarPassword(dto)) {
            return ResponseEntity.ok(RespuestaDTO.builder()
                    .token("Contraseña actualizada correctamente")
                    .build());
        } else {
            // Podríamos mejorar esto para distinguir entre diferentes tipos de error
            return ResponseEntity.badRequest().body(RespuestaDTO.builder()
                    .token("No se pudo actualizar la contraseña. Verifica el código o asegúrate de que la nueva contraseña sea diferente a la actual.")
                    .build());
        }
    }
}