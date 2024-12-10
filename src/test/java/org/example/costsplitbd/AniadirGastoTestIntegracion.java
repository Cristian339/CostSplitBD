package org.example.costsplitbd;

import org.example.costsplitbd.dto.GastoDTO;
import org.example.costsplitbd.repositories.GrupoRepository;
import org.example.costsplitbd.repositories.UsuarioRepository;
import org.example.costsplitbd.services.GrupoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class AniadirGastoTestIntegracion {

    @InjectMocks
    private GrupoService grupoService;

    @Mock
    private GrupoRepository grupoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Test de Integracion -> Añadir gasto con descripción nula")
    public void testAniadirGastoConDescripcionNulaIntegracion() {
        // GIVEN
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setDescripcion(null);
        gastoDTO.setMontoTotal(BigDecimal.TEN);
        gastoDTO.setFecha(LocalDateTime.now());
        gastoDTO.setIdGrupo(1L);
        gastoDTO.setIdPagador(1L);
        gastoDTO.setTipo("Comida");

        // WHEN & THEN
        Exception exception = assertThrows(Exception.class, () -> grupoService.aniadirGasto(gastoDTO));
        assertEquals("La descripción no puede estar vacía", exception.getMessage());
        verifyNoInteractions(grupoRepository, usuarioRepository);
    }
}