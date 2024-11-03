/*
package org.example.costsplitbd.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "usuario_grupo", schema = "costsplit", catalog = "postgres")
public class UsuarioGrupo {
    @EmbeddedId
    private UsuarioGrupoId id;

    @Embeddable
    public static class UsuarioGrupoId {
        @Column(name = "id_usuario")
        private Integer idUsuario;

        @Column(name = "id_grupo")
        private Integer idGrupo;
    }
}*/
