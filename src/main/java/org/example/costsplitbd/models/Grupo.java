package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Representa un grupo en la aplicación de división de costos.
 * Esta entidad se utiliza para agrupar a los usuarios y gestionar los gastos compartidos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "grupo", schema = "costsplit", catalog = "postgres")
public class Grupo {
    /**
     * El identificador único para el grupo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * El nombre del grupo.
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * La fecha de creación del grupo.
     */
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    /**
     * La URL de la imagen del grupo.
     */
    @Column(name = "imagen_url", nullable = false)
    private String imagenUrl;

    /**
     * La descripción del grupo.
     */
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    /**
     * Los usuarios que pertenecen al grupo.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "usuario_grupo",
            joinColumns = {@JoinColumn(name = "id_grupo")},
            inverseJoinColumns = {@JoinColumn(name = "id_usuario")}
    )
    private Set<Usuario> usuarios;
}