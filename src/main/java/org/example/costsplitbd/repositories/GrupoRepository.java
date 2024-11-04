package org.example.costsplitbd.repositories;

import org.example.costsplitbd.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo,Long> {
    List<Grupo> findByUsuarios_Id(Long idUsuario);
}
