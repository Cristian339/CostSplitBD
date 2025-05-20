package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Objeto de Transferencia de Datos para representar un detalle de gasto.
 * Este DTO se utiliza para transferir información sobre un detalle de gasto, incluyendo el ID, ID del gasto,
 * ID del usuario, nombre del usuario e importe.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleGastoDTO {
    /**
     * El ID del detalle de gasto.
     */
    private Long id;

    /**
     * El ID del gasto al que pertenece este detalle.
     */
    private Long gastoId;

    /**
     * El ID del usuario asociado a este detalle.
     */
    private Long usuarioId;

    /**
     * El nombre del usuario asociado a este detalle.
     */
    private String usuarioNombre;

    /**
     * El importe asignado al usuario en este detalle.
     */
    private BigDecimal importe;
}