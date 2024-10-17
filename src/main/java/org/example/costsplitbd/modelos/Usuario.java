package org.example.costsplitbd.modelos;

import jakarta.persistence.*;
import lombok.*;
import org.example.costsplitbd.enumerados.Rol;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "usuario", schema = "CostSplit", catalog = "postgres")
public class Usuario {
    @jakarta.persistence.Id
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
    private Integer id;

     @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "email")
    private String email;

    @Column(name = "contrasenia")
    private String contrasenia;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "es_admin")
    private boolean esAdmin;

    @Column(name = "rol")
    @Enumerated(EnumType.STRING)
    private Rol rol;



    //si la tabla tiene foreign keys no se genera
    //si tiene foreign keys y atributos si se genera


}
