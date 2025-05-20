package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.costsplitbd.enumerados.Divisa;
import org.example.costsplitbd.enumerados.MetodoPago;
import org.example.costsplitbd.enumerados.MetodoReparticion;
import org.example.costsplitbd.enumerados.TipoGasto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Objeto de Transferencia de Datos para representar un gasto.
 * Este DTO se utiliza para transferir información sobre un gasto, incluyendo el ID, ID del grupo, descripción, monto total, fecha, ID del pagador, tipo de gasto, método de pago, método de repartición, categoría y divisa.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastoDTO {
    /**
     * El ID del gasto.
     */
    private Long id;

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
    private TipoGasto tipoGasto;

    /**
     * El método de pago.
     */
    private MetodoPago metodoPago;

    /**
     * El método de repartición.
     */
    private MetodoReparticion metodoReparticion;

    /**
     * El ID de la categoría.
     */
    private Long categoriaId;

    /**
     * El nombre de la categoría.
     */
    private String categoriaNombre;

    /**
     * La divisa del gasto.
     */
    private Divisa divisa;
}