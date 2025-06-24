package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CambiarPasswordDTO {
    private String email;
    private String codigo;
    private String nuevaContrasenia;
}