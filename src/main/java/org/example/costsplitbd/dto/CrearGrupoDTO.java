package org.example.costsplitbd.dto;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.costsplitbd.models.Usuario;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Objeto de Transferencia de Datos para crear un grupo.
 * Este DTO se utiliza para transferir la información necesaria para crear un grupo, incluyendo el nombre, la URL de la imagen y la descripción.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearGrupoDTO {
    /**
     * El nombre del grupo.
     */
    @NotBlank(message = "El nombre de grupo no puede estar vacío")
    private String nombre;

    /**
     * La URL de la imagen del grupo.
     */
    private String imagenUrl;

    /**
     * La descripción del grupo.
     */
    @NotBlank(message = "La Descripcion no puede ser vacia")
    private String descripcion;

    List<Usuario> participantes;
}