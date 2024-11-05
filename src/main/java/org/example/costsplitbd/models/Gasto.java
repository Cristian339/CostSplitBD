package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa un gasto en la aplicación de división de costos.
 * Esta entidad se utiliza para registrar los gastos realizados por los usuarios dentro de un grupo.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "grupo")
@Entity
@Table(name = "gasto", schema = "costsplit", catalog = "postgres")
public class Gasto {
    /**
     * El identificador único para el gasto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * El grupo al que pertenece este gasto.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    /**
     * La descripción del gasto.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * El monto total del gasto.
     */
    @Column(name = "monto_total", nullable = false)
    private BigDecimal montoTotal;

    /**
     * La fecha en que se realizó el gasto.
     */
    @Column(name = "fecha", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime fecha;

    /**
     * El tipo de gasto.
     */
    @Column(name = "tipo_gasto", nullable = false)
    private String tipo;

    /**
     * El usuario que realizó el gasto.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_pagador", nullable = false)
    private Usuario pagador;

    /**
     * Enum para los tipos de gasto.
     */
    public enum TipoGasto {
        DEUDA, TRANSFERENCIA
    }
}