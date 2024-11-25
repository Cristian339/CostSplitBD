package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa un gasto en la aplicación de división de costos.
 * Esta entidad se utiliza para registrar los gastos realizados por los usuarios dentro de un grupo.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"grupo", "usuarios"})
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
     * Los usuarios asociados con el gasto.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "usuario_gasto",
            joinColumns = @JoinColumn(name = "id_gasto"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<Usuario> usuarios = new HashSet<>();

    /**
     * Enum para los tipos de gasto.
     */
    public enum TipoGasto {
        DEUDA, TRANSFERENCIA
    }
}