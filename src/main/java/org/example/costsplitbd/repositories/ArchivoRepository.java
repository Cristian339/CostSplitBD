package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones de la entidad Archivo.
 */
@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {

    /**
     * Encuentra todos los archivos por el ID del gasto.
     *
     * @param idGasto el ID del gasto
     * @return una lista de archivos asociados al gasto
     */
    List<Archivo> findByGastoId(Long idGasto);
}