package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.costsplitbd.enumerados.EstadoLiquidacion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa una liquidación entre usuarios en la aplicación de división de costos.
 * Esta entidad registra los pagos realizados entre usuarios para saldar deudas.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "liquidacion", schema = "costsplit")
public class Liquidacion {
    /**
     * El identificador único para la liquidación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * El usuario que realiza el pago.
     */
    @ManyToOne
    @JoinColumn(name = "pagador_id", nullable = false)
    private Usuario pagador;

    /**
     * El usuario que recibe el pago.
     */
    @ManyToOne
    @JoinColumn(name = "receptor_id", nullable = false)
    private Usuario receptor;

    /**
     * El monto de la liquidación.
     */
    @Column(name = "importe", nullable = false)
    private BigDecimal importe;

    /**
     * La fecha en que se realizó la liquidación.
     */
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;


    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoLiquidacion estado;


    /**
     * El grupo al que pertenece esta liquidación.
     */
    @ManyToOne
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;
}