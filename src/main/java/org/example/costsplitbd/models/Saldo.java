/*
package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

*/
/**
 * Representa un saldo en la aplicación de división de costos.
 * Esta entidad se utiliza para rastrear las deudas entre usuarios dentro de un grupo.
 *//*

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "saldo", schema = "costsplit", catalog = "postgres")
public class Saldo {
    */
/**
     * El identificador único para el saldo.
     *//*

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    */
/**
     * El grupo al que pertenece este saldo.
     *//*

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    */
/**
     * El usuario que debe dinero (deudor).
     *//*

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "deudor", nullable = false)
    private Usuario deudor;

    */
/**
     * El usuario al que se le debe dinero (acreedor).
     *//*

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "acreedor", nullable = false)
    private Usuario acreedor;

    */
/**
     * La cantidad de dinero que se debe.
     *//*

    @Column(name = "monto", nullable = false)
    private BigDecimal monto;
}*/
