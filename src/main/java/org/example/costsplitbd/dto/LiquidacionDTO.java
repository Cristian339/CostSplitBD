package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.costsplitbd.enumerados.EstadoLiquidacion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Objeto de Transferencia de Datos para representar una liquidación.
 * Este DTO se utiliza para transferir información sobre una liquidación, incluyendo el ID, información del pagador y receptor,
 * importe, fecha, información del grupo y estado de la liquidación.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiquidacionDTO {
    /**
     * El ID de la liquidación.
     */
    private Long id;

    /**
     * El ID del usuario pagador.
     */
    private Long pagadorId;

    /**
     * El nombre del usuario pagador.
     */
    private String pagadorNombre;

    /**
     * El ID del usuario receptor.
     */
    private Long receptorId;

    /**
     * El nombre del usuario receptor.
     */
    private String receptorNombre;

    /**
     * El importe de la liquidación.
     */
    private BigDecimal importe;

    /**
     * La fecha de la liquidación.
     */
    private LocalDateTime fecha;

    /**
     * El ID del grupo al que pertenece la liquidación.
     */
    private Long grupoId;

    /**
     * El nombre del grupo al que pertenece la liquidación.
     */
    private String grupoNombre;

    /**
     * El estado de la liquidación.
     */
    private EstadoLiquidacion estado;
}