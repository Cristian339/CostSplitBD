package org.example.costsplitbd;

import jakarta.transaction.Transactional;
import org.example.costsplitbd.dto.UsuarioDTO;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.example.costsplitbd.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ListarAmigosTestIT {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private Usuario amigo;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setNombre("Usuario");
        usuario.setApellidos("Apellidos");
        usuario.setEmail("usuario@example.com");
        usuario.setContrasenia("1234");
        usuario.setUrlImg("http://example.com/img.jpg");
        usuario.setEsAdmin(false);
        usuarioRepository.save(usuario);

        amigo = new Usuario();
        amigo.setNombre("Amigo");
        amigo.setApellidos("Apellidos");
        amigo.setEmail("amigo@example.com");
        amigo.setContrasenia("1234");
        amigo.setUrlImg("http://example.com/img.jpg");
        amigo.setEsAdmin(false);
        usuarioRepository.save(amigo);

        // Add amigo to usuario's friends list
        usuario.getAmigos().add(amigo);
        usuarioRepository.save(usuario);
    }

    @Test
    @DisplayName("Test de Integración -> Listar amigos con ID de usuario válido")
    @Tag("Amigos")
    public void testListarAmigosConIdUsuarioValidoIntegracion() {
        // WHEN
        List<UsuarioDTO> amigos = usuarioService.listarAmigos(usuario.getId());

        // THEN
        assertNotNull(amigos);
        assertEquals(1, amigos.size());
        assertEquals(amigo.getId(), amigos.get(0).getId());
    }
}