package org.example.costsplitbd;

import org.example.costsplitbd.dto.GastoDTO;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
public class AniadirGastoTest {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Grupo grupo;
    private Usuario pagador;

    @BeforeEach
    public void inicializarDatos() {
        grupo = new Grupo();
        grupo.setNombre("Grupo de prueba");
        grupo.setDescripcion("Descripción del grupo de prueba");
        grupo.setFechaCreacion(LocalDateTime.now());
        grupo.setImagenUrl("http://example.com/imgGrupo.jpg");
        grupoRepository.save(grupo);

        pagador = new Usuario();
        pagador.setNombre("Nombre");
        pagador.setApellidos("Apellidos");
        pagador.setEmail("email@example.com");
        pagador.setContrasenia("1234");
        usuarioRepository.save(pagador);
    }

    @Test
    @DisplayName("Test 1 -> Añadir gasto con descripción nula")
    @Tag("Gasto")
    public void testAniadirGastoConDescripcionNula() {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setDescripcion(null);
        gastoDTO.setMontoTotal(BigDecimal.TEN);
        gastoDTO.setFecha(LocalDateTime.now());
        gastoDTO.setIdGrupo(grupo.getId());
        gastoDTO.setIdPagador(pagador.getId());
        gastoDTO.setTipo("Comida");

        assertThrows(Exception.class, () -> grupoService.aniadirGasto(gastoDTO), "La descripción no puede estar vacía");
    }

    @Test
    @DisplayName("Test 2 -> Añadir gasto con monto total negativo")
    @Tag("Gasto")
    public void testAniadirGastoConMontoTotalNegativo() {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setDescripcion("Descripción");
        gastoDTO.setMontoTotal(BigDecimal.valueOf(-10));
        gastoDTO.setFecha(LocalDateTime.now());
        gastoDTO.setIdGrupo(grupo.getId());
        gastoDTO.setIdPagador(pagador.getId());
        gastoDTO.setTipo("Comida");

        assertThrows(Exception.class, () -> grupoService.aniadirGasto(gastoDTO), "El monto total no puede ser negativo");
    }

    @Test
    @DisplayName("Test 3 -> Añadir gasto con fecha nula")
    @Tag("Gasto")
    public void testAniadirGastoConFechaNula() {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setDescripcion("Descripción");
        gastoDTO.setMontoTotal(BigDecimal.TEN);
        gastoDTO.setFecha(null);
        gastoDTO.setIdGrupo(grupo.getId());
        gastoDTO.setIdPagador(pagador.getId());
        gastoDTO.setTipo("Comida");

        assertThrows(Exception.class, () -> grupoService.aniadirGasto(gastoDTO), "La fecha es inválida");
    }

    @Test
    @DisplayName("Test 4 -> Añadir gasto con grupo inexistente")
    @Tag("Gasto")
    public void testAniadirGastoConGrupoInexistente() {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setDescripcion("Descripción");
        gastoDTO.setMontoTotal(BigDecimal.TEN);
        gastoDTO.setFecha(LocalDateTime.now());
        gastoDTO.setIdGrupo(-1L);
        gastoDTO.setIdPagador(pagador.getId());
        gastoDTO.setTipo("Comida");

        assertThrows(Exception.class, () -> grupoService.aniadirGasto(gastoDTO), "El grupo no existe");
    }

    @Test
    @DisplayName("Test 5 -> Añadir gasto con pagador inexistente")
    @Tag("Gasto")
    public void testAniadirGastoConPagadorInexistente() {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setDescripcion("Descripción");
        gastoDTO.setMontoTotal(BigDecimal.TEN);
        gastoDTO.setFecha(LocalDateTime.now());
        gastoDTO.setIdGrupo(grupo.getId());
        gastoDTO.setIdPagador(-1L);
        gastoDTO.setTipo("Comida");

        assertThrows(Exception.class, () -> grupoService.aniadirGasto(gastoDTO), "El pagador no existe");
    }

    @Test
    @DisplayName("Test 6 -> Añadir gasto con tipo nulo")
    @Tag("Gasto")
    public void testAniadirGastoConTipoNulo() {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setDescripcion("Descripción");
        gastoDTO.setMontoTotal(BigDecimal.TEN);
        gastoDTO.setFecha(LocalDateTime.now());
        gastoDTO.setIdGrupo(grupo.getId());
        gastoDTO.setIdPagador(pagador.getId());
        gastoDTO.setTipo(null);

        assertThrows(Exception.class, () -> grupoService.aniadirGasto(gastoDTO), "El tipo de gasto es inválido");
    }
}