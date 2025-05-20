package org.example.costsplitbd.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de Transferencia de Datos para representar una categoría de gastos.
 * Este DTO se utiliza para transferir información sobre una categoría, incluyendo el ID, nombre e icono.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
    /**
     * El ID de la categoría.
     */
    private Long id;

    /**
     * El nombre de la categoría.
     */
    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    private String nombre;

    /**
     * El icono representativo de la categoría.
     */
    private String icono;
}