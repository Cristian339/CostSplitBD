package org.example.costsplitbd;

import jakarta.transaction.Transactional;
import org.example.costsplitbd.dto.CrearGrupoDTO;
import org.example.costsplitbd.dto.GrupoDTO;
import org.example.costsplitbd.dto.UsuarioDTO;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.example.costsplitbd.services.GrupoService;
import org.example.costsplitbd.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class GroupServiceTest {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;



    @Test
    @DisplayName("Test 1 -> Crear grupo con nombre vacío")
    @Tag("Grupo")
    public void testCrearGrupoConNombreVacio() throws Exception {
        // GIVEN
        UsuarioDTO usuario = usuarioService.crearUsuario(new UsuarioDTO(null, "Usuario1", "Apellido1", "usuario1@example.com", "http://example.com/img1.jpg"));
        assertNotNull(usuario, "El usuario no se creó correctamente");

        CrearGrupoDTO grupoInvalido = new CrearGrupoDTO();
        grupoInvalido.setNombre("");

        // WHEN & THEN
        Exception exception = assertThrows(Exception.class, () -> grupoService.crearGrupo(grupoInvalido, Objects.requireNonNull(usuarioRepository.findById(usuario.getId()).orElse(null))));
        assertNotNull(exception.getMessage());

    }

    @Test
    @DisplayName("Test 2 -> Crear grupo con usuario inexistente")
    @Tag("Grupo")
    public void testCrearGrupoConUsuarioInexistente() throws Exception {
        // GIVEN
        CrearGrupoDTO grupoConUsuarioInvalido = new CrearGrupoDTO();
        grupoConUsuarioInvalido.setNombre("Grupo con Usuario Invalido");
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setId(-1L);

        // WHEN & THEN
        Exception exception = assertThrows(Exception.class, () -> grupoService.crearGrupo(grupoConUsuarioInvalido, usuarioInvalido));
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Test 3 -> Crear grupo con datos correctos")
    @Tag("Grupo")
    public void testCrearGrupoPositivoUnitario() throws Exception {
        // GIVEN
        UsuarioDTO usuarioDTO = usuarioService.crearUsuario(new UsuarioDTO(null, "Usuario1", "Apellido1", "usuario1@example.com", "http://example.com/img1.jpg"));
        assertNotNull(usuarioDTO, "El usuario no se creó correctamente");

        Usuario usuario = usuarioRepository.findById(usuarioDTO.getId()).orElse(null);
        assertNotNull(usuario, "El usuario no se encontró en la base de datos");

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