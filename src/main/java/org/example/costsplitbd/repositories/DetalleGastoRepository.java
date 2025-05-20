package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.DetalleGasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones de la entidad DetalleGasto.
 */
@Repository
public interface DetalleGastoRepository extends JpaRepository<DetalleGasto, Long> {

    /**
     * Encuentra todos los detalles de gasto por el ID del gasto.
     *
     * @param idGasto el ID del gasto
     * @return una lista de detalles del gasto
     */
    List<DetalleGasto> findByGastoId(Long idGasto);

    /**
     * Encuentra todos los detalles de gasto por el ID del usuario.
     *
     * @param idUsuario el ID del usuario
     * @return una lista de detalles de gasto del usuario
     */
    List<DetalleGasto> findByUsuarioId(Long idUsuario);
}