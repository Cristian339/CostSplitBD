package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de Transferencia de Datos para representar un usuario.
 * Este DTO se utiliza para transferir información sobre un usuario, incluyendo el ID, nombre, apellido y correo electrónico.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    /**
     * El ID del usuario.
     */
    private Long id;

    /**
     * El nombre del usuario.
     */
    private String nombre;

    /**
     * El apellido del usuario.
     */
    private String apellido;

    /**
     * El correo electrónico del usuario.
     */
    private String email;
}