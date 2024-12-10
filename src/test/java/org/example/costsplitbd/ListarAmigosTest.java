package org.example.costsplitbd;

import jakarta.transaction.Transactional;
import org.example.costsplitbd.dto.UsuarioDTO;
import org.example.costsplitbd.models.Grupo;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.GrupoRepository;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.example.costsplitbd.services.GrupoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ListarAmigosTest {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private Long idGrupo;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setNombre("Nombre");
        usuario.setApellidos("Apellidos");
        usuario.setEmail("email@example.com");
        usuario.setContrasenia("1234");
        usuario.setUrlImg("http://example.com/img.jpg");
        usuario.setEsAdmin(false);
        usuarioRepository.save(usuario);

        // Retrieve the saved user to ensure it is managed by the current session
        Usuario managedUsuario = usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo de prueba");
        grupo.setDescripcion("Descripción del grupo de prueba");
        grupo.setImagenUrl("http://example.com/imgGrupo.jpg");
        grupo.setFechaCreacion(LocalDateTime.now());
        grupo.setUsuarios(new HashSet<>(Collections.singletonList(managedUsuario)));
        grupoRepository.save(grupo);
        idGrupo = grupo.getId();
    }

    @Test
    @DisplayName("Test 1 -> Ver participantes de un grupo con ID de grupo válido")
    @Tag("Participante")
    public void testVerParticipantesGrupoConIdGrupoValido() {
        // WHEN
        List<UsuarioDTO> participantes = grupoService.verParticipantesGrupo(idGrupo);

        // THEN
        assertNotNull(participantes);
        assertFalse(participantes.isEmpty());
    }

    @Test
    @DisplayName("Test 2 -> Ver participantes de un grupo con ID de grupo inválido")
    @Tag("Participante")
    public void testVerParticipantesGrupoConIdGrupoInvalido() {
        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> grupoService.verParticipantesGrupo(-1L));
    }

    @Test
    @DisplayName("Test 3 -> Ver participantes de un grupo sin participantes")
    @Tag("Participante")
    public void testVerParticipantesGrupoSinParticipantes() {
        // GIVEN
        Grupo grupoSinParticipantes = new Grupo();
        grupoSinParticipantes.setNombre("Grupo sin participantes");
        grupoSinParticipantes.setDescripcion("Descripción del grupo sin participantes");
        grupoSinParticipantes.setImagenUrl("http://example.com/imgGrupoSinParticipantes.jpg");
        grupoSinParticipantes.setFechaCreacion(LocalDateTime.now());
        grupoSinParticipantes.setUsuarios(new HashSet<>());
        grupoRepository.save(grupoSinParticipantes);
        Long idGrupoSinParticipantes = grupoSinParticipantes.getId();

        // WHEN
        List<UsuarioDTO> participantes = grupoService.verParticipantesGrupo(idGrupoSinParticipantes);

        // THEN
        assertNotNull(participantes);
        assertTrue(participantes.isEmpty());
    }
}