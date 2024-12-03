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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class GroupServiceTest {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Grupo grupo;

    @BeforeEach
    @Transactional
    public void inicializarBaseDatos() {
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Usuario1");
        usuario1.setApellidos("Apellido1");
        usuario1.setEmail("usuario1@example.com");
        usuario1 = usuarioRepository.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Usuario2");
        usuario2.setApellidos("Apellido2");
        usuario2.setEmail("usuario2@example.com");
        usuario2 = usuarioRepository.save(usuario2);

        Usuario usuario3 = new Usuario();
        usuario3.setNombre("Usuario3");
        usuario3.setApellidos("Apellido3");
        usuario3.setEmail("usuario3@example.com");
        usuario3 = usuarioRepository.save(usuario3);

        grupo = new Grupo();
        grupo = grupoRepository.save(grupo);
    }

    @Test
    public void testCrearGrupoNegativoUnitario() {
        AniadirParticipanteDTO participantes = new AniadirParticipanteDTO();
        participantes.setIdUsuarios(Arrays.asList(1L, 2L, 3L));
        GrupoDetalladoDTO grupoDetalladoDTO = grupoService.aniadirParticipantes(grupo.getId(), participantes);
        assertNotNull(grupoDetalladoDTO);
    }

//    @Test
//    public void testCrearGrupoPositivoUnitario() {
//        AniadirParticipanteDTO participantes = new AniadirParticipanteDTO();
//        participantes.setIdUsuarios(Arrays.asList(1L, 2L, 3L));
//        GrupoDetalladoDTO grupoDetalladoDTO = grupoService.aniadirParticipantes(grupo.getId(), participantes);
//        assertNotNull(grupoDetalladoDTO);
//    }
}