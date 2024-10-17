package org.example.costsplitbd.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "grupo", schema = "CostSplit", catalog = "postgres")
public class Grupo {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="fecha_creacion")
    private LocalDate fechaCreacion;



    @ManyToMany
    @JoinTable(name = "usuario_grupo",
            joinColumns = {@JoinColumn(name = "id_grupo")},
            inverseJoinColumns = {@JoinColumn(name = "id_usuario")} //el inverso de la relacion es el usuario
    )//es la relacion que tenemos usuario_grupo
    private Set<Usuario> usuarios; //relacion muchos a muchos y ya sabe que ala que tiene que llegar es a dicha tabla

//    //many to one
//    @ManyToOne
//    @JoinColumn(name = "id_usuario")
//    private Usuario usuario; //relacion muchos a uno
//
//
//    @ManyToOne
//    @JoinColumn(name = "id_grupo")
//    //siempre se inicia desde donde se hacen las foreign keys
}
