package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "usuario", schema = "costsplit", catalog = "postgres")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "mail", nullable = false)
    private String email;

    @Column(name = "pass", nullable = false)
    private String contrasenia;

    @Column(name = "fecha_creacion", columnDefinition = "date default current_date")
    private LocalDate fechaCreacion;

    @Column(name = "es_admin", nullable = false)
    private boolean esAdmin;

}

