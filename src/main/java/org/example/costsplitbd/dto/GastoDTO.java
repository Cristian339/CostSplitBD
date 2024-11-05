package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Objeto de Transferencia de Datos para representar un gasto.
 * Este DTO se utiliza para transferir información sobre un gasto, incluyendo el ID del grupo, la descripción, el monto total, la fecha, el ID del pagador y el tipo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GastoDTO {
    /**
     * El ID del grupo.
     */
    private Long idGrupo;

    /**
     * La descripción del gasto.
     */
    private String descripcion;

    /**
     * El monto total del gasto.
     */
    private BigDecimal montoTotal;

    /**
     * La fecha del gasto.
     */
    private LocalDateTime fecha;

    /**
     * El ID del pagador.
     */
    private Long idPagador;

    /**
     * El tipo de gasto.
     */
    private String tipo;
}