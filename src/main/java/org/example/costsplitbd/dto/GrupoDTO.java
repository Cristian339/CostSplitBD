package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoDTO {
    private Long id;
    private String nombre;
    private String imagenUrl;
    private LocalDateTime fechaCreacion;
    private String descripcion;
}
