package org.example.costsplitbd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de Transferencia de Datos para representar un archivo adjunto.
 * Este DTO se utiliza para transferir información sobre un archivo, incluyendo el ID, ID del gasto asociado,
 * URL, tipo y nombre del archivo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivoDTO {
    /**
     * El ID del archivo.
     */
    private Long id;

    /**
     * El ID del gasto al que está asociado este archivo.
     */
    private Long gastoId;

    /**
     * La URL donde se almacena el archivo.
     */
    private String url;

    /**
     * El tipo de archivo (extensión o MIME type).
     */
    private String tipo;

    /**
     * El nombre original del archivo.
     */
    private String nombre;
}