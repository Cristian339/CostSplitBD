package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Transferencia de Datos para agregar participantes a un grupo.
 * Este DTO se utiliza para transferir la lista de IDs de usuarios que se agregarán como participantes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AniadirParticipanteDTO {
    /**
     * Lista de IDs de usuarios que se agregarán como participantes.
     */
    private List<Long> idUsuarios;
}