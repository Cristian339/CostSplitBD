/*
package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

*/
/**
 * Representa la participación de un usuario en un gasto en la aplicación de división de costos.
 * Esta entidad se utiliza para registrar la cantidad de dinero que cada usuario debe pagar por un gasto específico.
 *//*

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "participacion_gasto", schema = "costsplit", catalog = "postgres")
public class ParticipacionGasto {
    */
/**
     * El identificador único para la participación en el gasto.
     *//*

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    */
/**
     * El gasto al que pertenece esta participación.
     *//*

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_gasto", nullable = false)
    private Gasto gasto;

    */
/**
     * El usuario que participa en el gasto.
     *//*

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    */
/**
     * El monto individual que el usuario debe pagar por el gasto.
     *//*

    @Column(name = "monto_individual", nullable = false)
    private BigDecimal montoIndividual;

    Se esta pensando aun como implementar esta entidad
}*/
