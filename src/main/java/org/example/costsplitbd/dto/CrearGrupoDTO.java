package org.example.costsplitbd.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearGrupoDTO {
    private String nombre;
    private String imagenUrl;
    private String descripcion;
}
