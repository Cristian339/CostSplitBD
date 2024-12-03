package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Representa un balance en la aplicación de división de costos.
 * Esta entidad se utiliza para rastrear el balance de cada usuario dentro de un grupo.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "balance", schema = "costsplit")
public class Balance {
    /**
     * El identificador único para el balance.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * El grupo al que pertenece este balance.
     */
    @ManyToOne
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    /**
     * El usuario al que pertenece este balance.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * La cantidad de dinero en el balance.
     */
    @Column(name = "importe", nullable = false)
    private BigDecimal importe;
}