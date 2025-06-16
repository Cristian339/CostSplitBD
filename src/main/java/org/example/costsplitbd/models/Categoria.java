package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa una categoría para clasificar gastos en la aplicación.
 * Esta entidad permite organizar los gastos según su tipo o finalidad.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "categoria", schema = "costsplit")
public class Categoria {
    /**
     * El identificador único para la categoría.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * El nombre de la categoría.
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * El icono representativo de la categoría.
     */
    @Column(name = "icono")
    private String icono;

    /**
     * El color asociado a la categoría.
     */
    @Column(name = "color")
    private String color;

    /**
     * Descripción detallada de la categoría.
     */
    @Column(name = "descripcion")
    private String descripcion;

}