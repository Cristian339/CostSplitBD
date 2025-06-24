package org.example.costsplitbd.services;

import lombok.RequiredArgsConstructor;
import org.example.costsplitbd.dto.CambiarPasswordDTO;
import org.example.costsplitbd.dto.RecuperarPasswordDTO;
import org.example.costsplitbd.dto.VerificarCodigoDTO;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RecuperacionPasswordService {

    private final UsuarioRepository usuarioRepository;
    private final JavaMailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    // Almacena los códigos de verificación: email -> {código, timestamp}
    private final Map<String, Map<String, Object>> codigosVerificacion = new HashMap<>();

    public boolean solicitarRecuperacion(RecuperarPasswordDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(dto.getEmail());
        if (usuarioOpt.isEmpty()) {
            return false;
        }

        String codigo = generarCodigo();

        // Guardar código con timestamp
        Map<String, Object> datosCodigo = new HashMap<>();
        datosCodigo.put("codigo", codigo);
        datosCodigo.put("timestamp", System.currentTimeMillis());
        codigosVerificacion.put(dto.getEmail(), datosCodigo);

        // Enviar email
        enviarCodigoPorEmail(dto.getEmail(), codigo);

        return true;
    }

    public boolean verificarCodigo(VerificarCodigoDTO dto) {
        Map<String, Object> datosCodigo = codigosVerificacion.get(dto.getEmail());

        if (datosCodigo == null) {
            return false;
        }

        String codigoAlmacenado = (String) datosCodigo.get("codigo");
        long timestamp = (long) datosCodigo.get("timestamp");

        // Verificar que el código no haya expirado (15 minutos de validez)
        boolean codigoValido = codigoAlmacenado.equals(dto.getCodigo()) &&
                (System.currentTimeMillis() - timestamp) <= TimeUnit.MINUTES.toMillis(15);

        return codigoValido;
    }

    public boolean cambiarPassword(CambiarPasswordDTO dto) {
        // Verificar que el código sea válido
        if (!verificarCodigo(new VerificarCodigoDTO(dto.getEmail(), dto.getCodigo()))) {
            return false;
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(dto.getEmail());
        if (usuarioOpt.isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar que la nueva contraseña no sea igual a la anterior
        if (passwordEncoder.matches(dto.getNuevaContrasenia(), usuario.getContrasenia())) {
            return false; // La nueva contraseña es igual a la actual
        }

        // Actualizar contraseña
        usuario.setContrasenia(passwordEncoder.encode(dto.getNuevaContrasenia()));
        usuarioRepository.save(usuario);

        // Eliminar código una vez usado
        codigosVerificacion.remove(dto.getEmail());

        return true;
    }

    private String generarCodigo() {
        SecureRandom random = new SecureRandom();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            codigo.append(random.nextInt(10));
        }

        return codigo.toString();
    }

    private void enviarCodigoPorEmail(String email, String codigo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de recuperación de contraseña");
        message.setText("Tu código para recuperar la contraseña es: " + codigo +
                "\n\nEste código expirará en 15 minutos.");

        emailSender.send(message);
    }
}