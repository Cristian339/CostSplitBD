package org.example.costsplitbd;

import jakarta.transaction.Transactional;
import org.example.costsplitbd.dto.GrupoDTO;
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
public class ListarGruposTest {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private Long idUsuario;

    @BeforeEach
    public void inicializarDatos() {
        usuario = new Usuario();
        usuario.setNombre("Nombre");
        usuario.setApellidos("Apellidos");
        usuario.setEmail("email@example.com");
        usuario.setContrasenia("1234");
        usuario.setUrlImg("http://example.com/img.jpg");
        usuario.setEsAdmin(false);
        usuarioRepository.save(usuario);
        idUsuario = usuario.getId();


        Usuario managedUsuario = usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo de prueba");
        grupo.setDescripcion("Descripción del grupo de prueba");
        grupo.setImagenUrl("http://example.com/imgGrupo.jpg");
        grupo.setFechaCreacion(LocalDateTime.now());
        grupo.setUsuarios(new HashSet<>(Collections.singletonList(managedUsuario)));
        grupoRepository.save(grupo);
    }

    @Test
    @DisplayName("Test 1 -> Listar grupos con ID de usuario válido")
    @Tag("Grupo")
    public void testListarGruposConIdUsuarioValido() {
        // WHEN
        List<GrupoDTO> grupos = grupoService.listarGrupos(idUsuario);

        // THEN
        assertNotNull(grupos);
        assertFalse(grupos.isEmpty());
    }

    @Test
    @DisplayName("Test 2 -> Listar grupos con ID de usuario inválido")
    @Tag("Grupo")
    public void testListarGruposConIdUsuarioInvalido() {
        // WHEN
        List<GrupoDTO> grupos = grupoService.listarGrupos(-1L);

        // THEN
        assertNotNull(grupos);
        assertTrue(grupos.isEmpty());
    }

    @Test
    @DisplayName("Test 3 -> Listar grupos sin grupos")
    @Tag("Grupo")
    public void testListarGruposSinGrupos() {
        // GIVEN
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Nuevo Nombre");
        nuevoUsuario.setApellidos("Nuevos Apellidos");
        nuevoUsuario.setEmail("nuevoemail@example.com");
        nuevoUsuario.setContrasenia("5678");
        nuevoUsuario.setUrlImg("http://example.com/nuevoimg.jpg");
        nuevoUsuario.setEsAdmin(false);
        usuarioRepository.save(nuevoUsuario);
        Long idNuevoUsuario = nuevoUsuario.getId();

        // WHEN
        List<GrupoDTO> grupos = grupoService.listarGrupos(idNuevoUsuario);

        // THEN
        assertNotNull(grupos);
        assertTrue(grupos.isEmpty());
    }
}