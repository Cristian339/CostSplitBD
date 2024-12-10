package org.example.costsplitbd;

import jakarta.transaction.Transactional;
import org.example.costsplitbd.dto.BalanceDTO;
import org.example.costsplitbd.models.Balance;
import org.example.costsplitbd.models.Grupo;
import org.example.costsplitbd.models.Usuario;
import org.example.costsplitbd.repositories.BalanceRepository;
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
public class VerBalancesTestIntegracion {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BalanceRepository balanceRepository;

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

        Balance balance = new Balance();
        balance.setGrupo(grupo);
        balance.setUsuario(managedUsuario);
        balance.setImporte(BigDecimal.valueOf(100.00));
        balanceRepository.save(balance);
    }

    @Test
    @DisplayName("Test de Integración -> Ver balances con ID de grupo válido")
    @Tag("Balance")
    public void testVerBalancesConIdGrupoValidoIntegracion() {
        // WHEN
        List<BalanceDTO> balances = grupoService.verBalances(idGrupo);

        // THEN
        assertNotNull(balances);
        assertFalse(balances.isEmpty());
    }
}