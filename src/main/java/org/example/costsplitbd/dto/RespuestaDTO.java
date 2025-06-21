package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaDTO {
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String nombre;
    private String email;
}