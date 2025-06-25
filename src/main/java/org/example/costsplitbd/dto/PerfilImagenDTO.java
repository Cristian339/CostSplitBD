package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerfilImagenDTO {
    private Long usuarioId;
    private String imagenUrl;
}