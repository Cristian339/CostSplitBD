package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de la entidad Balance.
 */
@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    /**
     * Encuentra un balance por el ID del grupo y el ID del usuario.
     *
     * @param idGrupo el ID del grupo
     * @param idUsuario el ID del usuario
     * @return un Optional con el balance encontrado, si existe
     */
    @Query("SELECT b FROM Balance b WHERE b.grupo.id = :idGrupo AND b.usuario.id = :idUsuario")
    Optional<Balance> findBalanceByGrupoIdAndUsuarioId(Long idGrupo, Long idUsuario);

    /**
     * Encuentra todos los balances por el ID del grupo.
     *
     * @param idGrupo el ID del grupo
     * @return una lista de balances del grupo
     */
    @Query("SELECT b FROM Balance b WHERE b.grupo.id = :idGrupo")
    List<Balance> findBalanceByGrupoId(Long idGrupo);
}