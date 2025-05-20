package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Representa el detalle de un gasto en la aplicación de división de costos.
 * Esta entidad se utiliza para registrar la cantidad específica que corresponde a cada usuario en un gasto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "detalle_gasto", schema = "costsplit")
public class DetalleGasto {
    /**
     * El identificador único para el detalle de gasto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * El gasto al que pertenece este detalle.
     */
    @ManyToOne
    @JoinColumn(name = "id_gasto", nullable = false)
    private Gasto gasto;

    /**
     * El usuario al que corresponde este detalle de gasto.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * La cantidad de dinero que corresponde a este usuario en el gasto.
     */
    @Column(name = "importe", nullable = false)
    private BigDecimal importe;
}