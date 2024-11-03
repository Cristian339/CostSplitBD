package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoDetalladoDTO {
    private Long id;
    private String nombre;
    private String imagenUrl;
    private LocalDateTime fechaCreacion;
    private String descripcion;
    private List<UsuarioDTO> usuarios;
}
