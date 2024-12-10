package org.example.costsplitbd;

import jakarta.transaction.Transactional;
import org.example.costsplitbd.dto.GastoDTO;
import org.example.costsplitbd.models.Gasto;
import org.example.costsplitbd.models.Grupo;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.GastoRepository;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class VerGastosTest {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GastoRepository gastoRepository;

    private Usuario usuario;
    private Long idGrupo;

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


        Usuario managedUsuario = usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo de prueba");
        grupo.setDescripcion("Descripción del grupo de prueba");
        grupo.setImagenUrl("http://example.com/imgGrupo.jpg");
        grupo.setFechaCreacion(LocalDateTime.now());
        grupo.setUsuarios(new HashSet<>(Collections.singletonList(managedUsuario)));
        grupoRepository.save(grupo);
        idGrupo = grupo.getId();


        Gasto gasto = new Gasto();
        gasto.setGrupo(grupo);
        gasto.setDescripcion("Gasto de prueba");
        gasto.setMontoTotal(BigDecimal.valueOf(100.00));
        gasto.setFecha(LocalDateTime.now());
        gasto.setPagador(managedUsuario);
        gasto.setTipo("Tipo de gasto");
        gastoRepository.save(gasto);
    }

    @Test
    @DisplayName("Test 1 -> Ver gastos de un grupo con ID de grupo válido")
    @Tag("Gasto")
    public void testVerGastosConIdGrupoValido() {
        // WHEN
        List<GastoDTO> gastos = grupoService.verGastos(idGrupo);

        // THEN
        assertNotNull(gastos);
        assertFalse(gastos.isEmpty());
    }

    @Test
    @DisplayName("Test 2 -> Ver gastos de un grupo con ID de grupo inválido")
    @Tag("Gasto")
    public void testVerGastosConIdGrupoInvalido() {
        // WHEN
        List<GastoDTO> gastos = grupoService.verGastos(-1L);

        // THEN
        assertNotNull(gastos);
        assertTrue(gastos.isEmpty());
    }

    @Test
    @DisplayName("Test 3 -> Ver gastos de un grupo sin gastos")
    @Tag("Gasto")
    public void testVerGastosSinGastos() {
        // GIVEN
        Grupo grupoSinGastos = new Grupo();
        grupoSinGastos.setNombre("Grupo sin gastos");
        grupoSinGastos.setDescripcion("Descripción del grupo sin gastos");
        grupoSinGastos.setImagenUrl("http://example.com/imgGrupoSinGastos.jpg");
        grupoSinGastos.setFechaCreacion(LocalDateTime.now());
        grupoSinGastos.setUsuarios(new HashSet<>(Collections.singletonList(usuario)));
        grupoRepository.save(grupoSinGastos);
        Long idGrupoSinGastos = grupoSinGastos.getId();

        // WHEN
        List<GastoDTO> gastos = grupoService.verGastos(idGrupoSinGastos);

        // THEN
        assertNotNull(gastos);
        assertTrue(gastos.isEmpty());
    }
}