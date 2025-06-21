package org.example.costsplitbd.controllers;

import lombok.RequiredArgsConstructor;
import org.example.costsplitbd.dto.LoginDTO;
import org.example.costsplitbd.dto.RespuestaDTO;
import org.example.costsplitbd.dto.UsuarioDTO;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.security.JWTService;
import org.example.costsplitbd.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registro(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            // Autenticación usando AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getContrasenia()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Obtener usuario y generar token
            Usuario usuario = usuarioService.getUsuarioByEmail(loginDTO.getEmail());
            String token = jwtService.generateToken(usuario);

            // Construir respuesta
            RespuestaDTO respuesta = RespuestaDTO.builder()
                    .token(token)
                    .tipo("Bearer")
                    .id(usuario.getId())
                    .nombre(usuario.getNombre())
                    .email(usuario.getEmail())
                    .build();

            return ResponseEntity.ok(respuesta);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(RespuestaDTO.builder()
                            .token("Credenciales inválidas")
                            .build());
        }
    }
}