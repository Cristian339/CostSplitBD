package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastoDTO {
    private Long idGrupo;
    private String descripcion;
    private BigDecimal montoTotal;
    private LocalDateTime fecha;
    private String tipo;
    private Long idPagador;


}
