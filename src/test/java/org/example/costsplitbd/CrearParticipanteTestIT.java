/*
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
public class CrearParticipanteTestIT {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private Grupo grupo;

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

        grupo = new Grupo();
        grupo.setNombre("Grupo de prueba");
        grupo.setDescripcion("Descripción del grupo de prueba");
        grupo.setImagenUrl("http://example.com/imgGrupo.jpg");
        grupo.setFechaCreacion(LocalDateTime.now());
        grupo.setUsuarios(new HashSet<>());
        grupoRepository.save(grupo);
    }

    @Test
    @DisplayName("Test de Integración -> Añadir participante con datos válidos")
    @Tag("Participante")
    public void testAniadirParticipanteConDatosValidosIntegracion() {
        // GIVEN
        AniadirParticipanteDTO aniadirParticipanteDTO = new AniadirParticipanteDTO();
        aniadirParticipanteDTO.setIdGrupo(grupo.getId());
        aniadirParticipanteDTO.setIdUsuario(usuario.getId());

        // WHEN
        GrupoDetalladoDTO grupoDetalladoDTO = grupoService.aniadirParticipanteGrupo(aniadirParticipanteDTO);

        // THEN
        assertNotNull(grupoDetalladoDTO);
        assertTrue(grupoDetalladoDTO.getUsuarios().stream().anyMatch(u -> u.getId().equals(usuario.getId())));
    }
}*/
