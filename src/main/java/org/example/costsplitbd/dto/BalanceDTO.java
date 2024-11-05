package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Objeto de Transferencia de Datos para representar el balance de un usuario.
 * Este DTO se utiliza para transferir información del balance, incluyendo el ID del usuario, el nombre del usuario y el importe.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDTO {
    /**
     * El ID del usuario.
     */
    private Long usuarioId;

    /**
     * El nombre del usuario.
     */
    private String usuarioNombre;

    /**
     * El importe del balance.
     */
    private BigDecimal importe;
}