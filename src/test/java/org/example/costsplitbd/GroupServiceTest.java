package org.example.costsplitbd;

import jakarta.transaction.Transactional;
import org.example.costsplitbd.dto.CrearGrupoDTO;
import org.example.costsplitbd.dto.GrupoDTO;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

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

    private Usuario usuario;

    @BeforeEach
    @Transactional
    public void inicializarBaseDatos() {
        usuario = new Usuario();
        usuario.setNombre("Usuario1");
        usuario.setApellidos("Apellido1");
        usuario.setEmail("usuario1@example.com");
        usuario.setContrasenia("password1");
        usuario.setUrlImg("http://example.com/img1.jpg"); // Set a non-null value for the url_img column
        usuario = usuarioRepository.save(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Usuario2");
        usuario2.setApellidos("Apellido2");
        usuario2.setEmail("usuario2@example.com");
        usuario2.setContrasenia("password2");
        usuario2.setUrlImg("http://example.com/img2.jpg"); // Set a non-null value for the url_img column
        usuario2 = usuarioRepository.save(usuario2);

        Usuario usuario3 = new Usuario();
        usuario3.setNombre("Usuario3");
        usuario3.setApellidos("Apellido3");
        usuario3.setEmail("usuario3@example.com");
        usuario3.setContrasenia("password3");
        usuario3.setUrlImg("http://example.com/img3.jpg"); // Set a non-null value for the url_img column
        usuario3 = usuarioRepository.save(usuario3);
    }

    @Test
    @DisplayName("Test 1 -> Crear grupo con datos incorrectos")
    @Tag("Grupo")
    public void testCrearGrupoNegativoUnitario() {
        // GIVEN
        CrearGrupoDTO grupoInvalido = new CrearGrupoDTO();
        grupoInvalido.setNombre(""); // Nombre vacío

        // WHEN
        Exception exception = assertThrows(Exception.class, () -> grupoService.crearGrupo(grupoInvalido, usuario));

        // THEN
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("El nombre del grupo es obligatorio"));
    }

    @Test
    @DisplayName("Test 2 -> Crear grupo con datos correctos")
    @Tag("Grupo")
    public void testCrearGrupoPositivoUnitario() throws Exception {
        // GIVEN
        CrearGrupoDTO grupoValido = new CrearGrupoDTO();
        grupoValido.setNombre("Grupo Valido");
        grupoValido.setImagenUrl("http://example.com/img.jpg");
        grupoValido.setDescripcion("Descripción del grupo");
        grupoValido.setParticipantes(new ArrayList<>(Arrays.asList(usuario)));

        // WHEN
        GrupoDTO grupoCreado = grupoService.crearGrupo(grupoValido, usuario);

        // THEN
        assertNotNull(grupoCreado);
        assertEquals("Grupo Valido", grupoCreado.getNombre());
        assertEquals("Descripción del grupo", grupoCreado.getDescripcion());
        assertNotNull(grupoCreado.getFechaCreacion());
    }
}