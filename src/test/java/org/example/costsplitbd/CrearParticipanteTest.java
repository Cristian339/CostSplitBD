package org.example.costsplitbd;

import jakarta.transaction.Transactional;
import org.example.costsplitbd.dto.AniadirParticipanteDTO;
import org.example.costsplitbd.dto.GrupoDetalladoDTO;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class CrearParticipanteTest {

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
    @DisplayName("Test 1 -> Añadir participantes con ID de grupo válido")
    @Tag("Participante")
    public void testAniadirParticipantesConIdGrupoValido() {
        // GIVEN
        AniadirParticipanteDTO aniadirParticipanteDTO = new AniadirParticipanteDTO();
        aniadirParticipanteDTO.setIdUsuarios(Collections.singletonList(usuario.getId()));

        // WHEN
        GrupoDetalladoDTO grupoDetalladoDTO = grupoService.aniadirParticipantes(idGrupo, aniadirParticipanteDTO);

        // THEN
        assertNotNull(grupoDetalladoDTO);
        assertTrue(grupoDetalladoDTO.getUsuarios().stream().anyMatch(u -> u.getId().equals(usuario.getId())));
    }

    @Test
    @DisplayName("Test 2 -> Añadir participantes con ID de grupo inválido")
    @Tag("Participante")
    public void testAniadirParticipantesConIdGrupoInvalido() {
        // GIVEN
        AniadirParticipanteDTO aniadirParticipanteDTO = new AniadirParticipanteDTO();
        aniadirParticipanteDTO.setIdUsuarios(Collections.singletonList(usuario.getId()));

        // WHEN & THEN
        assertThrows(Exception.class, () -> grupoService.aniadirParticipantes(-1L, aniadirParticipanteDTO));
    }

    @Test
    @DisplayName("Test 3 -> Añadir participantes válidos")
    @Tag("Participante")
    public void testAniadirParticipantesConParticipantesValidos() {
        // GIVEN
        AniadirParticipanteDTO aniadirParticipanteDTO = new AniadirParticipanteDTO();
        aniadirParticipanteDTO.setIdUsuarios(Collections.singletonList(usuario.getId()));

        // WHEN
        GrupoDetalladoDTO grupoDetalladoDTO = grupoService.aniadirParticipantes(idGrupo, aniadirParticipanteDTO);

        // THEN
        assertNotNull(grupoDetalladoDTO);
        assertTrue(grupoDetalladoDTO.getUsuarios().stream().anyMatch(u -> u.getId().equals(usuario.getId())));
    }

    @Test
    @DisplayName("Test 4 -> Añadir participantes inválidos")
    @Tag("Participante")
    public void testAniadirParticipantesConParticipantesInvalidos() {
        // GIVEN
        AniadirParticipanteDTO aniadirParticipanteDTO = new AniadirParticipanteDTO();
        aniadirParticipanteDTO.setIdUsuarios(Collections.singletonList(-1L));

        // WHEN
        GrupoDetalladoDTO grupoDetalladoDTO = grupoService.aniadirParticipantes(idGrupo, aniadirParticipanteDTO);

        // THEN
        assertNotNull(grupoDetalladoDTO);
        assertFalse(grupoDetalladoDTO.getUsuarios().stream().anyMatch(u -> u.getId().equals(-1L)));
    }
}