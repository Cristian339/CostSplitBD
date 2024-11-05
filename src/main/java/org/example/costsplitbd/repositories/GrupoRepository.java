package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar las operaciones de la entidad Grupo.
 */
@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    /**
     * Encuentra todos los grupos por el ID del usuario.
     *
     * @param idUsuario el ID del usuario
     * @return una lista de grupos del usuario
     */
    List<Grupo> findByUsuarios_Id(Long idUsuario);
}