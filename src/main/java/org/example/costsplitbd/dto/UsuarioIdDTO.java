package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para exponer únicamente el ID de un usuario.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioIdDTO {
    private Long id;
}