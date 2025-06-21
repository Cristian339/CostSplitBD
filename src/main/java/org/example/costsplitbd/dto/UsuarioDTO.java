package org.example.costsplitbd.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * Objeto de Transferencia de Datos para representar un usuario.
 * Este DTO se utiliza para transferir información sobre un usuario, incluyendo el ID, nombre, apellido y correo electrónico.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UsuarioDTO {
    /**
     * El ID del usuario.
     */
    private Long id;

    /**
     * El nombre del usuario.
     */
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    /**
     * El apellido del usuario.
     */
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    /**
     * El correo electrónico del usuario.
     */
    @Email(message = "El correo electrónico debe tener un formato correcto")
    private String email;

    /**
     * La URL de la imagen del usuario.
     */

    private String urlImg;

    /**
     * La contraseña del usuario.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contrasenia;
}