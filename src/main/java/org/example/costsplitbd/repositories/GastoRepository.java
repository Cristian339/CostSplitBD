package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones de la entidad Gasto.
 */
@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

    /**
     * Encuentra todos los gastos por el ID del grupo.
     *
     * @param idGrupo el ID del grupo
     * @return una lista de gastos del grupo
     */
    @Query("SELECT DISTINCT g FROM Gasto g WHERE g.grupo.id = :idGrupo")
    List<Gasto> findGastoByGrupoId(Long idGrupo);
}