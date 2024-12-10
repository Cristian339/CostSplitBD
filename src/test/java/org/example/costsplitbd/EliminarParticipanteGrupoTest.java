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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class EliminarParticipanteGrupoTest {

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
    @DisplayName("Test 1 -> Eliminar participante con ID de grupo válido")
    @Tag("Participante")
    public void testEliminarParticipanteConIdGrupoValido() {
        // GIVEN
        AniadirParticipanteDTO aniadirParticipanteDTO = new AniadirParticipanteDTO();
        aniadirParticipanteDTO.setIdUsuarios(List.of(usuario.getId()));

        // WHEN
        grupoService.eliminarParticipante(idGrupo, aniadirParticipanteDTO);

        // THEN
        GrupoDetalladoDTO grupoDetalladoDTO = grupoService.verGrupo(idGrupo);
        assertNotNull(grupoDetalladoDTO);
        assertFalse(grupoDetalladoDTO.getUsuarios().stream().anyMatch(u -> u.getId().equals(usuario.getId())));
    }

    @Test
    @DisplayName("Test 2 -> Eliminar participante con ID de grupo inválido")
    @Tag("Participante")
    public void testEliminarParticipanteConIdGrupoInvalido() {
        // GIVEN
        AniadirParticipanteDTO aniadirParticipanteDTO = new AniadirParticipanteDTO();
        aniadirParticipanteDTO.setIdUsuarios(List.of(usuario.getId()));

        // WHEN & THEN
        assertThrows(Exception.class, () -> grupoService.eliminarParticipante(-1L, aniadirParticipanteDTO));
    }

    @Test
    @DisplayName("Test 3 -> Eliminar participante con ID de usuario válido")
    @Tag("Participante")
    public void testEliminarParticipanteConIdUsuarioValido() {
        // GIVEN
        AniadirParticipanteDTO aniadirParticipanteDTO = new AniadirParticipanteDTO();
        aniadirParticipanteDTO.setIdUsuarios(List.of(usuario.getId()));

        // WHEN
        grupoService.eliminarParticipante(idGrupo, aniadirParticipanteDTO);

        // THEN
        GrupoDetalladoDTO grupoDetalladoDTO = grupoService.verGrupo(idGrupo);
        assertNotNull(grupoDetalladoDTO);
        assertFalse(grupoDetalladoDTO.getUsuarios().stream().anyMatch(u -> u.getId().equals(usuario.getId())));
    }

    @Test
    @DisplayName("Test 4 -> Eliminar participante con ID de usuario inválido")
    @Tag("Participante")
    public void testEliminarParticipanteConIdUsuarioInvalido() {
        // GIVEN
        AniadirParticipanteDTO aniadirParticipanteDTO = new AniadirParticipanteDTO();
        aniadirParticipanteDTO.setIdUsuarios(List.of(-1L));

        // WHEN & THEN
        assertThrows(Exception.class, () -> grupoService.eliminarParticipante(idGrupo, aniadirParticipanteDTO));
    }

    @Test
    @DisplayName("Test 5 -> Eliminar participante que no pertenece al grupo")
    @Tag("Participante")
    public void testEliminarParticipanteQueNoPerteneceAlGrupo() {
        // GIVEN
        Usuario otroUsuario = new Usuario();
        otroUsuario.setNombre("Otro Nombre");
        otroUsuario.setApellidos("Otros Apellidos");
        otroUsuario.setEmail("otroemail@example.com");
        otroUsuario.setContrasenia("5678");
        otroUsuario.setUrlImg("http://example.com/otroimg.jpg");
        otroUsuario.setEsAdmin(false);
        usuarioRepository.save(otroUsuario);

        AniadirParticipanteDTO aniadirParticipanteDTO = new AniadirParticipanteDTO();
        aniadirParticipanteDTO.setIdUsuarios(List.of(otroUsuario.getId()));

        // WHEN & THEN
        assertThrows(Exception.class, () -> grupoService.eliminarParticipante(idGrupo, aniadirParticipanteDTO));
    }
}