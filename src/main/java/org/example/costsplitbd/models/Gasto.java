package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.costsplitbd.enumerados.Divisa;
import org.example.costsplitbd.enumerados.MetodoPago;
import org.example.costsplitbd.enumerados.MetodoReparticion;
import org.example.costsplitbd.enumerados.TipoGasto;

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
@Table(name = "gasto", schema = "costsplit")
public class Gasto {
    /**
     * El identificador único para el gasto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_gasto", nullable = false)
    private TipoGasto tipoGasto;

    /**
     * El método de pago utilizado.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    /**
     * La divisa en la que se realizó el gasto.
     * Se permite nulo temporalmente para la migración.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "divisa", nullable = true) // CAMBIO AQUÍ: nullable = true
    private Divisa divisa;

    /**
     * El método de repartición del gasto entre los usuarios.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_reparticion")
    private MetodoReparticion metodoReparticion;

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
}