package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Objeto de Transferencia de Datos para representar un grupo detallado.
 * Este DTO se utiliza para transferir información detallada sobre un grupo, incluyendo el ID, nombre, URL de la imagen, fecha de creación, descripción y la lista de usuarios.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoDetalladoDTO {
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

    /**
     * La lista de usuarios en el grupo.
     */
    private List<UsuarioDTO> usuarios;
}