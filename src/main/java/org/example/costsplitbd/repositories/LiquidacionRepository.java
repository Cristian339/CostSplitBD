package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.Liquidacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones de la entidad Liquidacion.
 */
@Repository
public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {

    /**
     * Encuentra todas las liquidaciones por el ID del grupo.
     *
     * @param idGrupo el ID del grupo
     * @return una lista de liquidaciones del grupo
     */
    List<Liquidacion> findByGrupoId(Long idGrupo);

    /**
     * Encuentra todas las liquidaciones donde un usuario es el pagador.
     *
     * @param idUsuario el ID del usuario pagador
     * @return una lista de liquidaciones donde el usuario es pagador
     */
    List<Liquidacion> findByPagadorId(Long idUsuario);

    /**
     * Encuentra todas las liquidaciones donde un usuario es el receptor.
     *
     * @param idUsuario el ID del usuario receptor
     * @return una lista de liquidaciones donde el usuario es receptor
     */
    List<Liquidacion> findByReceptorId(Long idUsuario);
}