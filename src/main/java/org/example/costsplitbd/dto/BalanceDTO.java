package org.example.costsplitbd.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.apache.logging.log4j.message.Message;

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