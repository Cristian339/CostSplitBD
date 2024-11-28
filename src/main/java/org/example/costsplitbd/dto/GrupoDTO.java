package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

/**
 * Objeto de Transferencia de Datos para representar un grupo.
 * Este DTO se utiliza para transferir información sobre un grupo, incluyendo el ID, nombre, URL de la imagen, fecha de creación y descripción.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class GrupoDTO {
    /**
     * El ID del grupo.
     */
    private Long id;

    /**
     * El nombre del grupo.
     */
    private String nombre;

    /**
     * La URL de la imagen del grupo.
     */
    private String imagenUrl;

    /**
     * La fecha de creación del grupo.
     */
    private LocalDateTime fechaCreacion;

    /**
     * La descripción del grupo.
     */
    private String descripcion;
}