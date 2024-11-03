package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "grupo")
@Entity
@Table(name = "gasto", schema = "costsplit", catalog = "postgres")
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "monto_total", nullable = false)
    private BigDecimal montoTotal;

    @Column(name = "fecha", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime fecha;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoGasto tipo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_pagador", nullable = false)
    private Usuario pagador;




    public enum TipoGasto {
        DEUDA, TRANSFERENCIA
    }
}