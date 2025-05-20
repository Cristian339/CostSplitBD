package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa un archivo adjunto en la aplicación de división de costos.
 * Esta entidad se utiliza para almacenar comprobantes de pago u otros documentos relacionados.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "archivo", schema = "costsplit")
public class Archivo {
    /**
     * El identificador único para el archivo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * El gasto al que está asociado este archivo.
     */
    @ManyToOne
    @JoinColumn(name = "id_gasto")
    private Gasto gasto;

    /**
     * La URL donde se almacena el archivo.
     */
    @Column(name = "url", nullable = false)
    private String url;

    /**
     * El tipo de archivo (extensión o MIME type).
     */
    @Column(name = "tipo")
    private String tipo;

    /**
     * El nombre original del archivo.
     */
    @Column(name = "nombre")
    private String nombre;
}