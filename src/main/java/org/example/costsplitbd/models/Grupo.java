package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "grupo", schema = "costsplit", catalog = "postgres")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "imagen_url", nullable = false)
    private String imagenUrl;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "usuario_grupo",
            joinColumns = {@JoinColumn(name = "id_grupo")},
            inverseJoinColumns = {@JoinColumn(name = "id_usuario")}
    )
    private Set<Usuario> usuarios;




}