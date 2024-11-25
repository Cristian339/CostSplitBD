package org.example.costsplitbd.dto;

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
    private String nombre;

    /**
     * La URL de la imagen del grupo.
     */
    private String imagenUrl;

    /**
     * La descripción del grupo.
     */
    private String descripcion;

    List<Usuario> participantes;
}